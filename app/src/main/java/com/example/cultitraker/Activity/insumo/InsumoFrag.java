package com.example.cultitraker.Activity.insumo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cultitraker.Activity.parcela.ParcelaRegistroFragment;
import com.example.cultitraker.AdapterItems.AdapterGeneral;
import com.example.cultitraker.AdapterItems.AdapterModel;
import com.example.cultitraker.DataBase.CommandDb.InsumoExecuteDB;
import com.example.cultitraker.DataBase.CommandDb.RegarExecuteDb;
import com.example.cultitraker.Models.Insumo;
import com.example.cultitraker.Models.Regar;
import com.example.cultitraker.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsumoFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsumoFrag extends Fragment {

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insumo, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewInsumo);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        cargarDatosInsumo();

        Button button = view.findViewById(R.id.btn_AgregarInsumo);
        button.setOnClickListener(v -> {
            InsumoRegistroFrag nuevoFragment = new InsumoRegistroFrag();

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frl_principal, nuevoFragment)
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }

    private void cargarDatosInsumo() {
        ArrayList<Insumo> insumos = cargarDatosInsumoDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();

        for (Insumo insumo : insumos) {
            AdapterModel adapterModel = new AdapterModel();
            //adapterModel.setDetail(String.valueOf(insumo.get()));
            adapterModels.add(adapterModel);
        }

        AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, requireContext(), R.layout.card_item_bloque);
        recyclerView.setAdapter(adapterGeneral);
    }

    private ArrayList<Insumo> cargarDatosInsumoDB() {
        InsumoExecuteDB insumoExecuteDb = new InsumoExecuteDB(requireContext());
        //return insumoExecuteDb.consultarDatos();
        return new ArrayList<>();
    }
}