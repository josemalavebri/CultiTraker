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

import com.example.cultitraker.Activity.Cultivo.CultivoActivity;
import com.example.cultitraker.Activity.parcela.ParcelaActivity;
import com.example.cultitraker.DataBase.CommandDb.CultivoExecuteDb;
import com.example.cultitraker.DataBase.CommandDb.ParcelaExecuteDb;
import com.example.cultitraker.DataBase.CommandDb.UsuarioExecuteDb;
import com.example.cultitraker.Models.Cultivo;
import com.example.cultitraker.Models.ParcelaTierra;
import com.example.cultitraker.Models.Usuario;

import java.util.ArrayList;

public class IniciarSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iniciar_sesion);
        logDatos();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void logDatos(){
        CultivoExecuteDb db = new CultivoExecuteDb(this);
        db.agregarDatos(new Cultivo(1, "Zapallo", "Tipo" ,"2024-05-01"));

        ArrayList<Cultivo> cultivo = db.consultarDatos();
        System.out.println(cultivo.get(0));
    }

    public void registrarseButtonAction(View view){
        Intent intent = new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }

    public void loginButtonAction(View view){
        Usuario usuario = crearUsuarioData();
        Intent intent = new Intent(this, ParcelaActivity.class);
        UsuarioExecuteDb usuarioExecuteDb = new UsuarioExecuteDb(this);
        boolean isValid = usuarioExecuteDb.consultarPorEmailPassword(usuario);
        if(isValid){
            startActivity(intent);
            Toast.makeText(this,"Sesion Iniciada",Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(this,"Sesion Fallida",Toast.LENGTH_LONG).show();
        }
    }

    private Usuario crearUsuarioData(){
        Usuario usuario = new Usuario();
        EditText email = findViewById(R.id.txt_email);
        String emailTexto = email.getText().toString();
        usuario.setEmail(emailTexto) ;

        EditText password = findViewById(R.id.txt_password);
        String passwordTexto = email.getText().toString();
        usuario.setEmail(passwordTexto) ;
        return usuario;
    }

}