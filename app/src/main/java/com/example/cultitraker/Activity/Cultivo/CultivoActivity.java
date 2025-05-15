package com.example.cultitraker.Activity.Cultivo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultitraker.Activity.parcela.ParcelaRegistroActivity;
import com.example.cultitraker.AdapterItems.AdapterGeneral;
import com.example.cultitraker.AdapterItems.AdapterModel;
import com.example.cultitraker.DataBase.CommandDb.CultivoExecuteDb;
import com.example.cultitraker.Models.Cultivo;
import com.example.cultitraker.R;

import java.util.ArrayList;

public class CultivoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cultivo);
        recyclerView = findViewById(R.id.recyclerViewCultivo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cargarDatosParcela();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void cargarDatosParcela(){
        ArrayList<Cultivo> cultivos = cargarDatosParcelaDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();

        for(Cultivo cultivo: cultivos) {
            AdapterModel adapterModel = new AdapterModel();

            adapterModel.setTitulo(cultivo.getNombre());
            adapterModel.setSubTitulo(cultivo.getTipo());
            adapterModel.setParrafo(cultivo.getFechaSiembra());
            adapterModels.add(adapterModel);
        }

        //ENVIAR SUS CARD ITEMS CON SU ID
        AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, this, R.layout.card_item_bloque);
        recyclerView.setAdapter(adapterGeneral);
    }

    private ArrayList<Cultivo> cargarDatosParcelaDB(){
        CultivoExecuteDb cultivoExecuteDb = new CultivoExecuteDb(this);
        return cultivoExecuteDb.consultarDatos();
    }

    public void agregarActionButton(View view){
        Intent intent = new Intent(this, ParcelaRegistroActivity.class);
        startActivity(intent);
    }
}