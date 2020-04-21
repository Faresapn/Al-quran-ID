package com.faresa.alquran;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.faresa.alquran.db.AdzanHelper;
import com.faresa.alquran.db.CityHelper;
import com.faresa.alquran.db.DatabaseHelper;
import com.faresa.alquran.db.QuranHelper;
import com.faresa.alquran.model.ResponseAdzanAndResponseCity;
import com.faresa.alquran.model.muslimsalat.Jadwal;
import com.faresa.alquran.model.muslimsalat.ResponseAdzan;
import com.faresa.alquran.model.city.CityModel;
import com.faresa.alquran.model.city.ResponseCity;
import com.faresa.alquran.preference.AppPreference;
import com.faresa.alquran.receiver.AlarmReceiver;
import com.faresa.alquran.rest.ApiClient;
import com.faresa.alquran.rest.ApiService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PreloadActivity extends AppCompatActivity {
    private QuranHelper quranHelper;
    AdzanHelper adzanHelper;
    CityHelper cityHelper;
    AppPreference appPreference;
    CompositeDisposable disposables;

    private String[] surahName = {"Al-Fatihah", "Al-Baqarah", "Ali Imran", "An-Nisaa", "Al-Maidah",
            "Al-An'am", "Al-A'raf", "Al-Anfaal", "At-Taubah", "Yunus", "Huud", "Yusuf", "Ar-Ra'd",
            "Ibrahim", "Al-Hijr", "An-Nahl", "Al-Israa'", "Al-Kahfi", "Maryam", "Thaahaa",
            "Al-Anbiyaa", "Al-Hajj", "Al-Mu'minuun", "An-Nuur", "Al-Furqaan", "Asy-Syu'araa",
            "An-Naml", "Al-Qashash", "Al-'Ankabuut", "Ar-Ruum", "Luqman", "As-Sajdah", "Al-Ahzab",
            "Saba'", "Faathir", "YaaSiin", "Ash-Shaaffat", "Shaad", "Az-Zumar", "Al-Mu'min",
            "Fushshilat", "Asy-Syuura", "Az-Zukhruf", "Ad-Dukhaan", "Al-Jaatsiyah", "Al-Ahqaaf",
            "Muhammad", "Al-Fath", "Al-Hujuraat", "Qaaf", "Adz-Dzaariyat", "Ath-Thuur", "An-Najm",
            "Al-Qamar", "Ar-Rahmaan", "Al-Waaqi'ah", "Al-Hadiid", "Al-Mujaadilah", "Al-Hasyr",
            "Al-Mumtahanah", "Ash-Shaff", "Al-Jumuah", "Al-Munaafiqun", "At-Taghaabun",
            "Ath-Thalaaq", "At-Tahriim", "Al-Mulk", "Al-Qalam", "Al-Haaqqah", "Al-Ma'aarij", "Nuh",
            "Al-Jin", "Al-Muzzammil", "Al-Muddatstsir", "Al-Qiyaamah", "Al-Insaan", "Al-Mursalaat",
            "An-Naba'", "An-Naazi'aat", "'Abasa", "At-Takwiir", "Al-Infithaar", "Al-Muthaffif",
            "Al-Insyiqaaq", "Al-Buruuj", "Ath-Thaariq", "Al-A'laa", "Al-Ghaasyiyah", "Al-Fajr",
            "Al-Balad", "Asy-Syams", "Al-Lail", "Adh-Dhuhaa", "Al-Insyirah", "At-Tiin", "Al-'Alaq",
            "Al-Qadr", "Al-Bayyinah", "Az-Zalzalah", "Al-'Aadiyaat", "Al-Qaari'ah", "At-Takaatsur",
            "Al-'Ashr", "Al-Humazah", "Al-Fiil", "Quraisy", "Al-Maa'uun", "Al-Kautsar", "Al-Kaafiruun",
            "An-Nashr", "Al-Lahab", "Al-Ikhlash", "Al-Falaq", "An-Naas"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);

        quranHelper = new QuranHelper(this);
        adzanHelper = new AdzanHelper(this);
        cityHelper = new CityHelper(this);
        appPreference = new AppPreference(this);
        disposables = new CompositeDisposable();
        Boolean firstRun = appPreference.getFirstRun();
        Boolean changeData1 = appPreference.getChangeData1();

        if (firstRun) {
            loadData();
            appPreference.setChangeData1(false);
        } else if (changeData1) {
            boolean deleted = quranHelper.deleteQuranData();
            Log.e("deleted", String.valueOf(deleted));
            initData(R.raw.data);
            appPreference.setChangeData1(false);
            moveActivity();
        } else {
            moveActivity();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void loadData() {
        String cityId = appPreference.getCityId();
        String city = appPreference.getCity();

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = format.format(date);

        ApiService apiService = ApiClient.getClient(true).create(ApiService.class);
        Observable<ResponseCity> cityObservable = apiService.getAllCity()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        apiService = ApiClient.getClient(false).create(ApiService.class);
        Observable<ResponseAdzan> adzanObservable = apiService.getShalat(city)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<ResponseAdzanAndResponseCity> combined = Observable.zip(cityObservable, adzanObservable, (responseCity, adzan) -> new ResponseAdzanAndResponseCity(adzan, responseCity));

        combined.subscribe(new Observer<ResponseAdzanAndResponseCity>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(ResponseAdzanAndResponseCity responseAdzanAndResponseCity) {
                List<CityModel> cities = responseAdzanAndResponseCity.getResponseCity().getCities();
                Jadwal adzanModel = responseAdzanAndResponseCity.getResponseAdzan().getItems().get(0);
                cityHelper.insertCityData(cities);
                adzanHelper.insertAdzanData(adzanModel);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
                showAlert("Gagal memuat data. Silakan coba beberapa saat lagi");
            }

            @Override
            public void onComplete() {
                initData(R.raw.data);
                quranHelper.insertSurahData(surahName);
                appPreference.setFirstRun(false);
                moveActivity();
            }
        });
    }

    private void initData(int resourceId) {
        new LoadData(resourceId).execute();
    }

    @SuppressLint("StaticFieldLeak")
    class LoadData extends AsyncTask<Void, Void, Void> {
        int resourceId;

        LoadData(int resourceId) {
            this.resourceId = resourceId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            insertFromFile(PreloadActivity.this, resourceId);
            return null;
        }
    }

    public void insertFromFile(Context context, int resourceId) {
        try {
            InputStream insertsStream = context.getResources().openRawResource(resourceId);
            BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SQLiteDatabase database = databaseHelper.getWritableDatabase();

            database.beginTransaction();

            while (insertReader.ready()) {
                String insertStmt = insertReader.readLine();
                database.execSQL(insertStmt);
            }

            database.setTransactionSuccessful();
            database.endTransaction();
            database.close();

            insertReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PreloadActivity.this);

        builder
                .setTitle("Error")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> finish());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void moveActivity() {
        new Handler().postDelayed(() -> {
            AlarmReceiver receiver = new AlarmReceiver();
            try {
                receiver.registerNotification(this);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            startActivity(new Intent(PreloadActivity.this, MainActivity.class));
            finish();
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
