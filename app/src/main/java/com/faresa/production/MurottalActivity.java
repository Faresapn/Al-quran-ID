package com.faresa.production;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.faresa.production.adapter.MurottalAdapter;
import com.faresa.production.model.QuranMurottal;
import com.faresa.production.rest.ApiService;
import com.faresa.production.rest.MurottalClient;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MurottalActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_murottal);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#89C8C7")));
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Murottal Al-Qur'an");
            getSupportActionBar().setElevation(0);
        }
        getDataMurottal();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu Sebentar");
        progressDialog.show();

    }

    public void getDataMurottal(){
        ApiService apiService = MurottalClient.getRetrofit().create(ApiService.class);
        Call<List<QuranMurottal>> call = apiService.getDataQuran();

        call.enqueue(new Callback<List<QuranMurottal>>() {
            @Override
            public void onResponse(Call<List<QuranMurottal>> call, Response<List<QuranMurottal>> response) {
                setRecyclerView(response.body());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<QuranMurottal>> call, Throwable t) {

            }
        });
    }
    public void setRecyclerView(List<QuranMurottal> quranMurottals){
        RecyclerView recyclerView = findViewById(R.id.rv_murottal);
        MurottalAdapter murottalAdapter = new MurottalAdapter(this,quranMurottals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(murottalAdapter);

    }
}
