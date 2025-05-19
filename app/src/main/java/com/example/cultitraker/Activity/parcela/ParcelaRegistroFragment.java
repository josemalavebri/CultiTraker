package com.example.cultitraker.Activity.parcela;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cultitraker.DataBase.CommandDb.ParcelaExecuteDb;
import com.example.cultitraker.Models.Parcela;
import com.example.cultitraker.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParcelaRegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParcelaRegistroFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ParcelaExecuteDb parcelaExecuteDb;

    private EditText nombreParcela;
    private EditText tamanoParcela;
    private EditText cantidadCultivo;
    private Spinner spinnerCultivo;
    private Button btnGuardar;
    private Button btnCancelar;
    private boolean isEdit;
    private int id;


    public ParcelaRegistroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParcelaRegistroFragment.
     */
    // TODO: Rename and change types and number of parameters

    //CAMBIO
    public static ParcelaRegistroFragment newInstance(String param1, String param2) {
        ParcelaRegistroFragment fragment = new ParcelaRegistroFragment();
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

        //CAMBIO
        View view = inflater.inflate(R.layout.fragment_parcela_registro, container, false);
        initComponent(view);

        return view;
    }

    private void initComponent(View view) {
        //CAMBIO
        parcelaExecuteDb = new ParcelaExecuteDb(requireContext());
        //CAMBIO
        nombreParcela = view.findViewById(R.id.txt_nombreParcela);
        tamanoParcela = view.findViewById(R.id.txt_tamanoParcela);
        cantidadCultivo = view.findViewById(R.id.txt_cantidadParcela);
        spinnerCultivo = view.findViewById(R.id.spinnerCultivo);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        btnCancelar = view.findViewById(R.id.btn_salir);

        isEdit = getArguments() != null && getArguments().getBoolean("isEdit", false);
        id = getArguments() != null ? getArguments().getInt("id", 0) : 0;


        //no va
        cargarTiposCultivo();


        if (isEdit) {
            cargarDatosParcela();
        }

        inicializarEventos();
    }

    private void cargarTiposCultivo() {
        List<String> tiposCultivo = new ArrayList<>();
        tiposCultivo.add("Seleccione un cultivo");
        tiposCultivo.addAll(Arrays.asList("MaÃ­z", "Arroz", "Trigo", "Soja")); // Agrega los tipos de cultivo necesarios

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, tiposCultivo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCultivo.setAdapter(adapter);
    }



    private void inicializarEventos() {
        btnGuardar.setOnClickListener(v -> registrarParcela());
        btnCancelar.setOnClickListener(v -> cancelar());
    }

    private void cargarDatosParcela() {

        //CAMBIO
        ArrayList<Parcela> parcelas = parcelaExecuteDb.consultarPorId(id);
        Parcela parcela = parcelas.get(0);

        if (parcela != null) {
            nombreParcela.setText(parcela.getNombre());
            tamanoParcela.setText(String.valueOf(parcela.getTamano()));
            cantidadCultivo.setText(String.valueOf(parcela.getCantidadCultivo()));

            ArrayAdapter adapter = (ArrayAdapter) spinnerCultivo.getAdapter();
            int position = adapter.getPosition(parcela.getCultivo());
            spinnerCultivo.setSelection(position);
        } else {
            Toast.makeText(requireContext(), "No se encontraron datos", Toast.LENGTH_LONG).show();
        }
    }

    private Parcela crearParcela() {

        //CAMBIO
        Parcela parcela = new Parcela();
        parcela.setId(isEdit ? id : 0);
        parcela.setNombre(nombreParcela.getText().toString());
        parcela.setTamano(Double.parseDouble(tamanoParcela.getText().toString()));
        parcela.setCantidadCultivo(Integer.parseInt(cantidadCultivo.getText().toString()));
        parcela.setCultivo(spinnerCultivo.getSelectedItem().toString());
        return parcela;
    }

    private void registrarParcela() {

        //CAMBIO
        Parcela parcela = crearParcela();
        boolean isValido;
        if (isEdit) {
            isValido = parcelaExecuteDb.actualizarDatos(parcela);
        } else {
            isValido = parcelaExecuteDb.agregarDatos(parcela);
        }

        if (isValido) {
            Toast.makeText(requireContext(), "Registro " + (isEdit ? "actualizado" : "guardado"), Toast.LENGTH_LONG).show();
            limpiar();
            ventanaPrincipal();
        } else {
            Toast.makeText(requireContext(), "Parcela no guardada", Toast.LENGTH_LONG).show();
        }
        isEdit = false;
    }

    private void limpiar() {
        //CAMBIO
        nombreParcela.setText("");
        tamanoParcela.setText("");
        cantidadCultivo.setText("");
        spinnerCultivo.setSelection(0);
    }


    private void cancelar() {
        limpiar();
        ventanaPrincipal();
    }

    private void ventanaPrincipal() {
        ParcelaTierraFrag nuevoFragment = new ParcelaTierraFrag();
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frl_principal, nuevoFragment)
                .addToBackStack(null)
                .commit();
    }



}