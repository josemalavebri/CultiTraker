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
    private EditText tipoActividad, descripcion, fecha, estado;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tarea_registro, container, false);
    }

    private Tareas crearTareaData() {
        Tareas tarea = new Tareas();
        tarea.setTipoActividad(tipoActividad.getText().toString());
        tarea.setDescripcion(descripcion.getText().toString());
        tarea.setFecha(fecha.getText().toString());
        tarea.setEstado(estado.getText().toString());
        return tarea;
    }

    private void registrarTarea() {
        Tareas tarea = crearTareaData();
        TareasExecuteDb tareasExecuteDb = new TareasExecuteDb(requireContext());

        boolean isValid = tareasExecuteDb.agregarDatos(tarea);
        if (isValid) {
            Toast.makeText(getContext(), "Tarea guardada con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Tarea no guardada", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelarTarea() {
        // Regresar al fragmento anterior
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}

