package com.am.hugorivera.examen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static String name = "bdExamen";
    private static int version = 1;
    private static SQLiteDatabase.CursorFactory cursorFactory = null;
    private static String Sqlempleados = "CREATE TABLE empleados (nombre TEXT,fecha TEXT,puesto TEXT);";

    public Database(Context context) {
        super(context, name, cursorFactory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Sqlempleados);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertarUsuario(String nombre, String fecha, String puesto) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("INSERT INTO empleados (nombre, fecha, puesto) " +
                    " VALUES('" + nombre + "', '" + fecha + "', '" + puesto + "' ) ");
            db.close();
        }
    }

    public boolean Verificar() {
        SQLiteDatabase db = getReadableDatabase();
        boolean flag;
        Cursor c = db.rawQuery("SELECT * from empleados", null);
        if (c.getCount() == 0) {
            flag = false;
            Log.println(Log.ASSERT, "No", "hay base");
        } else {
            flag = true;
            Log.println(Log.ASSERT, "Si", "hay base");
        }
        c.close();
        db.close();
        return flag;
    }
}
