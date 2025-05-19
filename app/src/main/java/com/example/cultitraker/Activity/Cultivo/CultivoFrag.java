package com.example.cultitraker.Activity.Cultivo;

import static android.widget.Toast.LENGTH_SHORT;

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

import com.example.cultitraker.Activity.parcela.ParcelaRegistroFragment;
import com.example.cultitraker.AdapterItems.AdapterGeneral;
import com.example.cultitraker.AdapterItems.AdapterModel;
import com.example.cultitraker.DataBase.CommandDb.CultivoExecuteDb;
import com.example.cultitraker.Models.Cultivo;
import com.example.cultitraker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CultivoFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CultivoFrag extends Fragment {

    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        View rootView = inflater.inflate(R.layout.fragment_cultivo, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerViewCultivo);  // <- cambia esto por el id real en fragment_cultivo.xml
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        cargarDatosCultivo();

        return rootView;

    }


    private void cargarDatosCultivo() {
        ArrayList<Cultivo> cultivos = cargarDatosCultivoDB();
        if(cultivos.isEmpty()){
            Toast.makeText(requireContext(),"No hay datos que mostrar",LENGTH_SHORT).show();
        }else{
            Toast.makeText(requireContext(),"Hay Datos que mostrar",LENGTH_SHORT).show();
            ArrayList<AdapterModel> adapterModels = new ArrayList<>();

            for (Cultivo cultivo : cultivos) {
                AdapterModel adapterModel = new AdapterModel();
                adapterModel.setTitulo(cultivo.getNombre());
                adapterModel.setSubTitulo(cultivo.getTipo());
                adapterModel.setParrafo(cultivo.getFechaSiembra());
                adapterModels.add(adapterModel);
            }

            AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, requireContext(), R.layout.card_item_bloque);
            recyclerView.setAdapter(adapterGeneral);
        }

    }

    private ArrayList<Cultivo> cargarDatosCultivoDB() {
        CultivoExecuteDb cultivoExecuteDb = new CultivoExecuteDb(requireContext());
        return cultivoExecuteDb.consultarDatos();
    }
}