package com.example.cultitraker.Activity.tareas;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultitraker.AdapterItems.AdapterGeneral;
import com.example.cultitraker.AdapterItems.AdapterModel;
import com.example.cultitraker.DataBase.CommandDb.TareasExecuteDb;
import com.example.cultitraker.Models.Tareas;
import com.example.cultitraker.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TareasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TareasFragment extends Fragment {

    RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TareasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TareasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TareasFragment newInstance(String param1, String param2) {
        TareasFragment fragment = new TareasFragment();
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
        //return inflater.inflate(R.layout.fragment_tareas, container, false);
        View view = inflater.inflate(R.layout.fragment_tareas, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewTareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        cargarDatosTarea();
        /* Aqui se encuntra el boton para agregar, solo se necesita el fragment container de la activity
        View botonAgregar = view.findViewById(R.id.button7);
        botonAgregar.setOnClickListener(v -> {
            TareaRegistro fragment = new TareaRegistro();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)  // Asegúrate que fragment_container sea el ID del contenedor donde quieres mostrar el fragment
                    .addToBackStack(null) // Esto es opcional, para poder volver atrás con el botón de retroceso
                    .commit();
        });
        */
        //No funciona usar ese metodo "callback" declarado asi, los fragments no se pueden llmar con intents
        /*View botonAgregar = view.findViewById(R.id.button7);
        botonAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TareaRegistroActivity.class);
            startActivity(intent);
        });*/
        //este usa el id:main en cambio los fragments no tienen
        /*
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        */
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        return view;
    }
    public void cargarDatosTarea() {
        ArrayList<Tareas> tareas = cargarDatosTareaDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();
        if (tareas != null) {
            for (Tareas tarea : tareas) {
                AdapterModel adapterModel = new AdapterModel();
                adapterModel.setTitulo(tarea.getTipoActividad());
                adapterModel.setSubTitulo(tarea.getEstado());
                adapterModel.setParrafo(tarea.getDescripcion());
                adapterModel.setDetail(tarea.getFecha());
                adapterModels.add(adapterModel);
            }
            AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, requireContext(), R.layout.card_item_bloque);
            recyclerView.setAdapter(adapterGeneral);
        }
    }

    private ArrayList<Tareas> cargarDatosTareaDB() {
        TareasExecuteDb tareaExecuteDb = new TareasExecuteDb(requireContext());
        return tareaExecuteDb.consultarDatos();
    }
    /*
    public void agregarActionButton(View view){
        Intent intent = new Intent(this, TareaRegistroActivity.class);
        startActivity(intent);
    }
    */
}