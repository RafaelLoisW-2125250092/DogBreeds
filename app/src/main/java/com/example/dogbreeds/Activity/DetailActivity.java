package com.example.dogbreeds.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dogbreeds.R;

public class DetailActivity extends AppCompatActivity {
    private TextView tvNama, tvUkuran, tvDeskripsi;
    private String yID, yNama, yFoto, yUkuran, yDeskripsi, yPath;
    private ImageView ivGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        yNama = intent.getStringExtra("xNama");
        yUkuran = intent.getStringExtra("xUkuran");
        yDeskripsi = intent.getStringExtra("xDeskripsi");
        yFoto = intent.getStringExtra("xPathFoto");

        tvNama = findViewById(R.id.tv_namaDetail);
        tvUkuran = findViewById(R.id.tv_ukuranDetail);
        tvDeskripsi = findViewById(R.id.tv_deskripsiDetail);
        ivGambar = findViewById(R.id.iv_gambarDetail);

        tvNama.setText(yNama);
        tvUkuran.setText(yUkuran);
        tvDeskripsi.setText(yDeskripsi);
        Glide.with(DetailActivity.this).load(yFoto).into(ivGambar);



    }
}