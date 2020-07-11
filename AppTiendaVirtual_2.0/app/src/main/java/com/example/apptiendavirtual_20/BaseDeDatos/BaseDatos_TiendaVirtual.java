package com.example.apptiendavirtual_20.BaseDeDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos_TiendaVirtual extends SQLiteOpenHelper {
public final static String NOME_BD="BaseDatosTiendaVirtual.db";
public final static int VERSION_BD=1;
private SQLiteDatabase operacionesBD;

    private String CREAR_TABLA_USUARIOS = "CREATE TABLE Usuarios ("+
            "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "usuario VARCHAR ( 50 ) NOT NULL,"+
            "contra VARCHAR ( 50 ) NOT NULL,"+
            "nombre VARCHAR ( 50 ) NOT NULL,"+
            "apellido VARCHAR ( 50 ) NOT NULL,"+
            "correo VARCHAR ( 80 ) NOT NULL,"+
            "celular VARCHAR ( 9 ) NOT NULL)";


    public BaseDatos_TiendaVirtual(Context context)
    {
        super(context,NOME_BD,null,VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_USUARIOS);
        db.execSQL("INSERT INTO Usuarios (idUsuario, usuario, contra, nombre, apellido, correo , celular) " +
                "VALUES(0, 'admin', 'admin','admin','admin','admin','000000000')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        onCreate(db);
    }

    public void asignarSQLiteDatBase(SQLiteDatabase operacionesBD)
    {
        this.operacionesBD = operacionesBD;
    }
}
