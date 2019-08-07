package com.ahmetc.yazimkurallari.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "yazimkurallari.sqlite",null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS \"kategoriler\" (\n" +
                "\t\"kategori_id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"kategori_ad\"\tTEXT\n" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS \"sorular\" (\n" +
                "\t\"soru_id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"soru_dogru\"\tTEXT,\n" +
                "\t\"soru_yanlis\"\tTEXT,\n" +
                "\t\"soru_yanlisyapilan\"\tINTEGER,\n" +
                "\t\"soru_dogruyapilan\"\tINTEGER,\n" +
                "\t\"soru_isaret\"\tINTEGER,\n" +
                "\t\"kategori_id\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"kategori_id\") REFERENCES \"kategoriler\"\n" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS \"kullanici\" (\n" +
                "\t\"dogru_sayisi\"\tINTEGER,\n" +
                "\t\"yanlis_sayisi\"\tINTEGER\n" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS \"ozel_sorular\" (\n" +
                "\t\"soru_id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"soru_dogru\"\tTEXT,\n" +
                "\t\"soru_yanlis\"\tTEXT,\n" +
                "\t\"soru_yanlisyapilan\"\tINTEGER,\n" +
                "\t\"soru_dogruyapilan\"\tINTEGER,\n" +
                "\t\"soru_isaret\"\tINTEGER,\n" +
                "\t\"kategori_id\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"kategori_id\") REFERENCES \"kategoriler\"\n" +
                ");");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS sorular");
        db.execSQL("DROP TABLE IF EXISTS kategoriler");
        db.execSQL("DROP TABLE IF EXISTS kullanici");
        db.execSQL("DROP TABLE IF EXISTS ozel_sorular");
        onCreate(db);
    }
}
