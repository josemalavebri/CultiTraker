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
        ArrayList<Usuario> usuarios = cargarDatosParcelaDB();
        ArrayList<AdapterModel> adapterModels = new ArrayList<>();

        for (Usuario usuario : usuarios){
            AdapterModel adapterModel = new AdapterModel();

            adapterModel.setTitulo(usuario.getEmail());
            adapterModel.setSubTitulo(usuario.getPassword());
            adapterModel.setParrafo("parrafo");
            adapterModel.setDetail("detalles");

            adapterModels.add(adapterModel);
        }
        //ENVIAR SUS CARD ITEMS CON SU ID
        AdapterGeneral adapterGeneral = new AdapterGeneral(adapterModels, this, R.layout.card_item_bloque);
        recyclerView.setAdapter(adapterGeneral);
    }

    private ArrayList<Usuario> cargarDatosParcelaDB(){
        UsuarioExecuteDb usuarioExecuteDb = new UsuarioExecuteDb(this);
        return usuarioExecuteDb.consultarDatos();
    }

    public void agregarActionButton(View view){
        Intent intent = new Intent(this, ParcelaRegistroActivity.class);
        startActivity(intent);
    }
}