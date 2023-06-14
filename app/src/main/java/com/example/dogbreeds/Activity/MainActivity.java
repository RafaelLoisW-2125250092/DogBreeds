package com.example.dogbreeds.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dogbreeds.API.APIRequestData;
import com.example.dogbreeds.API.RetroServer;
import com.example.dogbreeds.Adapter.AdapterDog;
import com.example.dogbreeds.Model.Datum;
import com.example.dogbreeds.Model.Root;
import com.example.dogbreeds.R;
import com.example.dogbreeds.Utility.KendaliLogin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvDogBreeds;
    private FloatingActionButton fabTambah, fabLogout;
    private ProgressBar pbDogBreeds;
    private TextView tvWelcome;
    private RecyclerView.Adapter adDogBreeds;
    private RecyclerView.LayoutManager lmDogBreeds;
    private List<Datum> listDogBreeds = new ArrayList<>();

    KendaliLogin KL = new KendaliLogin(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(KL.isLogin(KL.keySP_username)== true){
            rvDogBreeds = findViewById(R.id.rv_dogbreeds);
            fabTambah = findViewById(R.id.fab_tambah);
            pbDogBreeds = findViewById(R.id.pb_dogbreeds);
            fabLogout = findViewById(R.id.fab_logout);
            tvWelcome = findViewById(R.id.tv_welcome);

            lmDogBreeds = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rvDogBreeds.setLayoutManager(lmDogBreeds);
            tvWelcome.setText("Selamat Datang " + KL.getPref(KL.keySP_nama_lengkap));

            fabTambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, TambahActivity.class));
                    finish();
                }
            });

            fabLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KL.setPref(KL.keySP_username, null);
                    KL.setPref(KL.keySP_nama_lengkap,null);
                    KL.setPref(KL.keySP_email, null);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            });


        }else {
            startActivity(new Intent(MainActivity.this, Log.class));
            finish();
        }


    }

    @Override
    protected void onResume(){
        super.onResume();
        retrieveDogBreeds();
    }

    public void retrieveDogBreeds() {
        pbDogBreeds.setVisibility(View.VISIBLE);

        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<Root> proses = ARD.ardRetrieve();

        proses.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                listDogBreeds = response.body().getData();

                adDogBreeds = new AdapterDog(MainActivity.this, listDogBreeds);
                rvDogBreeds.setAdapter(adDogBreeds);
                adDogBreeds.notifyDataSetChanged();

                pbDogBreeds.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                pbDogBreeds.setVisibility(View.GONE);
            }
        });
    }
}