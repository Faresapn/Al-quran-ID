package com.odoj.odoj;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.odoj.odoj.adapter.CityAdapter;
import com.odoj.odoj.db.AdzanHelper;
import com.odoj.odoj.db.CityHelper;
import com.odoj.odoj.model.muslimsalat.Jadwal;
import com.odoj.odoj.model.muslimsalat.ResponseAdzan;
import com.odoj.odoj.preference.AppPreference;
import com.odoj.odoj.receiver.AlarmReceiver;
import com.odoj.odoj.rest.ServiceGenerator;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class AdzanActivity extends AppCompatActivity {
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.actvCity)
    AutoCompleteTextView actvCity;
    @BindView(R.id.tv_subuh)
    TextView tvSubuh;
    @BindView(R.id.tv_dzuhur)
    TextView tvDzuhur;
    @BindView(R.id.tv_ashar)
    TextView tvAshar;
    @BindView(R.id.tv_magrhib)
    TextView tvMaghrib;
    @BindView(R.id.tv_isya)
    TextView tvIsya;
    @BindView(R.id.avBanner)
    AdView avBanner;

    AppPreference appPreference;
    CityAdapter cityAdapter;
    CityHelper cityHelper;
    AdzanHelper adzanHelper;
    int position ;
    Button on,off;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adzan);
        ButterKnife.bind(this);
        on = findViewById(R.id.button2);

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");

//for Android 5-7
                intent.putExtra("app_package", getPackageName());
                intent.putExtra("app_uid", getApplicationInfo().uid);

// for Android 8 and above
                intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());

                startActivity(intent);
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#459496")));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Adzan");
            getSupportActionBar().setElevation(0);
        }

        cityHelper = new CityHelper(this);
        cityAdapter = new CityAdapter(this, cityHelper);
        appPreference = new AppPreference(AdzanActivity.this);
        adzanHelper = new AdzanHelper(this);

        actvCity.setAdapter(cityAdapter);
        actvCity.setThreshold(1);
        actvCity.setOnItemClickListener((parent, view, position, id) -> {
            /*String cityId = String.valueOf(id);
            appPreference.setCityId(cityId);*/
            String city = cityAdapter.getItem(position);
            appPreference.setCity(city);
            actvCity.setText("");

            reloadData();
        });
        loadData();
        showBannerAd();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        AdzanHelper adzanHelper = new AdzanHelper(this);
        Jadwal adzanModel = adzanHelper.getAllAdzanData();
        setData(adzanModel);
    }

    @SuppressLint("SimpleDateFormat")
    private void reloadData() {
        String city = appPreference.getCityId();
        String cityName = appPreference.getCity();

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = format.format(date);

        ServiceGenerator.getApi(false).getJadwalShalat(cityName)
                .enqueue(new Callback<ResponseAdzan>() {
                    @Override
                    public void onResponse(Call<ResponseAdzan> call, retrofit2.Response<ResponseAdzan> response) {
                        Log.e("response", cityName);
                        if (response.body() != null) {
                            Log.e("responsebody", String.valueOf(response.raw().request().url()));
                            Jadwal adzanModel = response.body().getItems().get(0);
                            adzanHelper.insertAdzanData(adzanModel);
                            setData(adzanModel);

                            AlarmReceiver receiver = new AlarmReceiver();
                            try {
                                receiver.registerNotification(getApplicationContext());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            Toast.makeText(AdzanActivity.this, "Lokasi telah diubah", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdzanActivity.this, "Gagal memuat data. Silakan coba beberapa saat lagi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAdzan> call, Throwable t) {
                        Toast.makeText(AdzanActivity.this, "Gagal memuat data. Silakan coba beberapa saat lagi", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setData(Jadwal adzanModel) {
        tvLocation.setText(appPreference.getCity());
        tvDate.setText(adzanModel.getTanggal());

        tvSubuh.setText(adzanModel.getSubuh());
        tvDzuhur.setText(adzanModel.getDzuhur());
        tvAshar.setText(adzanModel.getAshar());
        tvMaghrib.setText(adzanModel.getMaghrib());
        tvIsya.setText(adzanModel.getIsya());
    }

    //masih testing
    private void showBannerAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        avBanner.loadAd(adRequest);

    }
}
