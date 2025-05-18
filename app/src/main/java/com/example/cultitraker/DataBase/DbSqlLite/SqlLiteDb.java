package com.example.cultitraker.DataBase.DbSqlLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteDb extends SQLiteOpenHelper {
    public static final String DbName="Cultivos";
    public static final int DbVersion=3;

    private static final String TAG = "SqlLiteDb";

    public static final String TABLA_USUARIO = "CREATE TABLE usuario(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +"email TEXT,"
            +"password TEXT)";
    public static final String TABLA_INSUMO="CREATE TABLE insumo("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +"nombre TEXT," +
            "tipo TEXT," +
            "cantidad INTEGER," +
            "fecha TEXT," +
            "proveedor TEXT)";

    public static final String TABLA_PARCELA = "CREATE TABLE parcela (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "nombre TEXT,"+
            "tamano INTEGER,"+
            "cultivo TEXT,"+
            "cantidadCultivo INTEGER)";

    //AGREGAR SU TABLA
    public static final String TABLA_TAREAS = "CREATE TABLE tareas (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "tipoActividad TEXT,"+
            "descripcion TEXT,"+
            "estado TEXT,"+
            "fecha TEXT)";


    public SqlLiteDb(Context context) {
        super(context, DbName, null, DbVersion);
    }

    //AGREGAR LA TABLA
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_USUARIO);
        db.execSQL(TABLA_INSUMO);
        db.execSQL(TABLA_PARCELA);
        db.execSQL(TABLA_TAREAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }
}
