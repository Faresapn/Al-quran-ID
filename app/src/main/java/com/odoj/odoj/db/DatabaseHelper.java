package com.odoj.odoj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "myalquran";
    protected static final String TABLE_NAME_QURAN = "quran";
    protected static final String TABLE_NAME_SURAH = "surah";
    protected static final String TABLE_NAME_CITY = "regencies";
    protected static final String TABLE_NAME_ADZAN = "adzan";

    private static final int DATABASE_VERSION = 2;

    public static String CREATE_TABLE_QURAN =
            " CREATE TABLE IF NOT EXISTS 'Quran' (" +
            " 'ID' INTEGER PRIMARY KEY," +
            " 'DatabaseID' SMALLINT NOT NULL," +
            " 'JuzID' INTEGER NOT NULL," +
            " 'SuratID' INTEGER NOT NULL," +
            " 'VerseID' INTEGER NOT NULL," +
            " 'AyatText' TEXT CHARACTER" +
            "); ";

    public static String CREATE_TABLE_SURAH =
            " CREATE TABLE IF NOT EXISTS 'Surah' (" +
            " 'ID' INTEGER PRIMARY KEY," +
            " 'SurahText' TEXT NOT NULL" +
            ");";

    public static String CREATE_TABLE_CITY =
            " CREATE TABLE IF NOT EXISTS 'Regencies' (" +
            " 'ID' INTEGER PRIMARY KEY," +
            " 'CityId' TEXT NOT NULL," +
            " 'City' TEXT NOT NULL" +
            ");";

    public static String CREATE_TABLE_ADZAN =
            " CREATE TABLE IF NOT EXISTS 'Adzan' (" +
            " 'ID' INTEGER PRIMARY KEY," +
            " 'Tanggal' TEXT NOT NULL," +
            " 'Subuh' TEXT NOT NULL," +
            " 'Dzuhur' TEXT NOT NULL," +
            " 'Ashar' TEXT NOT NULL," +
            " 'Maghrib' TEXT NOT NULL," +
            " 'Isya' TEXT NOT NULL" +
            ");";

    private static final String AlterQuranAddTranslation = "ALTER TABLE 'Quran' ADD 'Translation' TEXT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME); //untuk delete database
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QURAN);
        db.execSQL(CREATE_TABLE_SURAH);
        db.execSQL(CREATE_TABLE_CITY);
        db.execSQL(CREATE_TABLE_ADZAN);
        db.execSQL(AlterQuranAddTranslation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int upgradeTo = oldVersion + 1;
        Log.e("upgrade", "true");
        while (upgradeTo <= newVersion) {
            switch (upgradeTo) {
                case 2:
                    db.execSQL(AlterQuranAddTranslation);
                    break;
            }
            upgradeTo++;
        }
    }
}