package com.faresa.production;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initListener();

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    public void initListener(){
        Button button_share = findViewById(R.id.button_share);
        Button button_rating = findViewById(R.id.button_rating);

        button_rating.setOnClickListener(v -> startActivity(new Intent(String.valueOf(getApplicationContext()), Uri.parse("https://play.google.com/store/apps/details?id=com.faresa.production"))));

        button_share.setOnClickListener(v -> {
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_SUBJECT,"Aplikasi Al-Quran");
            share.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.faresa.production");
            startActivity(share);
        });
    }
}
