package com.example.cultitraker.DataBase.DbSqlLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteDb extends SQLiteOpenHelper {
    public static final String DbName="Cultivos.sqlite";
    public static final int DbVersion=1;
    public static final String tablaUsuario="CREATE TABLE usuario(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +"email TEXT,"
            +"password TEXT)";

    public static final String tablaCultivo = "CREATE TABLE Parcelas (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "nombre TEXT,"+
            "ubicacion TEXT,"+
            "area REAL,"+
            "cultivo TEXT)";

    public SqlLiteDb(Context context) {
        super(context, DbName, null, DbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tablaUsuario);
        db.execSQL(tablaCultivo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
