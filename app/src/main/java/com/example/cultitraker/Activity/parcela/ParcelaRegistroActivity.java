package com.example.cultitraker.Activity.parcela;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cultitraker.DataBase.CommandDb.ParcelaExecuteDb;
import com.example.cultitraker.Models.Parcela;
import com.example.cultitraker.R;

public class ParcelaRegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parcela_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void RegistrarParcelaButtonAction(View view){
        Parcela parcela = crearParcelaData();
        ParcelaExecuteDb parcelaExecuteDb = new ParcelaExecuteDb(this);
        boolean isValid = parcelaExecuteDb.agregarDatos(parcela);
        if(isValid){
            Toast.makeText(this,"Parcela Guardada con Exito",Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this,"Parcela no Guardada",Toast.LENGTH_SHORT).show();
        }
    }

    private Parcela crearParcelaData(){
        Parcela parcela = new Parcela();
        EditText nombre = findViewById(R.id.txt_cantidadParcela);
        EditText cultivo = findViewById(R.id.txt_);
        EditText tamano = findViewById(R.id.txt_);
        EditText cantidadCultivo = findViewById(R.id.txt_cantidadParcela);
        String nombreParcela = nombre.getText().toString();
        String cultivoParcela = cultivo.getText().toString();
        String tamanoParcela = tamano.getText().toString();
        String cantidadCultivoParcela = cantidadCultivo.getText().toString();
        parcela.setNombre(nombreParcela);
        parcela.setTamano(Integer.parseInt(tamanoParcela));
        parcela.setCultivo(cultivoParcela);
        parcela.setCantidadCultivo(Integer.parseInt(cantidadCultivoParcela));
        return parcela;
    }



}