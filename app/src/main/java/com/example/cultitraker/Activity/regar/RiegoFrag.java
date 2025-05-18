package com.example.cultitraker.Activity.regar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultitraker.AdapterItems.AdapterGeneral;
import com.example.cultitraker.AdapterItems.AdapterModel;
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

    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riego, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewRiego);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        cargarDatosRiego();
        return view;
    }

    private void cargarDatosRiego() {
        ArrayList<Regar> riegos = cargarDatosRiegoDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();

        for (Regar riego : riegos) {
            AdapterModel adapterModel = new AdapterModel();
            adapterModel.setTitulo(riego.getFecha()+riego.getHora());
            adapterModel.setSubTitulo(riego.getCantidadAgua() + " L");
            adapterModel.setParrafo(riego.getMetodoRiego());
            adapterModel.setDetail(String.valueOf(riego.getParcelaId()));
            adapterModels.add(adapterModel);
        }

        AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, requireContext(), R.layout.card_item_bloque);
        recyclerView.setAdapter(adapterGeneral);
    }

    private ArrayList<Regar> cargarDatosRiegoDB() {
        RegarExecuteDb riegoExecuteDb = new RegarExecuteDb(requireContext());
        return riegoExecuteDb.consultarDatos();
    }
}