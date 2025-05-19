package com.example.cultitraker.Activity.regar;

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

import com.example.cultitraker.Activity.Cultivo.CultivoRegistroFrag;
import com.example.cultitraker.Activity.parcela.ParcelaRegistroFragment;
import com.example.cultitraker.AdapterItems.AdapterGeneral;
import com.example.cultitraker.AdapterItems.AdapterModel;
import com.example.cultitraker.AdapterItems.AdapterParcela;
import com.example.cultitraker.DataBase.CommandDb.ParcelaExecuteDb;
import com.example.cultitraker.DataBase.CommandDb.RegarExecuteDb;
import com.example.cultitraker.Models.Regar;
import com.example.cultitraker.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RiegoFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RiegoFrag extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private ArrayList<Integer>idRiegos;
    private RegarExecuteDb regarExecuteDb;

    public RiegoFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_riego.
     */
    // TODO: Rename and change types and number of parameters
    public static RiegoFrag newInstance(String param1, String param2) {
        RiegoFrag fragment = new RiegoFrag();
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
        /*View view = inflater.inflate(R.layout.fragment_riego, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewRiego);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        cargarDatosRiego();

        Button button = view.findViewById(R.id.btn_AgregarRiego);
        button.setOnClickListener(v -> {
            RiegoRegistroFrag nuevoFragment = new RiegoRegistroFrag();

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frl_principal, nuevoFragment)
                    .addToBackStack(null)
                    .commit();
        });
        return view;*/

        View view = inflater.inflate(R.layout.fragment_riego, container, false);

        iniciarComponentes(view);
        cargarDatosRiego();
        cargarEventos(view);

        return view;
    }

    private void iniciarComponentes(View view){
        regarExecuteDb = new RegarExecuteDb(requireContext());
        recyclerView = view.findViewById(R.id.recyclerViewRiego);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void cargarDatosRiego() {
        ArrayList<Regar> riegos = cargarDatosRiegoDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();
        idRiegos = new ArrayList<>();
        for (Regar riego : riegos) {
            AdapterModel adapterModel = new AdapterModel();
            idRiegos.add(riego.getId());
            adapterModel.setTitulo(riego.getFecha()+riego.getHora());
            adapterModel.setSubTitulo(riego.getCantidadAgua() + " L");
            adapterModel.setParrafo(riego.getMetodoRiego());
            adapterModel.setDetail(String.valueOf(riego.getParcelaId()));
            adapterModels.add(adapterModel);
        }
        AdapterParcela adapterParcela = new AdapterParcela(adapterModels, requireContext(), R.layout.card_item_riego);

        adapterParcela.setOnItemClickListener(v -> {
            int position = (int) v.getTag();
            int id = idRiegos.get(position);
            //cambio
            if (v.getId()==R.id.btn_EliminarParcelaTierra){
                System.out.println("1");
                confirmarEliminar(id);
                cargarDatosRiego();
            }else {
                actualizarRegistro(id);
            }
        });

        recyclerView.setAdapter(adapterParcela);
    }

    private ArrayList<Regar> cargarDatosRiegoDB() {
        RegarExecuteDb riegoExecuteDb = new RegarExecuteDb(requireContext());
        return riegoExecuteDb.consultarDatos();
    }

    private void confirmarEliminar(int id) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar este registro?")
                .setPositiveButton("Si", (dialog, which) -> {
                    boolean eliminado = regarExecuteDb.eliminarDatos(id);
                    if(eliminado){
                        Toast.makeText(requireContext(), "Registro eliminado correctamente", Toast.LENGTH_LONG).show();
                        System.out.println("DATO");
                        cargarDatosRiego();
                    }else{
                        Toast.makeText(requireContext(), "Error al eliminar el registro", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void actualizarRegistro(int id) {
        RiegoRegistroFrag nuevoFragment = new RiegoRegistroFrag();
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

    private void cargarEventos(View view){
        Button button = view.findViewById(R.id.btn_AgregarRiego);
        button.setOnClickListener(v -> {
            cambiarFragment();
        });
    }

    private void cambiarFragment(){
        RiegoRegistroFrag nuevoFragment = new RiegoRegistroFrag();
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frl_principal, nuevoFragment)
                .addToBackStack(null)
                .commit();
    }

}