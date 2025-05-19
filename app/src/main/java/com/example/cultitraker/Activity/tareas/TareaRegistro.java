package com.example.cultitraker.Activity.tareas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cultitraker.Activity.regar.RiegoRegistroFrag;
import com.example.cultitraker.DataBase.CommandDb.TareasExecuteDb;
import com.example.cultitraker.Models.Tareas;
import com.example.cultitraker.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TareaRegistro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TareaRegistro extends Fragment {

    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private EditText tipoActividad, descripcion, fecha, estado;
    private Button btnGuardar, btnCancelar;

    private boolean isEdit;
    private int id;
    private TareasExecuteDb tareasExecuteDb;


    public TareaRegistro() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TareaRegistro.
     */
    // TODO: Rename and change types and number of parameters
    public static TareaRegistro newInstance(String param1, String param2) {
        TareaRegistro fragment = new TareaRegistro();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarea_registro, container, false);
        initComponent(view);
        return view;
    }
    private void initComponent(View view) {
        tareasExecuteDb = new TareasExecuteDb(requireContext());

        tipoActividad = view.findViewById(R.id.txt_tipoActividad);
        descripcion = view.findViewById(R.id.txt_descripcionActividad);
        fecha = view.findViewById(R.id.txt_fechaActividad);
        estado = view.findViewById(R.id.txt_estadoActividad);

        btnGuardar = view.findViewById(R.id.btn_guardar);
        btnCancelar = view.findViewById(R.id.btnSalirTarea);

        // Verifica si es modo edición (para cargar datos ya existentes)
        isEdit = getArguments() != null && getArguments().getBoolean("isEdit", false);
        id = getArguments() != null ? getArguments().getInt("id", 0) : 0;


        if (isEdit) {
            cargarDatosTarea();
        }

        inicializarEventos();
    }

    private void inicializarEventos() {
        btnGuardar.setOnClickListener(v -> registrarTarea());
        btnCancelar.setOnClickListener(v -> cancelarTarea());
    }

    private void cargarDatosTarea() {
        ArrayList<Tareas> tareas = tareasExecuteDb.consultarPorId(id);
        if (!tareas.isEmpty()) {
            Tareas tarea = tareas.get(0);
            tipoActividad.setText(tarea.getTipoActividad());
            descripcion.setText(tarea.getDescripcion());
            fecha.setText(tarea.getFecha());
            estado.setText(tarea.getEstado());
        } else {
            Toast.makeText(requireContext(), "No se encontraron datos de la tarea", Toast.LENGTH_LONG).show();
        }
    }

    private Tareas crearTareaData() {
        Tareas tarea = new Tareas();
        tarea.setId(isEdit ? id : 0);
        tarea.setTipoActividad(tipoActividad.getText().toString());
        tarea.setDescripcion(descripcion.getText().toString());
        tarea.setFecha(fecha.getText().toString());
        tarea.setEstado(estado.getText().toString());
        return tarea;
    }

    private void registrarTarea() {
        Tareas tarea = crearTareaData();
        boolean isValid;
        if (isEdit) {
            isValid = tareasExecuteDb.actualizarDatos(tarea);
        } else {
            isValid = tareasExecuteDb.agregarDatos(tarea);
        }

        if (isValid) {
            Toast.makeText(requireContext(), "Registro " + (isEdit ? "actualizado" : "guardado"), Toast.LENGTH_LONG).show();
            limpiar();
            ventanaPrincipal();
        } else {
            Toast.makeText(requireContext(), "Tarea no guardada", Toast.LENGTH_LONG).show();
        }
        isEdit = false;
    }

    private void limpiar() {
        tipoActividad.setText("");
        descripcion.setText("");
        fecha.setText("");
        estado.setText("");
    }

    private void cancelarTarea() {
        limpiar();
        ventanaPrincipal();
    }

    private void ventanaPrincipal() {
        // Cambiar al fragmento principal, por ejemplo a ParcelaTierraFrag (ajusta según tu app)
        TareasFragment nuevoFragment = new TareasFragment();
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frl_principal, nuevoFragment)
                .addToBackStack(null)
                .commit();
    }


}