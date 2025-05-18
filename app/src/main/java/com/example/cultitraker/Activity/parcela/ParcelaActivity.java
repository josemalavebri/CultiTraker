package com.example.cultitraker.Activity.parcela;

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
import com.example.cultitraker.DataBase.CommandDb.CultivoExecuteDb;
import com.example.cultitraker.DataBase.CommandDb.ParcelaExecuteDb;
import com.example.cultitraker.Models.Cultivo;
import com.example.cultitraker.Models.Parcela;
import com.example.cultitraker.R;

import java.util.ArrayList;

public class ParcelaActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.);

        //PONER SUS RECYCLER VIEW CON SU ID
        recyclerView = findViewById(R.id.recyclerViewParcelas);

        //LINEA OBLIGATORIA
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarDatosParcela();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void cargarDatosParcela(){
        ArrayList<Parcela> parcelas = cargarDatosParcelaDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();

        for (Parcela parcela : parcelas){
            AdapterModel adapterModel = new AdapterModel();

            adapterModel.setTitulo(parcela.getNombre());
            adapterModel.setSubTitulo(parcela.getCultivo());
            adapterModel.setParrafo(""+parcela.getTamano());
            adapterModel.setDetail(""+parcela.getCantidadCultivo());
            adapterModels.add(adapterModel);
        }

        //ENVIAR SUS CARD ITEMS CON SU ID
        AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, this, R.layout.card_item_parcela);
        recyclerView.setAdapter(adapterGeneral);
    }

    private ArrayList<Parcela> cargarDatosParcelaDB(){
        ParcelaExecuteDb parcelaExecuteDb = new ParcelaExecuteDb(this);
        return parcelaExecuteDb.consultarDatos();
    }

    public void agregarActionButton(View view){
        Intent intent = new Intent(this, ParcelaRegistroActivity.class);
        startActivity(intent);
    }


}