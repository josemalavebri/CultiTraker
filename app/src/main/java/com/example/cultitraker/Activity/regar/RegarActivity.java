package com.example.cultitraker.Activity.regar;

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

import com.example.cultitraker.AdapterItems.AdapterGeneral;
import com.example.cultitraker.AdapterItems.AdapterModel;
import com.example.cultitraker.DataBase.CommandDb.RegarExecuteDb;
import com.example.cultitraker.Models.Regar;
import com.example.cultitraker.R;

import java.util.ArrayList;

public class RegarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_regar);
        recyclerView = findViewById(R.id.recyclerViewRegar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cargarDatosRegar();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void cargarDatosRegar(){
        ArrayList<Regar> listRegar = cargarDatosParcelaDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();
        for (Regar regar : listRegar){
            AdapterModel adapterModel = new AdapterModel();
            adapterModel.setTitulo(regar.getFecha()+regar.getHora());
            adapterModel.setSubTitulo(regar.getCantidadAgua() + " L");
            adapterModel.setParrafo(regar.getMetodoRiego());
            adapterModel.setDetail(String.valueOf(regar.getParcelaId()));
            adapterModels.add(adapterModel);
        }
        AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, this, R.layout.card_item);
        recyclerView.setAdapter(adapterGeneral);
    }

    private ArrayList<Regar> cargarDatosParcelaDB(){
        RegarExecuteDb regarExecuteDb = new RegarExecuteDb(this);
        ArrayList<Regar> listRegar = regarExecuteDb.consultarDatos();
        return listRegar;
    }

    public void enviarFormularioRegar(View view){
        Intent intent = new Intent(this, RegarFormularioActivity.class);
        startActivity(intent);
    }
}