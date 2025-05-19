package com.example.cultitraker.Activity.Cultivo;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cultitraker.AdapterItems.AdapterCultivo;
import com.example.cultitraker.AdapterItems.AdapterModel;
import com.example.cultitraker.DataBase.CommandDb.CultivoExecuteDb;
import com.example.cultitraker.Models.Cultivo;
import com.example.cultitraker.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CultivoFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CultivoFrag extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ArrayList<Integer> idCultivos;
    private CultivoExecuteDb cultivoExecuteDb;


    public CultivoFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CultivoFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static CultivoFrag newInstance(String param1, String param2) {
        CultivoFrag fragment = new CultivoFrag();
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
        View view = inflater.inflate(R.layout.fragment_cultivo, container, false);

        iniciarComponentes(view);
        cargarDatosCultivo();
        cargarEventos(view);

        return view;
    }


    private void iniciarComponentes(View view){
        cultivoExecuteDb = new CultivoExecuteDb(requireContext());
        recyclerView = view.findViewById(R.id.recyclerViewCultivo);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        }

    private void cargarDatosCultivo() {
        //cambio
        ArrayList<Cultivo> cultivos = traerDatosCultivoDB();

        ArrayList<AdapterModel> adapterModels = new ArrayList<>();

        //cambio
        idCultivos = new ArrayList<>();
        //cambio
        for (Cultivo cultivo : cultivos) {
            AdapterModel adapterModel = new AdapterModel();
            idCultivos.add(cultivo.getId());

            adapterModel.setTitulo(cultivo.getNombre());
            adapterModel.setSubTitulo(cultivo.getTipo());
            adapterModels.add(adapterModel);
        }

        //cambio
        AdapterCultivo adapterCultivo = new AdapterCultivo(adapterModels, requireContext(), R.layout.card_item_cultivo);

        adapterCultivo.setOnItemClickListener(v -> {
            int position = (int) v.getTag();
            int id = idCultivos.get(position);
            //cambio
            if (v.getId()==R.id.btn_EliminarCultivo){
                System.out.println("1");
                confirmarEliminar(id);
                cargarDatosCultivo();
            }else {
                actualizarRegistro(id);
            }
        });

        recyclerView.setAdapter(adapterCultivo);
    }

    private void actualizarRegistro(int id) {
        //CAMBIO
        CultivoRegistroFrag nuevoFragment = new CultivoRegistroFrag();
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
                    boolean eliminado = cultivoExecuteDb.eliminarDatos(id);
                    if(eliminado){
                        Toast.makeText(requireContext(), "Registro eliminado correctamente", Toast.LENGTH_LONG).show();
                        cargarDatosCultivo();
                    }else{
                        Toast.makeText(requireContext(), "Error al eliminar el registro", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private ArrayList<Cultivo> traerDatosCultivoDB() {
        CultivoExecuteDb cultivoExecuteDb = new CultivoExecuteDb(requireContext());
        return cultivoExecuteDb.consultarDatos();
    }

    private void cargarEventos(View view){
        //cambio

        Button button = view.findViewById(R.id.btn_AgregarCultivo);
        button.setOnClickListener(v -> {
            cambiarFragment();
        });

    }

    private void cambiarFragment(){
        //CAMBIO
        CultivoRegistroFrag nuevoFragment = new CultivoRegistroFrag();
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frl_principal, nuevoFragment)
                .addToBackStack(null)
                .commit();
    }

}