package com.example.cultitraker.DataBase.CommandDb;

import android.content.Context;
import android.database.Cursor;

import com.example.cultitraker.DataBase.DbSqlLite.ExecuteDb;
import com.example.cultitraker.Models.Insumo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsumoExecuteDB {
    private String[]columnas={"id","nombre","tipo","cantidad","fecha","proveedor"};
    private ExecuteDb executeDb;
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
    public boolean actualizarDatos(Insumo insumo){
        Map<String,Object>datos=new HashMap<>();
        datos.put(columnas[0], insumo.getId());
        datos.put(columnas[1], insumo.getNombre());
        datos.put(columnas[2], insumo.getTipo());
        datos.put(columnas[3], insumo.getCantidad());
        datos.put(columnas[4], insumo.getFecha());
        return executeDb.actualizarDatos(TABLENAME,datos);
    }
    public ArrayList<Insumo>consultarInsumos(){
        Cursor data=executeDb.consultarDatos(TABLENAME);
        return cursorToList(data);
    }
    public ArrayList<Insumo>consultarPorId(int id){
        String condicion="id=?";
        Cursor data=executeDb.consultarDatosCondicion(TABLENAME,columnas,condicion,new String[]{String.valueOf(id)});
        return cursorToList(data);
    }
    public List<Insumo>consultarPorNombre(String nombre){
        String condicion="nombre=?";
        Cursor data=executeDb.consultarDatosCondicion(TABLENAME,columnas,condicion,new String[]{nombre});
        return cursorToList(data);
    }
    public boolean eliminarDatos(int id){
        return executeDb.eliminarDatos(TABLENAME,id);
    }
    private ArrayList<Insumo>cursorToList(Cursor data){
        ArrayList<Insumo>insumos=new ArrayList<>();
        if(data.moveToFirst()){
            do{
                int idxID=data.getColumnIndex("id");
                int idxNombre=data.getColumnIndex("nombre");
                int idxTipo=data.getColumnIndex("tipo");
                int idxCantidad=data.getColumnIndex("cantidad");
                int idxFecha=data.getColumnIndex("fecha");
                int idxProveedor=data.getColumnIndex("proveedor");
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
