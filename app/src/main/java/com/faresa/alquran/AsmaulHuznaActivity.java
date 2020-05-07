package com.faresa.alquran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.faresa.alquran.adapter.AsmaulHuznaAdapter;
import com.faresa.alquran.model.AsmaulHuzna.DataItem;
import com.faresa.alquran.model.AsmaulHuzna.ResponseDataAsmaul;
import com.faresa.alquran.rest.ApiService;
import com.faresa.alquran.rest.RetrofitConfiguration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsmaulHuznaActivity extends AppCompatActivity {

    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asmaul_huzna);

        rv = findViewById(R.id.rv_asmaul);
        rv.setLayoutManager(new GridLayoutManager(this, 3));

        rv.setHasFixedSize(true);
        getData();

    }


    private void getData(){

        ApiService apiService = RetrofitConfiguration.getRetrofitSholat().create(ApiService.class);
        Call<ResponseDataAsmaul> call = apiService.getDataAsmaul();
        call.enqueue(new Callback<ResponseDataAsmaul>() {
            @Override
            public void onResponse(Call<ResponseDataAsmaul> call, Response<ResponseDataAsmaul> response) {
                assert response.body() != null;;

                AsmaulHuznaAdapter asmaulHuznaAdapter = new AsmaulHuznaAdapter(AsmaulHuznaActivity.this,response.body().getData());
                rv.setAdapter(asmaulHuznaAdapter);
            }

            @Override
            public void onFailure(Call<ResponseDataAsmaul> call, Throwable t) {

                Toast.makeText(AsmaulHuznaActivity.this, "error load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
