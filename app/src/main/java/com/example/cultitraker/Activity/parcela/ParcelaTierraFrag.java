package com.example.cultitraker.Activity.parcela;

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

import com.example.cultitraker.AdapterItems.AdapterModel;
import com.example.cultitraker.AdapterItems.AdapterParcela;
import com.example.cultitraker.DataBase.CommandDb.ParcelaExecuteDb;
import com.example.cultitraker.Models.Parcela;
import com.example.cultitraker.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParcelaTierraFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParcelaTierraFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    //primero
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ArrayList<Integer>idParcelas;
    private ParcelaExecuteDb parcelaExecuteDb;



    public ParcelaTierraFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment parcelaTierra.
     */
    // TODO: Rename and change types and number of parameters
    public static ParcelaTierraFrag newInstance(String param1, String param2) {
        ParcelaTierraFrag fragment = new ParcelaTierraFrag();
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


    //segundo
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //fragment_parcela_tierra
        View view = inflater.inflate(R.layout.fragment_parcela_tierra, container, false);

        iniciarComponentes(view);
        cargarDatosParcela();
        cargarEventos(view);

        return view;
    }

    private void iniciarComponentes(View view){
        //cambio
        parcelaExecuteDb = new ParcelaExecuteDb(requireContext());
        //ojo con el id del recycler

        //cambio
        recyclerView = view.findViewById(R.id.recyclerViewParcelas);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void cargarDatosParcela() {
        //cambio
        ArrayList<Parcela> parcelas = traerDatosParcelaDB();

        ArrayList<AdapterModel> adapterModels = new ArrayList<>();

        //cambio
        idParcelas=new ArrayList<>();
        //cambio
         for (Parcela parcela : parcelas) {
            AdapterModel adapterModel = new AdapterModel();
            idParcelas.add(parcela.getId());

            adapterModel.setTitulo(parcela.getNombre());
            adapterModel.setSubTitulo(parcela.getCultivo());
            adapterModel.setParrafo(String.valueOf(parcela.getTamano()));
            adapterModel.setDetail(String.valueOf(parcela.getCantidadCultivo()));
            adapterModels.add(adapterModel);
        }



        //cambio
        AdapterParcela adapterParcela = new AdapterParcela(adapterModels, requireContext(), R.layout.card_item_parcela_tierra);

        adapterParcela.setOnItemClickListener(v -> {
            int position = (int) v.getTag();
            int id = idParcelas.get(position);
            //cambio
            if (v.getId()==R.id.btn_EliminarParcelaTierra){
                System.out.println("1");
                confirmarEliminar(id);
                cargarDatosParcela();
            }else {
                actualizarRegistro(id);
            }
        });

        recyclerView.setAdapter(adapterParcela);
    }

    private void cargarEventos(View view){
        //cambio

        Button button = view.findViewById(R.id.btn_AgregarParcela);
        button.setOnClickListener(v -> {
            cambiarFragment();
        });

    }

    private ArrayList<Parcela> traerDatosParcelaDB() {

        //cambio
        ParcelaExecuteDb parcelaExecuteDb = new ParcelaExecuteDb(requireContext());
        return parcelaExecuteDb.consultarDatos();
    }

    private void confirmarEliminar(int id) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar este registro?")
                .setPositiveButton("Si", (dialog, which) -> {
                    System.out.println("DATO");
                    //cambio
                    boolean eliminado = parcelaExecuteDb.eliminarDatos(id);
                    if(eliminado){
                        Toast.makeText(requireContext(), "Registro eliminado correctamente", Toast.LENGTH_LONG).show();
                        cargarDatosParcela();
                    }else{
                        Toast.makeText(requireContext(), "Error al eliminar el registro", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void actualizarRegistro(int id) {


        //CAMBIO
        ParcelaRegistroFragment nuevoFragment = new ParcelaRegistroFragment();
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


    private void cambiarFragment(){
        //CAMBIO
        ParcelaRegistroFragment nuevoFragment = new ParcelaRegistroFragment();
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frl_principal, nuevoFragment)
                .addToBackStack(null)
                .commit();
    }

}
