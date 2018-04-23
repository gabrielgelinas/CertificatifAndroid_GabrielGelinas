package com.example.gggab.certificatifandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gggab(Zombietux) on 2018-04-23.
 */
public class DBAdapter {
    private Context mContext;
    private String db_name = "dbname";
    private DBHelper dbHelper;
    private int db_version = 1;
    private SQLiteDatabase sqLiteDatabase;

    public DBAdapter(Context mContext) {
        this.mContext = mContext;

        dbHelper = new DBHelper(mContext, db_name, null, db_version);
    }

    public void open() {
        try {
            this.sqLiteDatabase = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertEmploye(Employe employe) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("nom", employe.getNom());
        contentValues.put("prenom", employe.getPrenom());
        contentValues.put("departement", employe.getDepartement());
        contentValues.put("taches", employe.getTaches());
        contentValues.put("Fonction", employe.getFonction());

        this.sqLiteDatabase.insert("employes", null, contentValues);
    }

    public List<Employe> selectAllEmployes() {
        List<Employe> allEmployes = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("employes", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                allEmployes.add(new Employe(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        return allEmployes;
    }

    public void DropAll() {
        this.sqLiteDatabase.execSQL("delete from employes");
//        this.sqLiteDatabase.delete("employes", "faculty" + "=" + String.valueOf(faculty), null);
    }
}
