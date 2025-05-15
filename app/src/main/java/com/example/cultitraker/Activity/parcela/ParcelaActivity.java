package com.example.cultitraker.Activity.parcela;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultitraker.AdapterItems.AdapterGeneral;
import com.example.cultitraker.AdapterItems.AdapterModel;
import com.example.cultitraker.DataBase.CommandDb.UsuarioExecuteDb;
import com.example.cultitraker.Models.Usuario;
import com.example.cultitraker.R;

import java.util.ArrayList;

public class ParcelaActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parcela);
        recyclerView = findViewById(R.id.recyclerViewParcelas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cargarDatosParcela();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void cargarDatosParcela(){
        ArrayList<Usuario> usuarios = cargarDatosParcelaDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();
        for (Usuario usuario : usuarios){
            AdapterModel adapterModel = new AdapterModel();
            adapterModel.setTitulo(usuario.getEmail());
            adapterModel.setSubTitulo(usuario.getPassword());
            adapterModels.add(adapterModel);
        }
        AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, this, R.layout.card_item);
        recyclerView.setAdapter(adapterGeneral);
    }

    private ArrayList<Usuario> cargarDatosParcelaDB(){
        UsuarioExecuteDb usuarioExecuteDb = new UsuarioExecuteDb(this);
        ArrayList<Usuario> usuarios = usuarioExecuteDb.consultarDatos();
        return usuarios;
    }
}