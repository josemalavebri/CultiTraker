package com.example.cultitraker.Activity.parcela;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cultitraker.AdapterItems.AdapterGeneral;
import com.example.cultitraker.AdapterItems.AdapterModel;
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
    private String mParam1;
    private String mParam2;


    RecyclerView recyclerView;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parcela_tierra, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewParcelas);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        cargarDatosParcela();

        Button button = view.findViewById(R.id.btn_AgregarInsumo);
        button.setOnClickListener(v -> {
            ParcelaRegistroFragment nuevoFragment = new ParcelaRegistroFragment();

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frl_principal, nuevoFragment)
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }


    private void cargarDatosParcela() {
        ArrayList<Parcela> parcelas = cargarDatosParcelaDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();

        for (Parcela parcela : parcelas) {
            AdapterModel adapterModel = new AdapterModel();
            adapterModel.setTitulo(parcela.getNombre());
            adapterModel.setSubTitulo(parcela.getCultivo());
            adapterModel.setParrafo(String.valueOf(parcela.getTamano()));
            adapterModel.setDetail(String.valueOf(parcela.getCantidadCultivo()));
            adapterModels.add(adapterModel);
        }

        AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, requireContext(), R.layout.card_item_parcela);
        recyclerView.setAdapter(adapterGeneral);
    }

    private ArrayList<Parcela> cargarDatosParcelaDB() {
        ParcelaExecuteDb parcelaExecuteDb = new ParcelaExecuteDb(requireContext());
        return parcelaExecuteDb.consultarDatos();
    }




}