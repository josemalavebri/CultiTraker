package com.example.cultitraker.Activity.insumo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cultitraker.DataBase.CommandDb.InsumoExecuteDB;
import com.example.cultitraker.Models.Enums.TipoInsumo;
import com.example.cultitraker.Models.Insumo;
import com.example.cultitraker.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsumoRegistroFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsumoRegistroFrag extends Fragment {

    private InsumoExecuteDB insumoExecuteDB;
    private TipoInsumo[] tipoInsumo;
    private Spinner spinner;
    private MaterialButton button;
    private EditText fecha;
    private EditText nombre;
    private EditText proveedor;
    private EditText cantidad;
    private Spinner tipo;
    private boolean isEdit;
    private int id;
    private Button btn_Guardar;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InsumoRegistroFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InsumoRegistroFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static InsumoRegistroFrag newInstance(int id, boolean isEdit) {
        InsumoRegistroFrag fragment = new InsumoRegistroFrag();
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putBoolean("isEdit", isEdit);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insumo_registro, container, false);

        initComponent(view);
        inicializarEventos(view);
        return view;
    }

    public void inicializarEventos(View view){
        button.setOnClickListener(v -> {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Selecciona una fecha")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            materialDatePicker.addOnPositiveButtonClickListener((MaterialPickerOnPositiveButtonClickListener<Long>) selection -> {
                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selection));
                fecha.setText(MessageFormat.format("{0}", date));
            });

            materialDatePicker.show(getParentFragmentManager(), "tag");
        });


        Button btnAgregar = view.findViewById(R.id.btn_GuardarInsumo);
        btnAgregar.setOnClickListener(v -> {
            RegistrarInsumo();
        });

        Button btnCancelar = view.findViewById(R.id.btn_CancelarRegistro);
        btnCancelar.setOnClickListener(v ->{
            cancelar();
        });



    }

    private void initComponent(View view){
        insumoExecuteDB = new InsumoExecuteDB(getContext());
        tipoInsumo = TipoInsumo.values();
        spinner = view.findViewById(R.id.txt_TipoInsumo);
        button = view.findViewById(R.id.btn_FechaInsumo);
        nombre = view.findViewById(R.id.txt_NombreInsumo);
        cantidad = view.findViewById(R.id.txt_CantidadInsumo);
        proveedor = view.findViewById(R.id.txt_ProveInsumo);
        tipo = view.findViewById(R.id.txt_TipoInsumo);
        fecha =view.findViewById(R.id.txt_FechaInsumo);
        btn_Guardar = view.findViewById(R.id.btn_GuardarInsumo);
        isEdit = getArguments().getBoolean("isEdit",false);
        id=getArguments().getInt("id",0);
        tiposInsumos();
        if (isEdit) agregarDatosForm();
    }

    @SuppressLint("SetTextI18n")
    private void agregarDatosForm(){
        ArrayList<Insumo> insumos=insumoExecuteDB.consultarPorId(id);
        if(insumos!=null){
            btn_Guardar.setText("Actualizar");
            nombre.setText(insumos.get(0).getNombre());
            cantidad.setText(String.valueOf(insumos.get(0).getCantidad()));
            fecha.setText(insumos.get(0).getFecha());
            proveedor.setText(insumos.get(0).getProveedor());
            String tipoGuardado = insumos.get(0).getTipo();
            SpinnerAdapter adapter = tipo.getAdapter();

            for (int i = 0; i < adapter.getCount(); i++) {
                Object item = adapter.getItem(i);
                if (item instanceof TipoInsumo && item.toString().equals(tipoGuardado)) {
                    tipo.setSelection(i);
                    break;
                }
            }
        }
        else {
            Toast.makeText(requireContext(),"No se encontraron datos",Toast.LENGTH_LONG).show();
        }
    }

    private void tiposInsumos(){
        List<Object> listaInsumos = new ArrayList<>();
        listaInsumos.add("Seleccione un insumo");
        listaInsumos.addAll(Arrays.asList(tipoInsumo));
        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(requireContext(), android.R.layout.simple_spinner_item, listaInsumos){
            @Override
            public boolean isEnabled(int position){
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY); // Color gris para indicar que está deshabilitado
                } else {
                    tv.setTextColor(Color.BLACK); // Color normal
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        button.setOnClickListener(v -> {
            MaterialDatePicker<Long>material=MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Selecciona una fecha")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();
            material.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                @Override
                public void onPositiveButtonClick(Long selection) {
                    String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selection));
                    fecha.setText(MessageFormat.format("{0}",date));
                }
            });
            material.show(getParentFragmentManager(),"tag");
        });
    }
    private Insumo crearInsumo(){
        Insumo insumo = new Insumo();
        insumo.setId(isEdit?id:0);
        insumo.setNombre(nombre.getText().toString());
        insumo.setTipo(tipo.getSelectedItem().toString());
        insumo.setCantidad(Integer.parseInt(cantidad.getText().toString()));
        insumo.setFecha(fecha.getText().toString());
        insumo.setProveedor(proveedor.getText().toString());
        return insumo;
    }


    public void RegistrarInsumo(){
        Insumo insumo = crearInsumo();
        boolean isValido=isEdit?insumoExecuteDB.actualizarDatos(insumo):insumoExecuteDB.agregarDatos(insumo);
        if(isValido){
            Toast.makeText(requireContext(), "Registro"+(isEdit?" actualizado":" guardado"), Toast.LENGTH_LONG).show();
            limpiar();
            ventanaPrincipal();
        }
        else {
            Toast.makeText(requireContext(),"Insumo no Guardado",Toast.LENGTH_LONG).show();
        }
        isEdit=false;

    }
    private void ventanaPrincipal(){
        InsumoFrag nuevoFragment = new InsumoFrag();
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frl_principal, nuevoFragment)
                .addToBackStack(null)
                .commit();

    }


    private void limpiar(){
        nombre.setText("");
        tipo.setSelection(0);
        cantidad.setText("");
        fecha.setText("");
        proveedor.setText("");
    }

    public void cancelar(){
        limpiar();
        ventanaPrincipal();
    }



}