package com.example.cultitraker.Activity.insumo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultitraker.AdapterItems.AdapterGeneral;
import com.example.cultitraker.AdapterItems.AdapterModel;
import com.example.cultitraker.DataBase.CommandDb.InsumoExecuteDB;
import com.example.cultitraker.Models.Insumo;
import com.example.cultitraker.R;

import java.util.ArrayList;

public class InsumoActivity extends AppCompatActivity {
    private InsumoExecuteDB insumoExecuteDB;
    private RecyclerView recyclerView;
    private ArrayList<Integer>idInsumos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insumo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponent();
    }
    private void initComponent(){
        insumoExecuteDB = new InsumoExecuteDB(this);
        recyclerView = findViewById(R.id.recyclerViewInsumo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cargarDatosInsumo();
    }
    public void cargarDatosInsumo(){
        ArrayList<Insumo> insumos = cargarDatosInsumoDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();
        idInsumos=new ArrayList<>();
        for(Insumo insumo : insumos){
            AdapterModel adapterModel = new AdapterModel();
            idInsumos.add(insumo.getId());
            adapterModel.setTitulo(insumo.getNombre());
            adapterModel.setSubTitulo(insumo.getTipo());
            adapterModel.setParrafo(String.valueOf(insumo.getCantidad()));
            adapterModel.setDetail(insumo.getProveedor());
            adapterModels.add(adapterModel);
        }
        //ENVIAR SUS CARD ITEMS CON SU ID
        AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, this, R.layout.card_item);
        adapterGeneral.setOnItemClickListener(v -> {
            int position = (int) v.getTag();
            int id = idInsumos.get(position);
            actualizarRegistro(id);
        });
        recyclerView.setAdapter(adapterGeneral);
    }

    private void actualizarRegistro(int id) {
        Intent intent = new Intent(this, InsumoRegistroActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("isEdit",true);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
    }

    private ArrayList<Insumo>cargarDatosInsumoDB(){
        return insumoExecuteDB.consultarInsumos();
    }
    public void agregarRegistro(View v){
        Intent intent = new Intent(this, InsumoRegistroActivity.class);
        startActivity(intent);
    }
}