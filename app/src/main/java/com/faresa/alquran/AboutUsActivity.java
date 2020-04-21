package com.faresa.alquran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutUsActivity extends AppCompatActivity {
    ConstraintLayout satu,dua,tiga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        satu = findViewById(R.id.relativeLayout);
        satu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/makhalibagas/")));
            }
        });
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

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    public void initListener(){
        Button button_share = findViewById(R.id.button_share);
        Button button_rating = findViewById(R.id.button_rating);

        button_rating.setOnClickListener(v -> startActivity(new Intent(String.valueOf(getApplicationContext()), Uri.parse("https://play.google.com/store/apps/details?id=com.faresa.alquran"))));

        button_share.setOnClickListener(v -> {
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_SUBJECT,"Aplikasi Al-Quran");
            share.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.faresa.alquran");
            startActivity(share);
        });
    }
}
