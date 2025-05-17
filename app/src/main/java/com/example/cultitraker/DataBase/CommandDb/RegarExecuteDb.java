package com.example.cultitraker.DataBase.CommandDb;

import android.content.Context;
import android.database.Cursor;

import com.example.cultitraker.DataBase.DbSqlLite.ExecuteDb;
import com.example.cultitraker.Models.Regar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegarExecuteDb {
    private String[] columnas = {"id", "fecha", "hora", "cantidadAgua", "metodoRiego", "parcelaId"};
    private ExecuteDb executeDb ;
    private final String TABLENAME = "regar";
    public RegarExecuteDb(Context c) {
        executeDb = new ExecuteDb(c);
    }

    public boolean agregarDatos(Regar regar){
        Map<String, Object> datos = new HashMap<>();
        datos.put(columnas[1], regar.getFecha());
        datos.put(columnas[2], regar.getHora());
        datos.put(columnas[3], regar.getCantidadAgua());
        datos.put(columnas[4], regar.getMetodoRiego());
        datos.put(columnas[5], regar.getParcelaId());
        return executeDb.agregarRegistroDB(TABLENAME, datos);
    }

    public ArrayList<Regar> consultarDatos() {
        Cursor data =  executeDb.consultarDatos(TABLENAME);
        return cursorToList(data);
    }

    public List<Regar> consultarPorId(int id) {
        String condicion = "Id = ?";
        Cursor data = executeDb.consultarDatosCondicion(TABLENAME, columnas, condicion, new String[]{String.valueOf(id)});
        return cursorToList(data) ;
    }

    public boolean eliminarDatos(int id){
        return executeDb.eliminarDatos(TABLENAME,id);
    }

    private ArrayList<Regar> cursorToList(Cursor data) {
        ArrayList<Regar> listRegar = new ArrayList<>();
        if (data.moveToFirst()) {
            do {
                int idxId = data.getColumnIndex("id");
                int fechaxFecha = data.getColumnIndex("fecha");
                int idxHora = data.getColumnIndex("hora");
                int idxCantidadAgua = data.getColumnIndex("cantidadAgua");
                int idxMetodoRiesgo = data.getColumnIndex("metodoRiego");
                int idxParcelaId = data.getColumnIndex("parcelaId");

                if (idxId != -1 && fechaxFecha != -1 && idxHora != -1 && idxCantidadAgua != -1 && idxMetodoRiesgo != -1 && idxParcelaId != -1) {
                    String idTexto = data.getString(idxId);
                    String fecha = data.getString(fechaxFecha);
                    String hora = data.getString(idxHora);
                    String cantidadAguaTexto = data.getString(idxCantidadAgua);
                    String metodoRiesgo = data.getString(idxMetodoRiesgo);
                    String parcelaIdTexto = data.getString(idxParcelaId);
                    int id = Integer.parseInt(idTexto);
                    int cantidadAgua = Integer.parseInt(cantidadAguaTexto);
                    int parcelaId = Integer.parseInt(parcelaIdTexto);
                    listRegar.add(new Regar(id,fecha,hora,cantidadAgua,metodoRiesgo,parcelaId));
                }
            } while (data.moveToNext());
        }
        return listRegar;
    }
}
