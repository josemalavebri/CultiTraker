package com.example.cultitraker;

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

import com.example.cultitraker.DataBase.CommandDb.UsuarioExecuteDb;
import com.example.cultitraker.Models.Usuario;

public class RegistroActivity extends AppCompatActivity {
    private UsuarioExecuteDb usuarioExecuteDb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private Usuario getUsuario(){
        usuarioExecuteDb = new UsuarioExecuteDb(this);
        Usuario usuario = new Usuario();
        usuario.setId(0);
        usuario.setEmail(((EditText)findViewById(R.id.txt_correoRegistro)).getText().toString());
        usuario.setPassword(((EditText)findViewById(R.id.txt_contrasenaRegistro)).getText().toString());
        return usuario;
    }

    public void guardarBD(View view){
        Usuario usuario = getUsuario();
        boolean resultado = usuarioExecuteDb.agregarDatos(usuario);
        if (resultado)
            Toast.makeText(this, "Registro guardado con exito", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_LONG).show();
        reseteoObjetos();
    }


    public void cancelarRegistro(View view){
        Intent intent = new Intent(this,IniciarSesion.class);
        reseteoObjetos();
        startActivity(intent);
    }
    private void reseteoObjetos(){
        ((EditText)findViewById(R.id.txt_correoRegistro)).setText("");
        ((EditText)findViewById(R.id.txt_contrasenaRegistro)).setText("");
        ((EditText)findViewById(R.id.txt_contrasenaRegistroConfirmar)).setText("");

    }


}
