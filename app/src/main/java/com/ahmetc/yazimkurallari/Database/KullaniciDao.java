package com.ahmetc.yazimkurallari.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class KullaniciDao {
    public int yanlisSayisi(DatabaseHelper dbh) { // 100 yanlış cevap verilmiş
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT yanlis_sayisi FROM kullanici", null);
        int sonuc = 0;
        while (c.moveToNext()) {
            sonuc = c.getInt(c.getColumnIndex("yanlis_sayisi"));
        }
        return sonuc;
    }
    public void yanlisArttir(DatabaseHelper dbh, int current) {
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("yanlis_sayisi", ++current);
        sqLiteDatabase.update("kullanici", contentValues, "", new String[]{});
    }
    public int dogruSayisi(DatabaseHelper dbh) { // 100 doğru cevap verilmiş
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT dogru_sayisi FROM kullanici", null);
        int sonuc = 0;
        while (c.moveToNext()) {
            sonuc = c.getInt(c.getColumnIndex("dogru_sayisi"));
        }
        return sonuc;
    }
    public void dogruArttir(DatabaseHelper dbh, int current) {
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("dogru_sayisi", ++current);
        sqLiteDatabase.update("kullanici", contentValues, "", new String[]{});
    }
    public void sifirlaKullanici(DatabaseHelper dbh) {
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE kullanici SET dogru_sayisi = 0, yanlis_sayisi = 0");
        sqLiteDatabase.close();
    }
}
