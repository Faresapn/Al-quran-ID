package com.odoj.odoj.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private final String keyFirstRun = "First run";
    private final String keyCityId = "city id";
    private final String keyLastRead = "Last read";
    private final String keyAdzanId = "Adzan id";
    private final String keyDailyMessage = "daily message";
    private final String keyDaily = "daily";
    private final String keyDate = "date";
    private final String keyAdzanTrigger = "adzan trigger";
    private final String keyFirstTrigger = "first trigger";
    private final String keyDataDate = "data date";
    private final String keyChangeData1 = "change data1";
    private final String keyCity = "key city";

    public AppPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setFirstRun(Boolean input) {
        editor = prefs.edit();
        editor.putBoolean(keyFirstRun, input);
        editor.apply();
    }

    public Boolean getFirstRun() {
        return prefs.getBoolean(keyFirstRun, true);
    }

    public void setCityId(String input) {
        editor = prefs.edit();
        editor.putString(keyCityId, input);
        editor.apply();
    }

    public String getCityId() {
        return prefs.getString(keyCityId, "667");
    }

    public void setLastRead(int id) {
        editor = prefs.edit();
        editor.putInt(keyLastRead, id);
        editor.apply();
    }

    public int getLastRead() {
        return prefs.getInt(keyLastRead, 1);
    }

    public void setAdzanid(int id) {
        editor = prefs.edit();
        editor.putInt(keyAdzanId, id);
        editor.apply();
    }

    public int getAdzanId() {
        return prefs.getInt(keyAdzanId, 1);
    }

    public void setDailyMessage(String word) {
        editor = prefs.edit();
        editor.putString(keyDailyMessage, word);
        editor.apply();
    }

    public String getDailyMessage() {
        return prefs.getString(keyDailyMessage, "Ayo kejar target harianmu sebanyak 1 juz per hari!");
    }

    public void setDaily(String daily) {
        editor = prefs.edit();
        editor.putString(keyDaily, daily);
        editor.apply();
    }

    public String getDaily() {
        return prefs.getString(keyDaily, "juz");
    }

    public void setDate(String date) {
        editor = prefs.edit();
        editor.putString(keyDate, date);
        editor.apply();
    }

    public String getDate() {
        return prefs.getString(keyDate, "0000-00-00");
    }

    public void setAdzanTrigger(int id) {
        editor = prefs.edit();
        editor.putInt(keyAdzanTrigger, id);
        editor.apply();
    }

    public int getAdzanTrigger() {
        return prefs.getInt(keyAdzanTrigger, 1);
    }

    public void setFirstTrigger(boolean value) {
        editor = prefs.edit();
        editor.putBoolean(keyFirstTrigger, value);
        editor.apply();
    }

    public boolean getFirstTrigger() {
        return prefs.getBoolean(keyFirstTrigger, true);
    }

    public void setDataDate(String date) {
        editor = prefs.edit();
        editor.putString(keyDataDate, date);
        editor.apply();
    }

    public String getDataDate() {
        return prefs.getString(keyDataDate, "0000-00-00");
    }

    public void setChangeData1(Boolean value) {
        editor = prefs.edit();
        editor.putBoolean(keyChangeData1, value);
        editor.apply();
    }

    public Boolean getChangeData1() {
        return prefs.getBoolean(keyChangeData1, true);
    }

    public void setCity(String city) {
        editor = prefs.edit();
        editor.putString(keyCity, city);
        editor.apply();
    }

    public String getCity() {
        return prefs.getString(keyCity, "JAKARTA");
    }
}
