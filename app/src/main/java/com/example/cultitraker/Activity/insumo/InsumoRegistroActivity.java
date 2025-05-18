package com.example.cultitraker.Activity.insumo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

public class InsumoRegistroActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insumo_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponent();
    }
    @SuppressLint("CutPasteId")
    private void initComponent(){
        insumoExecuteDB = new InsumoExecuteDB(this);
        tipoInsumo = TipoInsumo.values();
        spinner = findViewById(R.id.txt_TipoInsumo);
        button = findViewById(R.id.btn_FechaInsumo);
        nombre = findViewById(R.id.txt_NombreInsumo);
        cantidad = findViewById(R.id.txt_CantidadInsumo);
        proveedor = findViewById(R.id.txt_ProveInsumo);
        tipo = findViewById(R.id.txt_TipoInsumo);
        fecha =findViewById(R.id.txt_FechaInsumo);
        isEdit = getIntent().getBooleanExtra("isEdit",false);
        id=getIntent().getIntExtra("id",0);
        tiposInsumos();
        if (isEdit) agregarDatosForm();
    }
    private void agregarDatosForm(){
        ArrayList<Insumo>insumos=insumoExecuteDB.consultarPorId(id);
        if(insumos!=null){
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
            Toast.makeText(this,"No se encontraron datos",Toast.LENGTH_LONG).show();
        }
    }
    private void tiposInsumos(){
        List<Object> listaInsumos = new ArrayList<>();
        listaInsumos.add("Seleccione un insumo");
        listaInsumos.addAll(Arrays.asList(tipoInsumo));
        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_spinner_item, listaInsumos){
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
            material.show(getSupportFragmentManager(),"tag");
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
    public void RegistrarInsumo(View v){
        Insumo insumo = crearInsumo();
        boolean isValido=isEdit?insumoExecuteDB.actualizarDatos(insumo):insumoExecuteDB.agregarDatos(insumo);
        if(isValido){
            Toast.makeText(this, "Registro"+(isEdit?" actualizado":" guardado"), Toast.LENGTH_LONG).show();
            limpiar();
            ventanaPrincipal();
        }
        else {
            Toast.makeText(this,"Insumo no Guardado",Toast.LENGTH_LONG).show();
        }
        isEdit=false;
        //Log.d("Insumo", insumo.toString());
//        boolean isValid = insumoExecuteDB.agregarDatos(insumo);
//        if(isValid){
//            Toast.makeText(this,"Insumo Guardado con Exito",Toast.LENGTH_LONG).show();
//            limpiar();
//            Intent intent = new Intent(this, InsumoActivity.class);
//            startActivity(intent);
//        } else{
//            Toast.makeText(this,"Insumo no Guardado",Toast.LENGTH_LONG).show();
//        }
    }
    private void ventanaPrincipal(){
        Intent intent = new Intent(this, InsumoActivity.class);
        startActivity(intent);
    }
    private void limpiar(){
        nombre.setText("");
        tipo.setSelection(0);
        cantidad.setText("");
        fecha.setText("");
        proveedor.setText("");
    }
    public void cancelar(View v){
        limpiar();
        ventanaPrincipal();
    }
}