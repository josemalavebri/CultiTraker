package com.example.cultitraker.Activity.tareas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cultitraker.DataBase.CommandDb.TareasExecuteDb;
import com.example.cultitraker.Models.Tareas;
import com.example.cultitraker.R;

public class TareaRegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tarea_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private Tareas crearTareaData() {
        Tareas tarea = new Tareas();
        EditText tipoActividad = findViewById(R.id.txt_tipoActividad);
        EditText descripcion = findViewById(R.id.txt_descripcionActividad);
        EditText fecha = findViewById(R.id.txt_fechaActividad);
        EditText estado = findViewById(R.id.txt_estadoActividad);

        String tipo = tipoActividad.getText().toString();
        String desc = descripcion.getText().toString();
        String fechaStr = fecha.getText().toString();
        String estadoStr = estado.getText().toString();

        tarea.setTipoActividad(tipo);
        tarea.setDescripcion(desc);
        tarea.setFecha(fechaStr);
        tarea.setEstado(estadoStr);

        return tarea;
    }
    public void RegistrarTareaButtonAction(View view) {
        Tareas tarea = crearTareaData();
        TareasExecuteDb tareasExecuteDb = new TareasExecuteDb(this);

        boolean isValid = tareasExecuteDb.agregarDatos(tarea);
        if (isValid) {
            Toast.makeText(this, "Tarea guardada con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tarea no guardada", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelarNuevaTarea(View view){
        Intent intent = new Intent(this, TareasActivity.class);
        startActivity(intent);
    }
}