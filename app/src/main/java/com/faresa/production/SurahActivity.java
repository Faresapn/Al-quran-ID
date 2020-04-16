package com.faresa.production;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;


import com.faresa.production.adapter.SurahAdapter;
import com.faresa.production.db.QuranHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

import static com.faresa.production.QuranActivity.EXTRA_SURAH;
import static com.faresa.production.QuranActivity.EXTRA_SURAH_ID;

public class SurahActivity extends AppCompatActivity {
    @BindView(R.id.svSurah)
    SearchView svSurah;
    @BindView(R.id.lvSurah)
    ListView lvSurah;
    @BindView(R.id.avBanner)
    AdView avBanner;

    private QuranHelper quranHelper;
    private SurahAdapter surahAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#89C8C7")));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Al-Qur'an");
            getSupportActionBar().setElevation(0);
        }

        quranHelper = new QuranHelper(this);

        svSurah.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                initDataByWord(newText);
                return true;
            }
        });

        surahAdapter = new SurahAdapter(this);
        surahAdapter.setSurahData(quranHelper.getAllSurah());
        lvSurah.setAdapter(surahAdapter);

        showBannerAd();
    }

    @OnClick(R.id.svSurah)
    public void onSearchClick() {
        svSurah.setIconified(false);
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

    private void initDataByWord(String word) {
        if (word.length() == 0) {
            surahAdapter.setSurahData(quranHelper.getAllSurah());
        } else {
            surahAdapter.setSurahData(quranHelper.getSurahByWord(word));
        }
    }

    @OnItemClick(R.id.lvSurah)
    public void OnSurahItemClick(long id) {
        Intent intent = new Intent(SurahActivity.this, QuranActivity.class);
        intent.putExtra(EXTRA_SURAH, true);
        intent.putExtra(EXTRA_SURAH_ID, (int) id);
        startActivity(intent);
    }

    private void showBannerAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        avBanner.loadAd(adRequest);
    }
}
