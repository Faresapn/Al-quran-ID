package com.odoj.odoj.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.odoj.odoj.model.city.CityModel;

import java.util.ArrayList;
import java.util.List;

public class CityHelper extends DatabaseHelper {

    public CityHelper(Context context) {
        super(context);
    }

    public ArrayList<CityModel> getAllCity() {
        ArrayList<CityModel> cityList = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME_CITY, null, null, null, null, null, "ID ASC");

        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex("ID"));
                String cityId = cursor.getString(cursor.getColumnIndex("CityId"));
                String city = cursor.getString(cursor.getColumnIndex("City"));

                CityModel cityModel = new CityModel(id, cityId, city);
                cityList.add(cityModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return cityList;
    }

    public void deleteCityData() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from " + TABLE_NAME_ADZAN);
        database.close();
    }

    public void insertCity(CityModel cityModel) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("CityId", cityModel.getCityId());
        contentValues.put("City", cityModel.getCityText());
        database.insert(TABLE_NAME_CITY, null, contentValues);

        database.close();
    }

    public void insertCityData(List<CityModel> cities) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from " + TABLE_NAME_ADZAN);
        ContentValues contentValues = new ContentValues();

        for (CityModel city : cities) {
            contentValues.put("CityId", city.getCityId());
            contentValues.put("City", city.getCityText());
            database.insert(TABLE_NAME_CITY, null, contentValues);
        }
        database.close();
    }

    public CityModel getCityByCityId(String cityId) {
        CityModel cityModel = new CityModel();
        String sql = "SELECT * FROM " + TABLE_NAME_CITY + " WHERE CityId = '" + cityId + "'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            Integer id = cursor.getInt(cursor.getColumnIndex("ID"));
            String city = cursor.getString(cursor.getColumnIndex("City"));
            cityModel.setId(id);
            cityModel.setCityId(cityId);
            cityModel.setCityText(city);
        }

        cursor.close();
        database.close();
        return cityModel;
    }

    public ArrayList<CityModel> getCityByQuery(String query) {
        ArrayList<CityModel> cityList = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_CITY + " WHERE City LIKE '%" + query + "%'";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex("ID"));
                String cityId = cursor.getString(cursor.getColumnIndex("CityId"));
                String city = cursor.getString(cursor.getColumnIndex("City"));

                CityModel cityModel = new CityModel(id, cityId, city);
                cityList.add(cityModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return cityList;
    }
}
