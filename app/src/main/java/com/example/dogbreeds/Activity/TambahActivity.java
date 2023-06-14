package com.example.dogbreeds.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dogbreeds.API.APIRequestData;
import com.example.dogbreeds.API.RetroServer;
import com.example.dogbreeds.Model.Root;
import com.example.dogbreeds.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {

    private EditText etNama, etFoto, etUkuran, etDeskripsi;
    private Button btnSimpan;
    private String nama, foto, ukuran, deskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etFoto = findViewById(R.id.et_foto);
        etUkuran = findViewById(R.id.et_ukuran);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        btnSimpan = findViewById(R.id.btn_tambah);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama=etNama.getText().toString();
                ukuran=etUkuran.getText().toString();
                foto=etFoto.getText().toString();
                deskripsi=etDeskripsi.getText().toString();

                if(nama.trim().isEmpty()){
                    etNama.setError("Nama Tidak Boleh Kosong");
                } else if (ukuran.trim().isEmpty()) {
                    etUkuran.setError("Ukuran Tidak Boleh Kosong");
                } else if (deskripsi.trim().isEmpty()) {
                    etDeskripsi.setError("Deskripsi Tidak Boleh Kosong");
                }
                else {
                    dogsadd();
                }
            }
        });
    }

    private void dogsadd(){
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<Root> proses = ARD.ardCreate(nama, foto, ukuran, deskripsi);

        proses.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode" + kode + "Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}