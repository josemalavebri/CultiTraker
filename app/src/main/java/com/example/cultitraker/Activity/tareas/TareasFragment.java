package com.example.cultitraker.Activity.tareas;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.cultitraker.Activity.Cultivo.CultivoRegistroFrag;
import com.example.cultitraker.AdapterItems.AdapterGeneral;
import com.example.cultitraker.AdapterItems.AdapterModel;
import com.example.cultitraker.AdapterItems.AdapterTareas;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView recyclerView;
    private TareasExecuteDb tareasExecuteDb;
    private ArrayList<Integer> idCultivos;


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
        View view = inflater.inflate(R.layout.fragment_tareas, container, false);

        iniciarComponentes(view);
        cargarDatosTarea();
        cargarEventos(view);

        return view;
    }

    private void iniciarComponentes(View view) {
        tareasExecuteDb = new TareasExecuteDb(requireContext());
        recyclerView = view.findViewById(R.id.recyclerViewTareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
    private void cargarDatosTarea() {
        ArrayList<Tareas> tareas = tareasExecuteDb.consultarDatos();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();
        idCultivos = new ArrayList<>();

        for (Tareas tarea : tareas) {
            idCultivos.add(tarea.getId());

            AdapterModel adapterModel = new AdapterModel();
            adapterModel.setTitulo(tarea.getTipoActividad());
            adapterModel.setSubTitulo(tarea.getEstado());
            adapterModel.setParrafo(tarea.getDescripcion());
            adapterModel.setDetail(tarea.getFecha());
            adapterModels.add(adapterModel);
        }

        AdapterTareas adapterGeneral = new AdapterTareas(adapterModels, requireContext(), R.layout.card_item_tareas);
        adapterGeneral.setOnItemClickListener(v -> {
            int position = (int) v.getTag();
            int id = idCultivos.get(position);
            //cambio
            if (v.getId()==R.id.btn_EliminarTarea){
                System.out.println("1");
                confirmarEliminar(id);
                cargarDatosTarea();
            }else {
                actualizarRegistro(id);
            }
        });
        recyclerView.setAdapter(adapterGeneral);
    }

    private void cargarEventos(View view) {
        Button button = view.findViewById(R.id.btn_AgregarTarea);
        button.setOnClickListener(v -> cambiarFragment());
    }

    private void cambiarFragment() {
        TareaRegistro nuevoFragment = new TareaRegistro();
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frl_principal, nuevoFragment)
                .addToBackStack(null)
                .commit();
    }

    private void actualizarRegistro(int id) {
        //CAMBIO
        TareaRegistro nuevoFragment = new TareaRegistro();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putBoolean("isEdit", true);
        nuevoFragment.setArguments(bundle);

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frl_principal, nuevoFragment)
                .addToBackStack(null)
                .commit();
    }

    private void confirmarEliminar(int id) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar este registro?")
                .setPositiveButton("Si", (dialog, which) -> {
                    System.out.println("DATO");
                    //cambio
                    boolean eliminado = tareasExecuteDb.eliminarDatos(id);
                    if(eliminado){
                        Toast.makeText(requireContext(), "Registro eliminado correctamente", Toast.LENGTH_LONG).show();
                        cargarDatosTarea();
                    }else{
                        Toast.makeText(requireContext(), "Error al eliminar el registro", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}