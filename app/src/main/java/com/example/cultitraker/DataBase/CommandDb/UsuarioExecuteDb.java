package com.example.cultitraker.DataBase.CommandDb;

import android.content.Context;
import android.database.Cursor;

import com.example.cultitraker.DataBase.DbSqlLite.ExecuteDb;
import com.example.cultitraker.Models.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioExecuteDb {
    private String[] columnas = {"id", "email", "password"};
    private ExecuteDb executeDb ;
    private final String TABLENAME = "usuario";
    public UsuarioExecuteDb(Context c) {
        executeDb = new ExecuteDb(c);
    }

    public boolean agregarDatos(Usuario usuario){
        Map<String, Object> datos = new HashMap<>();
        datos.put(columnas[1], usuario.getEmail());
        datos.put(columnas[2], usuario.getPassword());
        return executeDb.agregarRegistroDB(TABLENAME, datos);
    }

    public boolean consultarPorEmailPassword(Usuario usuario) {
        String condicion1 = "email = ?";
        String condicion2 = "password = ?";
        Cursor emailExist = executeDb.consultarDatosCondicion(TABLENAME, columnas, condicion1, new String[]{String.valueOf(usuario.getEmail())});
        if(emailExist != null && emailExist.moveToFirst()){
            Cursor passwordExist = executeDb.consultarDatosCondicion(TABLENAME, columnas, condicion2, new String[]{String.valueOf(usuario.getPassword())});
            if (passwordExist != null && passwordExist.moveToFirst()) {
                return true;
            } else {
                return false;
            }
        } else{
            return false;
        }

    }

    public ArrayList<Usuario> consultarDatos() {
        Cursor data =  executeDb.consultarDatos(TABLENAME);
        return cursorToList(data);
    }

    public List<Usuario> consultarPorId(int id) {
        String condicion = "Id = ?";
        Cursor data = executeDb.consultarDatosCondicion(TABLENAME, columnas, condicion, new String[]{String.valueOf(id)});
        return cursorToList(data) ;
    }



    public boolean eliminarDatos(int id){
        return executeDb.eliminarDatos(TABLENAME,id);
    }

    private ArrayList<Usuario> cursorToList(Cursor data) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        if (data.moveToFirst()) {
            do {

                int idxId = data.getColumnIndex("id");
                int idxPass = data.getColumnIndex("password");
                int idxEmail = data.getColumnIndex("email");

                if (idxId != -1 && idxPass != -1 && idxEmail != -1) {
                    String id = data.getString(0);
                    String password = data.getString(idxPass);
                    String email = data.getString(idxEmail);
                    int idConvert = Integer.parseInt(id);
                    usuarios.add(new Usuario(idConvert, password, email));
                }
            } while (data.moveToNext());
        }
        return usuarios;
    }


}
