package com.faresa.alquran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AboutUsActivity extends AppCompatActivity {
    ConstraintLayout satu,dua,tiga;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        satu = findViewById(R.id.relativeLayout);
        satu.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/makhalibagas/"))));
        dua = findViewById(R.id.relativeLayout2);
        dua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/faresa_pn/")));
            }
        });
        tiga = findViewById(R.id.relativeLayout3);
        tiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/silviniann_/")));
            }
        });
        initListener();
        loadInterstitialAd();
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    private void loadInterstitialAd() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_id));
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    public void initListener(){
        Button button_share = findViewById(R.id.button_share);
        Button button_rating = findViewById(R.id.button_rating);

        button_rating.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(getString(R.string.share_link)))));

        button_share.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String sAux = getString(R.string.share) + "\n";
            sAux = sAux + getString(R.string.share_link) + "\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        });
    }
}
