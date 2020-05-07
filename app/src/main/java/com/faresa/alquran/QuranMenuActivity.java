package com.faresa.alquran;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.faresa.alquran.adapter.AsmaulHuznaAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuranMenuActivity extends AppCompatActivity {
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_menu);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#89C8C7")));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Al-Qur'an");
            getSupportActionBar().setElevation(0);
        }
        loadInterstitialAd();
    }

    private void loadInterstitialAd() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_id));
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btnJuz, R.id.btnSurah,R.id.btnMurottal,R.id.btnAsmaul})
    public void onMenuClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btnJuz:
                intent = new Intent(this, JuzActivity.class);
                break;
            case R.id.btnSurah:
                intent = new Intent(this, SurahActivity.class);
                break;
            case R.id.btnMurottal:
                intent = new Intent(this,MurottalActivity.class);
                break;
            case R.id.btnAsmaul:
                intent = new Intent(this, AsmaulHuznaActivity.class);
                break;
        }
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }
}
