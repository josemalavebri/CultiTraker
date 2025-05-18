package com.example.cultitraker.Activity.regar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cultitraker.Models.Regar;
import com.example.cultitraker.R;

public class RegarFormularioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_regar_formulario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void agregarDatos(View view){
        /*
        Regar regar = new Regar();
        EditText txtHora = findViewById(R.id.txt_Hora);
        EditText txtCantidadAgua = findViewById(R.id.txt_CantidadAgua);
        EditText txtTipoRiego = findViewById(R.id.txt_TipoRiego);
        String hora = txtHora.getText().toString();
        */
    }
}