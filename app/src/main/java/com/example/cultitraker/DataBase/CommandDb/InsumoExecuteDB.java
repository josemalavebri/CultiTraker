package com.example.cultitraker.DataBase.CommandDb;

import android.content.Context;
import android.database.Cursor;

import com.example.cultitraker.DataBase.DbSqlLite.ExecuteDb;
import com.example.cultitraker.Models.Insumo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InsumoExecuteDB {
    private final String[]columnas={"ID","Nombre","Tipo","Cantidad","Fecha","Proveedor",};
    private final ExecuteDb executeDb;
    private final String TABLENAME="insumo";
    public InsumoExecuteDB(Context c){
        executeDb=new ExecuteDb(c);
    }
    public boolean agregarDatos(Insumo insumo){
        Map<String,Object>datos=new HashMap<>();
        datos.put(columnas[1], insumo.getNombre());
        datos.put(columnas[2], insumo.getTipo());
        datos.put(columnas[3], insumo.getCantidad());
        datos.put(columnas[4], insumo.getFecha());
        datos.put(columnas[5], insumo.getProveedor());
        return executeDb.agregarRegistroDB(TABLENAME,datos);
    }
    private ArrayList<Insumo>cursorToList(Cursor data){
        ArrayList<Insumo>insumos=new ArrayList<>();
        if(data.moveToFirst()){
            do{
                int idxID=data.getColumnIndex(columnas[0]);
                int idxNombre=data.getColumnIndex(columnas[1]);
                int idxTipo=data.getColumnIndex(columnas[2]);
                int idxCantidad=data.getColumnIndex(columnas[3]);
                int idxFecha=data.getColumnIndex(columnas[4]);
                int idxProveedor=data.getColumnIndex(columnas[5]);
                int [] datos = {idxID,idxNombre,idxTipo,idxCantidad,idxFecha,idxProveedor};
                boolean todosValidos= Arrays.stream(datos).allMatch(v->v!=-1);
                if (todosValidos){
                    int id = data.getInt(idxID);
                    String nombre =data.getString(idxNombre);
                    String tipo = data.getString(idxTipo);
                    int cantidad = data.getInt(idxCantidad);
                    String fecha = data.getString(idxFecha);
                    String proveedor = data.getString(idxProveedor);
                    insumos.add(new Insumo(id,nombre,tipo,cantidad,fecha,proveedor));
                }
            }while (data.moveToNext());
        }
        return insumos;
    }
}
