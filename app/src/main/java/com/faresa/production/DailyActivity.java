package com.faresa.production;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.faresa.production.preference.AppPreference;
import com.faresa.production.receiver.AlarmReceiver;
import com.google.android.gms.ads.AdRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DailyActivity extends AppCompatActivity {
    @BindView(R.id.tvTarget)
    TextView tvTarget;

    public static final String HALF_JUZ_MESSAGE = "Ayo kejar target harianmu sebanyak 1/2 juz per hari!";
    public static final String SURAH_MESSAGE = "Ayo kejar target harianmu sebanyak 1 surat per hari!";
    public static final String JUZ_MESSAGE = "Ayo kejar target harianmu sebanyak 1 juz per hari!";
    public static final String HALAMAN = "Ayo kejar target harianmu sebanyak 1 halaman per hari!";
    AppPreference preference;
    AlarmReceiver receiver = new AlarmReceiver();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        ButterKnife.bind(this);
        preference = new AppPreference(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#459496")));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Harian");
            getSupportActionBar().setElevation(0);
        }

        tvTarget.setText(preference.getDaily().toUpperCase() + "/HARI");
        Toolbar toolbar = findViewById(R.id.tolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    @OnClick({R.id.btnHalfJuz, R.id.btnSurahDaily, R.id.btnJuzDaily,R.id.btnnonaktif})
    public void onDailyClick(View v) {
        switch (v.getId()) {
            case R.id.btnHalfJuz:
                preference.setDaily("setengah juz");
                preference.setDailyMessage(HALF_JUZ_MESSAGE);
                tvTarget.setText(preference.getDaily().toUpperCase() + "/HARI");
                Toast.makeText(this, "Target diubah", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnSurahDaily:
                preference.setDaily("surah");
                preference.setDailyMessage(SURAH_MESSAGE);
                tvTarget.setText(preference.getDaily().toUpperCase() + "/HARI");
                Toast.makeText(this, "Target diubah", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnJuzDaily:
                preference.setDaily("juz");
                preference.setDailyMessage(JUZ_MESSAGE);
                tvTarget.setText(preference.getDaily().toUpperCase() + "/HARI");
                Toast.makeText(this, "Target diubah", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnnonaktif:
                preference.setDaily("nonaktif");
                receiver.daily_setcancel(getApplicationContext());
                break;
        }

    }
}
