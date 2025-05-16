package com.example.cultitraker.DataBase.CommandDb;

import android.content.Context;
import android.database.Cursor;

import com.example.cultitraker.DataBase.DbSqlLite.ExecuteDb;
import com.example.cultitraker.Models.Tareas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TareasExecuteDb {
    private String[] columnas = {"id", "tipoActividad", "descripcion", "estado", "fecha"};
    private ExecuteDb executeDb;
    private final String TABLENAME = "tareas";

    public TareasExecuteDb(Context c) { executeDb = new ExecuteDb(c); }

    public boolean agregarDatos(Tareas tarea) {
        Map<String, Object> datos = new HashMap<>();
        datos.put(columnas[1], tarea.getTipoActividad());
        datos.put(columnas[2], tarea.getDescripcion());
        datos.put(columnas[3], tarea.getEstado());
        datos.put(columnas[4], tarea.getFecha());
        return executeDb.agregarRegistroDB(TABLENAME, datos);
    }
    public ArrayList<Tareas> consultarDatos() {
        Cursor data = executeDb.consultarDatos(TABLENAME);
        return cursorToList(data);
    }
    public List<Tareas> consultarPorId(int id) {
        String condicion = "id = ?";
        Cursor data = executeDb.consultarDatosCondicion(TABLENAME, columnas, condicion, new String[]{String.valueOf(id)});
        return cursorToList(data);
    }
    public boolean eliminarDatos(int id) {
        return executeDb.eliminarDatos(TABLENAME, id);
    }

    private ArrayList<Tareas> cursorToList(Cursor data) {
        ArrayList<Tareas> tareasList = new ArrayList<>();

        if (data.moveToFirst()) {
            do {
                int idxId = data.getColumnIndex("id");
                int idxTipo = data.getColumnIndex("tipoActividad");
                int idxDesc = data.getColumnIndex("descripcion");
                int idxEstado = data.getColumnIndex("estado");
                int idxFecha = data.getColumnIndex("fecha");

                if (idxId != -1 && idxTipo != -1 && idxDesc != -1 && idxEstado != -1 && idxFecha != -1) {
                    int id = data.getInt(idxId);
                    String tipo = data.getString(idxTipo);
                    String descripcion = data.getString(idxDesc);
                    String estado = data.getString(idxEstado);
                    String fecha = data.getString(idxFecha);

                    tareasList.add(new Tareas(id, tipo, descripcion, estado, fecha));
                }
            } while (data.moveToNext());
        }

        return tareasList;
    }
}
