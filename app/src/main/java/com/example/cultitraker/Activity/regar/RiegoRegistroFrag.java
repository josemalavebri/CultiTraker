package com.example.cultitraker.Activity.regar;

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

import com.example.cultitraker.Activity.parcela.ParcelaTierraFrag;
import com.example.cultitraker.DataBase.CommandDb.RegarExecuteDb;
import com.example.cultitraker.Models.Enums.TipoInsumo;
import com.example.cultitraker.Models.Regar;
import com.example.cultitraker.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RiegoRegistroFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RiegoRegistroFrag extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner spinnerTipoInsumo;
    private EditText fechaInsumo;
    private MaterialButton btnFechaInsumo;
    private RegarExecuteDb regarExecuteDb;
    private EditText fecha;
    private EditText hora;
    private EditText cantidadAgua;
    private EditText tipoRiego;
    private Button btnGuardar;
    private Button btnCancelar;
    private boolean isEdit;
    private int id;

    public RiegoRegistroFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_riego_registro.
     */
    // TODO: Rename and change types and number of parameters
    public static RiegoRegistroFrag newInstance(String param1, String param2) {
        RiegoRegistroFrag fragment = new RiegoRegistroFrag();
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
        View view = inflater.inflate(R.layout.fragment_riego_registro, container, false);

        fechaInsumo = view.findViewById(R.id.txt_FechaInsumo);
        btnFechaInsumo = view.findViewById(R.id.btn_FechaInsumo);

        TipoInsumo[] tipoInsumo = TipoInsumo.values();
        ArrayAdapter<TipoInsumo> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, tipoInsumo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btnFechaInsumo.setOnClickListener(v -> {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Selecciona una fecha")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            materialDatePicker.addOnPositiveButtonClickListener((MaterialPickerOnPositiveButtonClickListener<Long>) selection -> {
                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selection));
                fechaInsumo.setText(MessageFormat.format("{0}", date));
            });

            materialDatePicker.show(getParentFragmentManager(), "tag");
        });

        initComponent(view);
        return view;
    }

    private void initComponent(View view) {
        regarExecuteDb = new RegarExecuteDb(requireContext());
        fecha = view.findViewById(R.id.txt_FechaInsumo);
        hora = view.findViewById(R.id.txt_Hora);
        cantidadAgua = view.findViewById(R.id.txt_CantidadAgua);
        tipoRiego = view.findViewById(R.id.txt_TipoRiego);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        btnCancelar = view.findViewById(R.id.btn_salir);

        isEdit = getArguments() != null && getArguments().getBoolean("isEdit", false);
        id = getArguments() != null ? getArguments().getInt("id", 0) : 0;

        if (isEdit) {
            cargarDatosRiego();
        }

        inicializarEventos();
    }

    private void inicializarEventos() {
        btnGuardar.setOnClickListener(v -> registrarRiego());
        btnCancelar.setOnClickListener(v -> cancelar());
    }

    private void cargarDatosRiego() {
        ArrayList<Regar> riegos = regarExecuteDb.consultarPorId(id);
        Regar riego = riegos.get(0);

        if (riego != null) {
            fecha.setText(riego.getFecha());
            hora.setText(riego.getHora());
            cantidadAgua.setText(String.valueOf(riego.getCantidadAgua()));
            tipoRiego.setText(riego.getMetodoRiego());
            /*
            ArrayAdapter adapter = (ArrayAdapter) spinnerTipoInsumo.getAdapter();
            int position = adapter.getPosition(riego.get());
            spinnerCultivo.setSelection(position);
             */
        } else {
            Toast.makeText(requireContext(), "No se encontraron datos", Toast.LENGTH_LONG).show();
        }
    }

    private Regar crearRiego() {
        Regar riego = new Regar();
        riego.setId(isEdit ? id : 0);
        riego.setFecha(fecha.getText().toString());
        riego.setHora(hora.getText().toString());
        riego.setCantidadAgua(Double.parseDouble(cantidadAgua.getText().toString()));
        riego.setMetodoRiego(tipoRiego.getText().toString());
        return riego;
    }

    private void registrarRiego(){
        Regar riego = crearRiego();
        boolean isValido;
        if (isEdit) {
            isValido = regarExecuteDb.actualizarDatos(riego);
        } else {
            isValido = regarExecuteDb.agregarDatos(riego);
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
        fecha.setText("");
        hora.setText("");
        cantidadAgua.setText("");
        tipoRiego.setText("");
    }

    private void cancelar() {
        limpiar();
        ventanaPrincipal();
    }

    private void ventanaPrincipal() {
        RiegoFrag nuevoFragment = new RiegoFrag();
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frl_principal, nuevoFragment)
                .addToBackStack(null)
                .commit();
    }

}