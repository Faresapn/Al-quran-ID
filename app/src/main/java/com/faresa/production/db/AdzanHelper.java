package com.faresa.production.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.faresa.production.model.muslimsalat.Jadwal;

public class AdzanHelper extends DatabaseHelper {

    public AdzanHelper(Context context) {
        super(context);
    }

    public Jadwal getAllAdzanData() {
        Jadwal adzanModel = new Jadwal();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME_ADZAN, null, null, null, null, null, "ID ASC");

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String tanggal = cursor.getString(cursor.getColumnIndex("Tanggal"));
            String subuh = cursor.getString(cursor.getColumnIndex("Subuh"));
            String dzuhur = cursor.getString(cursor.getColumnIndex("Dzuhur"));
            String ashar = cursor.getString(cursor.getColumnIndex("Ashar"));
            String maghrib = cursor.getString(cursor.getColumnIndex("Maghrib"));
            String isya = cursor.getString(cursor.getColumnIndex("Isya"));

            adzanModel.setId(id);
            adzanModel.setTanggal(tanggal);
            adzanModel.setSubuh(subuh);
            adzanModel.setDzuhur(dzuhur);
            adzanModel.setAshar(ashar);
            adzanModel.setMaghrib(maghrib);
            adzanModel.setIsya(isya);
        }

        cursor.close();
        database.close();
        return adzanModel;
    }

    public Jadwal getAdzanDataById(Integer id) {
        Jadwal model = new Jadwal();
        String sql = "SELECT * FROM " + TABLE_NAME_ADZAN + " WHERE ID = '" + id + "'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            String tanggal = cursor.getString(cursor.getColumnIndex("Tanggal"));
            String subuh = cursor.getString(cursor.getColumnIndex("Subuh"));
            String dzuhur = cursor.getString(cursor.getColumnIndex("Dzuhur"));
            String ashar = cursor.getString(cursor.getColumnIndex("Ashar"));
            String maghrib = cursor.getString(cursor.getColumnIndex("Maghrib"));
            String isya = cursor.getString(cursor.getColumnIndex("Isya"));

            model.setId(id);
            model.setTanggal(tanggal);
            model.setSubuh(subuh);
            model.setDzuhur(dzuhur);
            model.setAshar(ashar);
            model.setMaghrib(maghrib);
            model.setIsya(isya);
        }

        cursor.close();
        database.close();
        return model;
    }

    public void insertAdzanData(Jadwal adzanModel) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from " + TABLE_NAME_ADZAN);
        ContentValues contentValues = new ContentValues();

        contentValues.put("Tanggal", adzanModel.getTanggal());
        contentValues.put("Subuh", adzanModel.getSubuh());
        contentValues.put("Dzuhur", adzanModel.getDzuhur());
        contentValues.put("Ashar", adzanModel.getAshar());
        contentValues.put("Maghrib", adzanModel.getMaghrib());
        contentValues.put("Isya", adzanModel.getIsya());
        database.insert(TABLE_NAME_ADZAN, null, contentValues);

        database.close();
    }
}
