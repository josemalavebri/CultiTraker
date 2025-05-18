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
    private int id;
    private InsumoExecuteDB insumoExecuteDB;
    private RecyclerView recyclerView;
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
        id=0;
        insumoExecuteDB = new InsumoExecuteDB(this);
        recyclerView = findViewById(R.id.recyclerViewInsumo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cargarDatosInsumo();
    }
    private ArrayList<Integer>idInsumos;
    public void cargarDatosInsumo(){
        ArrayList<Insumo> insumos = cargarDatosInsumoDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();
        idInsumos=new ArrayList<>();
        for(Insumo insumo : insumos){
            AdapterModel adapterModel = new AdapterModel();
            //int [] ids= new int[]{insumo.getId()};
            idInsumos.add(insumo.getId());
            adapterModel.setTitulo(insumo.getNombre());
            adapterModel.setSubTitulo(insumo.getTipo());
            adapterModel.setParrafo(String.valueOf(insumo.getCantidad()));
            adapterModel.setDetail(insumo.getProveedor());
            adapterModels.add(adapterModel);
            //Log.d("IDS", "ids: " + Arrays.toString(ids));
        }
        //ENVIAR SUS CARD ITEMS CON SU ID
        AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, this, R.layout.card_item);
        adapterGeneral.setOnItemClickListener(v -> {
            int position = (int) v.getTag();
            int id = idInsumos.get(position);
            Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
        });
        recyclerView.setAdapter(adapterGeneral);

    }
    private ArrayList<Insumo>cargarDatosInsumoDB(){
        return insumoExecuteDB.consultarInsumos();
    }
    public void Agregar(View v){
        Intent intent = new Intent(this, InsumoRegistroActivity.class);
        startActivity(intent);
    }
    private void ventanaRegistro(View V){
        Intent intent = new Intent(this, InsumoRegistroActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("isEdit",true);
        startActivity(intent);
    }
}