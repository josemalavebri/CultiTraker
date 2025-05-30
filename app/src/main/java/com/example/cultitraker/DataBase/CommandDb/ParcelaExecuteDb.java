package com.example.cultitraker.DataBase.CommandDb;

import android.content.Context;
import android.database.Cursor;

import com.example.cultitraker.DataBase.DbSqlLite.ExecuteDb;
import com.example.cultitraker.Models.Parcela;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParcelaExecuteDb {
    private String[] columnas = {"id", "nombre", "tamano","cultivo","cantidadCultivo"};
    private ExecuteDb executeDb ;
    private final String TABLENAME = "parcelaTierra";
    public ParcelaExecuteDb(Context c) {
        executeDb = new ExecuteDb(c);
    }

    public boolean agregarDatos(Parcela parcela) {
        Map<String, Object> datos = new HashMap<>();
        datos.put(columnas[1], parcela.getNombre());
        datos.put(columnas[2], parcela.getTamano());
        datos.put(columnas[3], parcela.getCultivo());
        datos.put(columnas[4], parcela.getCantidadCultivo());
        return executeDb.agregarRegistroDB(TABLENAME, datos);
    }

    public boolean actualizarDatos(Parcela parcela) {
        Map<String, Object> datos = construirDatosParcela(parcela);
        datos.put(columnas[0], parcela.getId());
        return executeDb.actualizarDatos(TABLENAME, datos);
    }


    private Map<String, Object> construirDatosParcela(Parcela parcela) {
        Map<String, Object> datos = new HashMap<>();
        datos.put(columnas[1], parcela.getNombre());
        datos.put(columnas[2], parcela.getTamano());
        datos.put(columnas[3], parcela.getCultivo());
        datos.put(columnas[4], parcela.getCantidadCultivo());
        return datos;
    }




    public ArrayList<Parcela> consultarDatos() {
        Cursor data =  executeDb.consultarDatos(TABLENAME);
        return cursorToList(data);
    }

    public ArrayList<Parcela> consultarPorId(int id) {
        String condicion = "Id = ?";
        Cursor data = executeDb.consultarDatosCondicion(TABLENAME, columnas, condicion, new String[]{String.valueOf(id)});
        return cursorToList(data) ;
    }

    public boolean eliminarDatos(int id){
        return executeDb.eliminarDatos(TABLENAME,id);
    }

    private ArrayList<Parcela> cursorToList(Cursor data) {
        ArrayList<Parcela> parcelas = new ArrayList<>();

        if (data.moveToFirst()) {
            do {
                int idxId = data.getColumnIndex("id");
                int idxNombre = data.getColumnIndex("nombre");
                int idxTamano = data.getColumnIndex("tamano");
                int idxCultivo = data.getColumnIndex("cultivo");
                int idxCantidad = data.getColumnIndex("cantidadCultivo");

                if (idxId != -1 && idxNombre != -1 && idxTamano != -1 &&
                        idxCultivo != -1 && idxCantidad != -1) {

                    int id = data.getInt(idxId);
                    String nombre = data.getString(idxNombre);
                    int tamano = data.getInt(idxTamano);
                    String cultivo = data.getString(idxCultivo);
                    int cantidadCultivos = data.getInt(idxCantidad);

                    parcelas.add(new Parcela(id, nombre, tamano, cultivo, cantidadCultivos));
                }
            } while (data.moveToNext());
        }

        return parcelas;
    }
}
