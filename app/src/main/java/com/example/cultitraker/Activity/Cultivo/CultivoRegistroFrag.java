package com.example.cultitraker.Activity.Cultivo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cultitraker.Activity.parcela.ParcelaTierraFrag;
import com.example.cultitraker.DataBase.CommandDb.CultivoExecuteDb;
import com.example.cultitraker.Models.Cultivo;
import com.example.cultitraker.Models.Parcela;
import com.example.cultitraker.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CultivoRegistroFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CultivoRegistroFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText nombreCultivo, tipoRiego, fechaSiembra;
    private Button btnGuardar, btnCancelar;
    private boolean isEdit;
    private int id;
    private CultivoExecuteDb cultivoExecuteDb;


    public CultivoRegistroFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CultivoRegistroFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static CultivoRegistroFrag newInstance(String param1, String param2) {
        CultivoRegistroFrag fragment = new CultivoRegistroFrag();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cultivo_registro, container, false);
        initComponent(view);
        return view;
    }

    private void initComponent(View view) {
        // Instancia la clase de manejo de base de datos para Cultivo
        cultivoExecuteDb = new CultivoExecuteDb(requireContext());

        // Referencias a los campos del layout XML
        nombreCultivo = view.findViewById(R.id.txt_nombreCultivo);
        tipoRiego = view.findViewById(R.id.txt_TipoRiego);
        fechaSiembra = view.findViewById(R.id.txt_cantidadParcela);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        btnCancelar = view.findViewById(R.id.btn_salir);

        // Verifica si es modo edición (para cargar datos ya existentes)
        isEdit = getArguments() != null && getArguments().getBoolean("isEdit", false);
        id = getArguments() != null ? getArguments().getInt("id", 0) : 0;

        if (isEdit) {
            cargarDatosCultivo();
        }

        inicializarEventos();
    }

    private void inicializarEventos() {
        btnGuardar.setOnClickListener(v -> registrarCultivo());
        btnCancelar.setOnClickListener(v -> cancelar());
    }

    private void cargarDatosCultivo() {
        ArrayList<Cultivo> cultivos = cultivoExecuteDb.consultarPorId(id);

        if (!cultivos.isEmpty()) {
            Cultivo cultivo = cultivos.get(0);
            nombreCultivo.setText(cultivo.getNombre());
            tipoRiego.setText(cultivo.getTipo());
            fechaSiembra.setText(String.valueOf(cultivo.getFechaSiembra()));
        } else {
            Toast.makeText(requireContext(), "No se encontraron datos del cultivo", Toast.LENGTH_LONG).show();
        }
    }

    private void registrarCultivo() {
        Cultivo cultivo = crearCultivo();
        boolean isValido;

        if (isEdit) {
            isValido = cultivoExecuteDb.actualizarDatos(cultivo);
        } else {
            isValido = cultivoExecuteDb.agregarDatos(cultivo);
        }

        if (isValido) {
            Toast.makeText(requireContext(), "Registro " + (isEdit ? "actualizado" : "guardado"), Toast.LENGTH_LONG).show();
            limpiar();
            ventanaPrincipal();
        } else {
            Toast.makeText(requireContext(), "Cultivo no guardado", Toast.LENGTH_LONG).show();
        }

        isEdit = false;
    }


    private Cultivo crearCultivo() {
        Cultivo cultivo = new Cultivo();
        cultivo.setId(isEdit ? id : 0);
        cultivo.setNombre(nombreCultivo.getText().toString());
        cultivo.setTipo(tipoRiego.getText().toString());
        cultivo.setFechaSiembra(fechaSiembra.getText().toString());
        return cultivo;
    }

    private void limpiar() {
        nombreCultivo.setText("");
        tipoRiego.setText("");
        fechaSiembra.setText("");
    }


    private void cancelar() {
        limpiar();
        ventanaPrincipal();
    }

    private void ventanaPrincipal() {
        ParcelaTierraFrag nuevoFragment = new ParcelaTierraFrag();
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frl_principal, nuevoFragment)
                .addToBackStack(null)
                .commit();
    }


}