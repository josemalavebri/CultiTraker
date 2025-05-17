package com.example.cultitraker.DataBase.DbSqlLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteDb extends SQLiteOpenHelper {
    public static final String DbName="Cultivos.sqlite";
    public static final int DbVersion=1;
    public static final String TABLA_USUARIO="CREATE TABLE usuario(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +"email TEXT,"
            +"password TEXT)";

    public static final String TABLA_CULTIVO = "CREATE TABLE parcela (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "nombre TEXT,"+
            "ubicacion TEXT,"+
            "area REAL,"+
            "cultivo TEXT)";

    public static String TABLA_REGAR = "CREATE TABLE regar (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "fecha TEXT, " +
            "hora TEXT, " +
            "cantidadAgua REAL, " +
            "metodoRiego TEXT, " +
            "parcelaId INTEGER)";


    public SqlLiteDb(Context context) {
        super(context, DbName, null, DbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_USUARIO);
        db.execSQL(TABLA_CULTIVO);
        db.execSQL(TABLA_REGAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
