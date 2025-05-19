package com.example.cultitraker.Activity.insumo;

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
import com.example.cultitraker.AdapterItems.AdapterGeneral;
import com.example.cultitraker.AdapterItems.AdapterModel;
import com.example.cultitraker.DataBase.CommandDb.InsumoExecuteDB;
import com.example.cultitraker.Models.Insumo;
import com.example.cultitraker.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsumoFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsumoFrag extends Fragment {


    private InsumoExecuteDB insumoExecuteDB;
    private ArrayList<Integer>idInsumos;
    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InsumoFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsumoFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static InsumoFrag newInstance(String param1, String param2) {
        InsumoFrag fragment = new InsumoFrag();
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


        View view = inflater.inflate(R.layout.fragment_insumo, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewInsumo);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        initComponent();
        Button button = view.findViewById(R.id.btn_AgregarInsumo);
         button.setOnClickListener(v -> {
            InsumoRegistroFrag nuevoFragment = InsumoRegistroFrag.newInstance(0, false);

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frl_principal, nuevoFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void initComponent(){
        insumoExecuteDB = new InsumoExecuteDB(requireContext());
        cargarDatosInsumo();
    }



    public void cargarDatosInsumo(){
        ArrayList<Insumo> insumos = cargarDatosInsumoDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();
        idInsumos=new ArrayList<>();

        for(Insumo insumo : insumos){
            AdapterModel adapterModel = new AdapterModel();
            idInsumos.add(insumo.getId());
            adapterModel.setTitulo(insumo.getNombre());
            adapterModel.setSubTitulo(insumo.getTipo());
            adapterModel.setParrafo(String.valueOf(insumo.getCantidad()));
            adapterModel.setDetail(insumo.getProveedor());
            adapterModels.add(adapterModel);
        }


        //ENVIAR SUS CARD ITEMS CON SU ID
        AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, requireContext(), R.layout.card_item_parcela);

        adapterGeneral.setOnItemClickListener(v -> {
            int position = (int) v.getTag();
            int id = idInsumos.get(position);
            if (v.getId()==R.id.btn_EliminarParcela){
                confirmarEliminar(id);
                cargarDatosInsumo();
            }else {
                actualizarRegistro(id);
            }
        });
        recyclerView.setAdapter(adapterGeneral);
    }



    private void confirmarEliminar(int id) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar este registro?")
                .setPositiveButton("Si", (dialog, which) -> {
                    boolean eliminado = insumoExecuteDB.eliminarInsumo(id);
                    if(eliminado){
                        Toast.makeText(requireContext(), "Registro eliminado correctamente", Toast.LENGTH_LONG).show();
                        cargarDatosInsumo();
                    }else{
                        Toast.makeText(requireContext(), "Error al eliminar el registro", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void actualizarRegistro(int id) {
        InsumoRegistroFrag nuevoFragment = new InsumoRegistroFrag();

        // Pasar argumentos al fragmento
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

    private ArrayList<Insumo>cargarDatosInsumoDB(){
        return insumoExecuteDB.consultarInsumos();
    }




    private void cambiarFragment(){
        CultivoRegistroFrag nuevoFragment = new CultivoRegistroFrag();

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frl_principal, nuevoFragment)
                .addToBackStack(null)
                .commit();
    }


}