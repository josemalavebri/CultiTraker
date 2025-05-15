package com.example.cultitraker.DataBase.CommandDb;

import android.content.Context;
import android.database.Cursor;

import com.example.cultitraker.DataBase.DbSqlLite.ExecuteDb;
import com.example.cultitraker.Models.Cultivo;
import com.example.cultitraker.Models.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CultivoExecuteDb {
    private String[] columnas = {"id", "nombre", "tipo", "fechaSiembra"};
    private ExecuteDb executeDb ;
    private final String TABLENAME = "cultivo";
    public  CultivoExecuteDb(Context c) {
        executeDb = new ExecuteDb(c);
    }

    public boolean agregarDatos(Cultivo cultivo){
        Map<String, Object> datos = new HashMap<>();
        datos.put(columnas[1], cultivo.getNombre());
        datos.put(columnas[2], cultivo.getTipo());
        datos.put(columnas[3], cultivo.getFechaSiembra());
        return executeDb.agregarRegistroDB(TABLENAME, datos);
    }

    public ArrayList<Cultivo> consultarDatos() {
        Cursor data =  executeDb.consultarDatos(TABLENAME);
        return cursorToList(data);
    }

    public List<Cultivo> consultarPorId(int id) {
        String condicion = "Id = ?";
        Cursor data = executeDb.consultarDatosCondicion(TABLENAME, columnas, condicion, new String[]{String.valueOf(id)});
        return cursorToList(data) ;
    }

    public boolean eliminarDatos(int id){
        return executeDb.eliminarDatos(TABLENAME,id);
    }

    private ArrayList<Cultivo> cursorToList(Cursor data) {
        ArrayList<Cultivo> cultivos = new ArrayList<>();
        if (data.moveToFirst()) {
            do {

                int idxId = data.getColumnIndex("id");
                int idxNombre = data.getColumnIndex("nombre");
                int idxTipo = data.getColumnIndex("tipo");
                int idxFechaSiembra = data.getColumnIndex("fechaSiembra");

                if (idxId != -1 && idxNombre  != -1 && idxTipo!= -1  && idxFechaSiembra!= -1) {
                    String id = data.getString(0);
                    String nombre = data.getString(idxNombre);
                    String tipo= data.getString(idxTipo);
                    String fechaSiembra= data.getString(idxFechaSiembra);
                    int idConvert = Integer.parseInt(id);
                    cultivos.add(new Cultivo(idConvert, nombre, tipo, fechaSiembra));
                }
            } while (data.moveToNext());
        }
        return cultivos;
    }


}

