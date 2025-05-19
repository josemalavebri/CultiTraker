package com.example.cultitraker.DataBase.DbSqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

public class ExecuteDb {
    private SqlLiteDb db;
    public ExecuteDb(Context c){
        db = new SqlLiteDb(c);
    }

    public boolean agregarRegistroDB(String tabla, Map<String, Object> datos) {
        SQLiteDatabase db_Edit = db.getWritableDatabase();
        if(db_Edit==null){
            return false;
        }
        ContentValues cv = new ContentValues();
        for (Map.Entry<String, Object> entry : datos.entrySet()) {
            String columna = entry.getKey();
            Object valor = entry.getValue();
            if (valor instanceof String)
                cv.put(columna, (String) valor);
            else if (valor instanceof Integer)
                cv.put(columna, (Integer) valor);
            else if (valor instanceof Boolean)
                cv.put(columna, (Boolean) valor);
        }
        return db_Edit.insert(tabla, null, cv)>=0;
    }

    public Cursor consultarDatos(String tabla) {
        SQLiteDatabase db_Select = db.getReadableDatabase();
        Cursor data = db_Select.rawQuery("SELECT * FROM " + tabla, null);
        return data;
    }

    public Cursor consultarDatosCondicion(String tabla, String[] columnas, String condicion, String[] args) {
        SQLiteDatabase db_Select = db.getReadableDatabase();
        String columnasString = String.join(", ", columnas);
        Cursor data = db_Select.rawQuery(
                "SELECT " + columnasString + " FROM " + tabla + " WHERE " + condicion,
                args
        );
        return data;
    }

    public boolean actualizarDatos(String tabla, Map<String, Object> datos) {
        int id = -1;
        final SQLiteDatabase db_Edit = db.getWritableDatabase();
        if (db_Edit == null) {
            return false;
        }

        ContentValues cv = new ContentValues();
        for (Map.Entry<String, Object> entry : datos.entrySet()) {
            String columna = entry.getKey();
            Object valor = entry.getValue();

            if (columna.equalsIgnoreCase("id")) {
                id = (Integer) valor;
            }

            if (valor instanceof String) {
                cv.put(columna, (String) valor);
            } else if (valor instanceof Integer) {
                cv.put(columna, (Integer) valor);
            } else if (valor instanceof Boolean) {
                cv.put(columna, (Boolean) valor);
            }
        }
        if (id == -1) {
            return false;
        }
        int filasActualizadas = db_Edit.update(tabla, cv, "id=?", new String[]{String.valueOf(id)});

        return filasActualizadas > 0;
    }

    public boolean eliminarDatos(String tabla, int id){
        SQLiteDatabase db_delete = db.getWritableDatabase();
        if(db_delete== null){
            return false;
        }
        String condicion = "id=?";
        String[] args = {String.valueOf(id)};
        int filasEliminadas = db_delete.delete(tabla, condicion, args);
        return filasEliminadas > 0;
    }

}
