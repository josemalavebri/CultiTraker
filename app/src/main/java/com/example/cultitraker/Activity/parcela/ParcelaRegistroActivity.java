package com.example.cultitraker.Activity.parcela;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cultitraker.DataBase.CommandDb.CultivoExecuteDb;
import com.example.cultitraker.DataBase.CommandDb.ParcelaExecuteDb;
import com.example.cultitraker.Models.Cultivo;
import com.example.cultitraker.Models.ParcelaTierra;
import com.example.cultitraker.R;

import java.util.ArrayList;

public class ParcelaRegistroActivity extends AppCompatActivity {


    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parcela_registro);
        llenarDatosSpinner();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void RegistrarParcelaButtonAction(View view){
        ParcelaTierra parcela = crearParcelaData();
        ParcelaExecuteDb parcelaExecuteDb = new ParcelaExecuteDb(this);
        boolean isValid = parcelaExecuteDb.agregarDatos(parcela);
        if(isValid){
            Toast.makeText(this,"Parcela Guardada con Exito",Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this,"Parcela no Guardada",Toast.LENGTH_SHORT).show();
        }
    }

    private ParcelaTierra crearParcelaData(){
        ParcelaTierra parcela = new ParcelaTierra();
        EditText nombre = findViewById(R.id.txt_nombreParcela);
        EditText tamano = findViewById(R.id.txt_tamanoParcela);
        EditText cantidadCultivo = findViewById(R.id.txt_cantidadParcela);

        String nombreParcela = nombre.getText().toString();
        String cultivoParcela = "";
        String tamanoParcela = tamano.getText().toString();
        String cantidadCultivoParcela = cantidadCultivo.getText().toString();

        parcela.setNombre(nombreParcela);
        parcela.setTamano(Integer.parseInt(tamanoParcela));
        parcela.setCultivo(cultivoParcela);
        parcela.setCantidadCultivo(Integer.parseInt(cantidadCultivoParcela));
        return parcela;
    }

    public void cancelar(View view){
        Intent intent = new Intent(this,ParcelaActivity.class);
        startActivity(intent);
    }

    private ArrayList<Cultivo> traerDatosSpinnerDB(){
        CultivoExecuteDb cultivoExecuteDb = new CultivoExecuteDb(this);
        return cultivoExecuteDb.consultarDatos();
    }

    private void llenarDatosSpinner(){
        ArrayList<Cultivo> cultivos = traerDatosSpinnerDB();
        llenarSpinner(cultivos);
    }

    private void llenarSpinner(ArrayList<Cultivo> cultivos){

        spinner = findViewById(R.id.spn_cultivos);

        ArrayAdapter<Cultivo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cultivos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cultivo c = (Cultivo) parent.getItemAtPosition(position);
                int idProducto = c.getId(); // Aquí tienes el ID del producto
                Log.d("Cultivo", "ID seleccionado: " + idProducto);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

}

