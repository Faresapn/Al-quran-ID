package com.faresa.production;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.faresa.production.adapter.JuzAdapter;
import com.faresa.production.db.QuranHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

import static com.faresa.production.QuranActivity.EXTRA_JUZ;
import static com.faresa.production.QuranActivity.EXTRA_JUZ_ID;

public class JuzActivity extends AppCompatActivity {
    @BindView(R.id.lvJuz)
    ListView lvJuz;
    @BindView(R.id.avBanner)
    AdView avBanner;

    private QuranHelper quranHelper;
    private JuzAdapter juzAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juz);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#89C8C7")));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Al-Qur'an");
            getSupportActionBar().setElevation(0);
        }

        quranHelper = new QuranHelper(this);
        juzAdapter = new JuzAdapter(this);

        juzAdapter.setList(quranHelper.getAllJuz());
        lvJuz.setAdapter(juzAdapter);

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

    @OnItemClick(R.id.lvJuz)
    public void onJuzItemClick(long id) {
        Intent intent = new Intent(JuzActivity.this, QuranActivity.class);
        intent.putExtra(EXTRA_JUZ, true);
        intent.putExtra(EXTRA_JUZ_ID, (int) id);
        startActivity(intent);
    }

    //masih testing
    private void showBannerAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        avBanner.loadAd(adRequest);
    }
}
