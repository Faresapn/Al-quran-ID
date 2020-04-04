package com.odoj.odoj.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.odoj.odoj.model.QuranModel;
import com.odoj.odoj.model.SurahModel;

import java.util.ArrayList;

/**
 * Created by imam-pc on 23/09/2015.
 */
public class QuranHelper extends DatabaseHelper {

    public QuranHelper(Context context) {
        super(context);
    }

    public ArrayList<QuranModel> getAllDataBySurahId(Integer surahId) {
        ArrayList<QuranModel> recordsList = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME_QURAN + " WHERE SuratID = '" + surahId + "'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex("ID"));
                Integer juzID = cursor.getInt(cursor.getColumnIndex("JuzID"));
                Integer verseId = cursor.getInt(cursor.getColumnIndex("VerseID"));
                String ayatText = cursor.getString(cursor.getColumnIndex("AyatText"));
                String translation = cursor.getString(cursor.getColumnIndex("Translation"));

                QuranModel dataObj = new QuranModel();
                dataObj.setId(id);
                dataObj.setJuzId(juzID);
                dataObj.setSuratId(surahId);
                dataObj.setVerseId(verseId);
                dataObj.setAyatText(ayatText);
                dataObj.setTranslation(translation);
                recordsList.add(dataObj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return recordsList;
    }

    public ArrayList<QuranModel> getAllDataByJuzId(Integer juzId) {
        ArrayList<QuranModel> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME_QURAN + " WHERE JuzID = '" + juzId + "'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex("ID"));
                Integer surahId = cursor.getInt(cursor.getColumnIndex("SuratID"));
                Integer verseId = cursor.getInt(cursor.getColumnIndex("VerseID"));
                String ayatText = cursor.getString(cursor.getColumnIndex("AyatText"));
                String translation = cursor.getString(cursor.getColumnIndex("Translation"));

                QuranModel dataObj = new QuranModel();
                dataObj.setId(id);
                dataObj.setJuzId(juzId);
                dataObj.setSuratId(surahId);
                dataObj.setVerseId(verseId);
                dataObj.setAyatText(ayatText);
                dataObj.setTranslation(translation);
                list.add(dataObj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return list;
    }

    public QuranModel getAllDataById(Integer id) {
        QuranModel model = new QuranModel();
        String sql = "SELECT * FROM " + TABLE_NAME_QURAN + " WHERE ID = '" + id + "'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            Integer juzID = cursor.getInt(cursor.getColumnIndex("JuzID"));
            Integer surahId = cursor.getInt(cursor.getColumnIndex("SuratID"));
            Integer verseId = cursor.getInt(cursor.getColumnIndex("VerseID"));
            String ayatText = cursor.getString(cursor.getColumnIndex("AyatText"));
            String translation = cursor.getString(cursor.getColumnIndex("Translation"));

            model.setId(id);
            model.setJuzId(juzID);
            model.setSuratId(surahId);
            model.setVerseId(verseId);
            model.setAyatText(ayatText);
            model.setTranslation(translation);
        }

        cursor.close();
        database.close();
        return model;
    }

    public SurahModel getSurahById(Integer id) {
        SurahModel dataObj = new SurahModel();
        String sql = "SELECT * FROM " + TABLE_NAME_SURAH + " WHERE ID = '" + id + "'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            String surahText = cursor.getString(cursor.getColumnIndex("SurahText"));
            dataObj.setId(id);
            dataObj.setSurahText(surahText);
        }

        cursor.close();
        database.close();
        return dataObj;
    }

    public ArrayList<SurahModel> getAllSurah() {
        ArrayList<SurahModel> surahList = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME_SURAH, null, null, null, null, null, "ID ASC");

        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex("ID"));
                String surahText = cursor.getString(cursor.getColumnIndex("SurahText"));

                SurahModel surahModel = new SurahModel(id, surahText);
                surahList.add(surahModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return surahList;
    }

    public ArrayList<SurahModel> getSurahByWord(String word) {
        ArrayList<SurahModel> surahList = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME_SURAH + " WHERE SurahText LIKE '%" + word + "%'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex("ID"));
                String surahText = cursor.getString(cursor.getColumnIndex("SurahText"));

                SurahModel surahModel = new SurahModel(id, surahText);
                surahList.add(surahModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return surahList;
    }

    public ArrayList<Integer> getAllJuz() {
        ArrayList<Integer> list = new ArrayList<>();
        String sql = "SELECT DISTINCT JuzID FROM " + TABLE_NAME_QURAN;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Integer juzId = cursor.getInt(cursor.getColumnIndex("JuzID"));
                list.add(juzId);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return list;
    }

    public void insertSurahData(String[] words) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for (String word : words) {
            contentValues.put("SurahText", word);
            database.insert(TABLE_NAME_SURAH, null, contentValues);
        }
        database.close();
    }

    public boolean deleteQuranData() {
        boolean deleteSuccessful = false;
        SQLiteDatabase database = this.getWritableDatabase();
        deleteSuccessful = database.delete(TABLE_NAME_QURAN, null, null) > 0;
        database.close();
        return deleteSuccessful;
    }
}
