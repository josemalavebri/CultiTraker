package com.example.cultitraker;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

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

public class IniciarSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iniciar_sesion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void registrarseButtonAction(View view){
        Intent intent = new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }

    public void loginButtonAction(View view){
        Usuario usuario = crearUsuarioData();
        if(comprobarDatosFormulario(usuario)){
            if(comprobarDatosBDD(usuario)){
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "No existe el usuario, registrese", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean comprobarDatosBDD(Usuario usuario){
        UsuarioExecuteDb usuarioExecuteDb = new UsuarioExecuteDb(this);
        Boolean isValid = usuarioExecuteDb.consultarPorEmailPassword(usuario);
        return isValid;
    }



    private boolean comprobarDatosFormulario(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese un correo electrónico", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private Usuario crearUsuarioData(){
        Usuario usuario = new Usuario();
        EditText email = findViewById(R.id.txt_email);
        String emailTexto = email.getText().toString();
        usuario.setEmail(emailTexto) ;

        EditText password = findViewById(R.id.txt_password);
        String passwordTexto = password.getText().toString();
        usuario.setPassword(passwordTexto); ;
        return usuario;
    }

}