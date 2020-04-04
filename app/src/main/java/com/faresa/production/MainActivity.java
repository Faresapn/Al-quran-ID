package com.faresa.production;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.faresa.production.db.AdzanHelper;
import com.faresa.production.db.QuranHelper;
import com.faresa.production.kompas.Kompas;
import com.faresa.production.model.QuranModel;
import com.faresa.production.model.muslimsalat.Jadwal;
import com.faresa.production.model.muslimsalat.ResponseAdzan;
import com.faresa.production.preference.AppPreference;
import com.faresa.production.receiver.AlarmReceiver;
import com.faresa.production.rest.ApiClient;
import com.faresa.production.rest.ApiService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

import static com.faresa.production.DailyActivity.HALAMAN;
import static com.faresa.production.DailyActivity.HALF_JUZ_MESSAGE;
import static com.faresa.production.DailyActivity.JUZ_MESSAGE;
import static com.faresa.production.DailyActivity.SURAH_MESSAGE;
import static com.faresa.production.QuranActivity.EXTRA;
import static com.faresa.production.QuranActivity.EXTRA_ID;
import static com.faresa.production.QuranActivity.EXTRA_LAST_READ_JUZ;
import static com.faresa.production.QuranActivity.EXTRA_LAST_READ_SURAH;

public class MainActivity extends AppCompatActivity{
    @BindView(R.id.btnLastRead)
    Button btnLastRead;
    @BindView(R.id.ivBadge)
    ImageView ivBadge;
     Button kiblat;

    private InterstitialAd interstitialAd;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        kiblat = findViewById(R.id.btnQiblat);
        kiblat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Kompas.class);
                startActivity(intent);
            }
        });
        showBadge();
        loadInterstitialAd();
        dailyReset();
    }

    @OnClick({R.id.btnQuran, R.id.btnLastRead, R.id.btnAdzan, R.id.btnDaily, R.id.btnSetting})
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnQuran:
                intent = new Intent(this, QuranMenuActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLastRead:
                lastReadAlert();
                break;
            case R.id.btnAdzan:
                intent = new Intent(this, AdzanActivity.class);
                startActivity(intent);
                break;
            case R.id.btnDaily:
                intent = new Intent(this, DailyActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSetting:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void lastReadAlert() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View promptView = inflater.inflate(R.layout.alert_last_read, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.LastReadDialog)
                .setMessage("note: untuk menandai bacaan terakhir, klik pada ayat yang ingin ditandai")
                .create();

        AppPreference preference = new AppPreference(this);
        QuranHelper quranHelper = new QuranHelper(this);
        QuranModel quranModel = quranHelper.getAllDataById(preference.getLastRead());
        String surah = quranHelper.getSurahById(quranModel.getSuratId()).getSurahText();
        String verse = String.valueOf(quranModel.getVerseId());
        String juz = "Juz " + quranModel.getJuzId();

        TextView tvLastReadSurah = promptView.findViewById(R.id.tvLastReadSurah);
        TextView tvLastReadJuz = promptView.findViewById(R.id.tvLastReadJuz);
        Button btnAlertSurah = promptView.findViewById(R.id.btnAlertSurah);
        Button btnAlertJuz = promptView.findViewById(R.id.btnAlertJuz);

        Intent intent = new Intent(this, QuranActivity.class);
        intent.putExtra(EXTRA, true);
        intent.putExtra(EXTRA_ID, preference.getLastRead());

        btnAlertSurah.setOnClickListener(v -> {
            intent.putExtra(EXTRA_LAST_READ_SURAH, true);
            startActivity(intent);
            alertDialog.dismiss();
        });

        btnAlertJuz.setOnClickListener(v -> {
            intent.putExtra(EXTRA_LAST_READ_JUZ, true);
            startActivity(intent);
            alertDialog.dismiss();
        });

        tvLastReadSurah.setText("Q.S. " + surah + ": " + verse);
        tvLastReadJuz.setText(juz);
        alertDialog.setView(promptView);
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showBadge();
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    private void showBadge() {
        AppPreference preference = new AppPreference(this);
        Log.e("badge", "badge: " + preference.getDailyMessage());
        if (preference.getDailyMessage().equals("Kamu telah menyelesaikan target harianmu!")) {
            ivBadge.setImageResource(R.drawable.daily_badge_finished);
        } else {
            ivBadge.setImageResource(R.drawable.daily_badge);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void dailyReset() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = format.format(date);

        AppPreference preference = new AppPreference(this);
        if (!formattedDate.equals(preference.getDataDate())) {
            loadData(this, preference);
        }

        if (!formattedDate.equals(preference.getDate())) {
            preference.setDate(formattedDate);
            preference.setAdzanTrigger(1);

            switch (preference.getDaily()) {
                case "setengah juz":
                    preference.setDailyMessage(HALF_JUZ_MESSAGE);
                    break;
                case "surah":
                    preference.setDailyMessage(SURAH_MESSAGE);
                    break;
                case "juz":
                    preference.setDailyMessage(JUZ_MESSAGE);
                    break;
                case "halaman":
                    preference.setDailyMessage(HALAMAN);
                    break;
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void loadData(Context context, AppPreference preference) {
        AppPreference appPreference = new AppPreference(context);
        AdzanHelper adzanHelper = new AdzanHelper(context);
        String city = appPreference.getCityId();
        String cityName = appPreference.getCity();

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = format.format(date);

        ApiService apiService = ApiClient.getClient(false).create(ApiService.class);
        Call<ResponseAdzan> callAdzan = apiService.getJadwalShalat(cityName);

        callAdzan.enqueue(new Callback<ResponseAdzan>() {
            @Override
            public void onResponse(Call<ResponseAdzan> call, retrofit2.Response<ResponseAdzan> response) {
                if (response.body() != null) {
                    Log.e("empty", "msg: " + response.body().getItems().get(0).getTanggal());
                    Jadwal adzanModel = response.body().getItems().get(0);
                    Log.e("data", "dataload: " + adzanModel.getTanggal());
                    adzanHelper.insertAdzanData(adzanModel);
                    preference.setDataDate(formattedDate);

                    AlarmReceiver receiver = new AlarmReceiver();
                    try {
                        receiver.registerNotification(context);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAdzan> call, Throwable t) {
                Toast.makeText(context, "Pastikan koneksi internet aktif", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadInterstitialAd() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_id));
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


        }
        return super.onOptionsItemSelected(item);
    }
}
