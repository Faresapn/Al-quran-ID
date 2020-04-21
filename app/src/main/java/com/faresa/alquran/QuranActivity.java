package com.faresa.alquran;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.faresa.alquran.adapter.QuranAdapter;
import com.faresa.alquran.db.QuranHelper;
import com.faresa.alquran.model.QuranModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuranActivity extends AppCompatActivity {
    @BindView(R.id.rvQuran)
    RecyclerView rvQuran;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.avBanner)
    AdView avBanner;

    public static final String EXTRA_SURAH = "EXTRA_SURAH";
    public static final String EXTRA_JUZ = "EXTRA_JUZ";
    public static final String EXTRA = "EXTRA";

    public static final String EXTRA_LAST_READ_SURAH = "EXTRA_LAST_READ_SURAH";
    public static final String EXTRA_LAST_READ_JUZ = "EXTRA_LAST_READ_JUZ";

    public static final String EXTRA_SURAH_ID = "EXTRA_SURAH_ID";
    public static final String EXTRA_JUZ_ID = "EXTRA_JUZ_ID";
    public static final String EXTRA_ID = "EXTRA_ID";

    int surahId = 0;
    int juzId = 0;

    ArrayList<QuranModel> models;
    QuranModel itemById;
    String title;
    Integer position;
    Integer number;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);
        ButterKnife.bind(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        QuranAdapter quranAdapter = new QuranAdapter(this);
        QuranHelper quranHelper = new QuranHelper(this);

        id = getIntent().getIntExtra(EXTRA_ID, 0);
        if (id > 0) {
            itemById = quranHelper.getAllDataById(id);

            if (getIntent().getBooleanExtra(EXTRA_LAST_READ_SURAH, false)) {
                position = itemById.getVerseId() - 1;
                surahId = itemById.getSuratId();
                models = quranHelper.getAllDataBySurahId(surahId);
                number = surahId;
                title = quranHelper.getSurahById(surahId).getSurahText();

            } else if (getIntent().getBooleanExtra(EXTRA_LAST_READ_JUZ, false)) {
                juzId = itemById.getJuzId();
                models = quranHelper.getAllDataByJuzId(juzId);
                number = juzId;
                title = "Juz " + juzId;

                for (int i = 0; i < models.size(); i++) {
                    if (models.get(i).getVerseId() == 1) {
                        models.add(i, null);
                        i++;
                    }
                }

                for (int i = 0; i < models.size(); i++) {
                    if (models.get(i) != null) {
                        if (models.get(i).getId() == id) {
                            position = i;
                            break;
                        }
                    }

                }
            }

        } else if (getIntent().getBooleanExtra(EXTRA_SURAH, false)){
            surahId = getIntent().getIntExtra(EXTRA_SURAH_ID, 0);
            models = quranHelper.getAllDataBySurahId(surahId);
            number = surahId;
            title = quranHelper.getSurahById(surahId).getSurahText();
            position = 0;

        } else if (getIntent().getBooleanExtra(EXTRA_JUZ, false)) {
            juzId = getIntent().getIntExtra(EXTRA_JUZ_ID, 0);
            models = quranHelper.getAllDataByJuzId(juzId);
            number = juzId;
            title = "Juz " + juzId;
            position = 0;

            for (int i = 0; i < models.size(); i++) {
                if (models.get(i).getVerseId() == 1) {
                    models.add(i, null);
                    i++;
                }
            }
        }

        if (juzId != 0) {
            quranAdapter.setQuranIn("juz");

            if (juzId == 30) {
                quranAdapter.setLast(true);
            } else {
                quranAdapter.setNext(juzId + 1);
            }
        } else if (surahId != 0) {
            quranAdapter.setQuranIn("surah");

            if (surahId == 114) {
                quranAdapter.setLast(true);
            } else {
                quranAdapter.setNext(surahId + 1);
            }
        }

        quranAdapter.setModels(models);
        rvQuran.setAdapter(quranAdapter);

        rvQuran.setHasFixedSize(true);
        rvQuran.setLayoutManager(new LinearLayoutManager(this));
        rvQuran.scrollToPosition(position);

        quranAdapter.setArabTextSize(Integer.parseInt(preferences.getString(getResources().getString(R.string.key_arab_font_size), "24")));
        quranAdapter.setTranslationTextSize(Integer.parseInt(preferences.getString(getResources().getString(R.string.key_translation_font_size), "18")));
        tvNumber.setText(String.valueOf(number));
        tvTitle.setText(title);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar toolbar = findViewById(R.id.tolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),QuranMenuActivity.class));
                finish();
            }
        });
        showBannerAd();
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

    private void showBannerAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        avBanner.loadAd(adRequest);
    }
}
