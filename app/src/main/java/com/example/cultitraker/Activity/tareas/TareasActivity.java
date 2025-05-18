package com.example.cultitraker.Activity.tareas;

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
import com.example.cultitraker.DataBase.CommandDb.TareasExecuteDb;
import com.example.cultitraker.Models.Tareas;
import com.example.cultitraker.R;

import java.util.ArrayList;

public class TareasActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tareas);
        recyclerView = findViewById(R.id.recyclerViewTareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cargarDatosTarea();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void cargarDatosTarea() {
        ArrayList<Tareas> tareas = cargarDatosTareaDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();
        if(tareas!=null){
            for (Tareas tarea : tareas) {
                AdapterModel adapterModel = new AdapterModel();
                adapterModel.setTitulo(tarea.getTipoActividad());
                adapterModel.setSubTitulo(tarea.getEstado());
                adapterModel.setParrafo(tarea.getDescripcion());
                adapterModel.setDetail(tarea.getFecha());
                adapterModels.add(adapterModel);
            }
//            AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, this, R.layout.card_item_bloque, );
//            recyclerView.setAdapter(adapterGeneral);
        }
    }

    private ArrayList<Tareas> cargarDatosTareaDB(){
        TareasExecuteDb tareaExecuteDb = new TareasExecuteDb(this);
        return tareaExecuteDb.consultarDatos();
    }



    public void agregarActionButton(View view){
        Intent intent = new Intent(this, TareaRegistroActivity.class);
        startActivity(intent);
    }

}
