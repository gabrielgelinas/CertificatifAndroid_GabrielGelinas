package com.example.gggab.certificatifandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gggab(Zombietux) on 2018-04-23.
 */
class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create Table employes(" +
                "id integer primary key autoincrement, " +
                "nom text," +
                "prenom text," +
                "departement text," +
                "taches text," +
                "fonction text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "Drop Table if exists employes;";
        db.execSQL(query);
        onCreate(db);
    }
}
