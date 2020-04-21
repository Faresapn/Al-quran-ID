package com.faresa.alquran;

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

import com.faresa.alquran.adapter.CityAdapter;
import com.faresa.alquran.db.AdzanHelper;
import com.faresa.alquran.db.CityHelper;
import com.faresa.alquran.model.muslimsalat.Jadwal;
import com.faresa.alquran.model.muslimsalat.ResponseAdzan;
import com.faresa.alquran.preference.AppPreference;
import com.faresa.alquran.receiver.AlarmReceiver;
import com.faresa.alquran.rest.ServiceGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class    AdzanActivity extends AppCompatActivity {
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
    @BindView(R.id.tv_magrib)
    TextView tvMaghrib;
    @BindView(R.id.tv_isya)
    TextView tvIsya;


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
        on = findViewById(R.id.floatingActionButton);

        if (getSupportActionBar() != null){
            Objects.requireNonNull(getSupportActionBar()).hide();
        }
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
            String city = cityAdapter.getItem(position);
            appPreference.setCity(city);
            actvCity.setText("");
            tvLocation.setText(appPreference.getCity());
            reloadData();
        });
        loadData();
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
        String currentDate = new SimpleDateFormat("dd-MM", Locale.getDefault()).format(new Date());

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("mm-dd");
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
                            Log.d("jadwal", String.valueOf(adzanModel));

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
        String currentDatee = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("hh:mm aa \nEE, dd-MMM-yyyy  ");
        String datetime = dateformat.format(c.getTime());
        Log.d("mydatek",datetime);
        Log.e("mydateee",currentDatee );
        String currentDate = new SimpleDateFormat("EE , dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Log.d("mydatee",currentDate);
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Log.d("mydate",mydate);
        Log.d("jadwal", String.valueOf(adzanModel));
        tvDate.setText(datetime);

        tvSubuh.setText(adzanModel.getSubuh());
        tvDzuhur.setText(adzanModel.getDzuhur());
        tvAshar.setText(adzanModel.getAshar());
        tvMaghrib.setText(adzanModel.getMaghrib());
        tvIsya.setText(adzanModel.getIsya());
        Log.d("test",String.valueOf(adzanModel.getImsak()));
    }

}
