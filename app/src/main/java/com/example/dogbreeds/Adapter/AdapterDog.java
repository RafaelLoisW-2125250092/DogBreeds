package com.example.dogbreeds.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dogbreeds.API.APIRequestData;
import com.example.dogbreeds.API.RetroServer;
import com.example.dogbreeds.Activity.DetailActivity;
import com.example.dogbreeds.Activity.MainActivity;
import com.example.dogbreeds.Activity.UbahActivity;
import com.example.dogbreeds.Model.Datum;
import com.example.dogbreeds.Model.Root;
import com.example.dogbreeds.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDog extends RecyclerView.Adapter<AdapterDog.VHDogBreeds>{
    //private final List<Datum> listdogbreeds;
    private Context ctx;
    private final List<Datum> listdogbreeds;

    public AdapterDog(Context ctx, List<Datum> listdogbreeds) {
        this.ctx = ctx;
        this.listdogbreeds = listdogbreeds;
    }

    @NonNull
    @Override
    public VHDogBreeds onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dogbreeds, parent, false);
        return new VHDogBreeds(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHDogBreeds holder, int position) {
        Datum D = listdogbreeds.get(position);

        Log.d("hsxuhfbducjsx", D.getFoto());

        holder.tvId.setText(D.getId());
        holder.tvNama.setText(D.getNama());
        holder.tvUkuran.setText(D.getUkuran());
        holder.tvDeskripsi.setText(D.getDeskripsi());
        holder.tvpathFoto.setText(D.getFoto());
        Glide.with(holder.itemView.getContext())
                .load(D.getFoto())
                .apply(new RequestOptions().override(350,550))
                .into(holder.ivGambar);
    }

    @Override
    public int getItemCount() {
        if (listdogbreeds.isEmpty()) {

        }else {
            return listdogbreeds.size();
        }
        return 0;
    }



    public class VHDogBreeds extends RecyclerView.ViewHolder{
        TextView tvId, tvpathFoto, tvNama, tvUkuran, tvDeskripsi;
        ImageView ivGambar;

        public VHDogBreeds(@NonNull View itemView) {
            super(itemView);


            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvpathFoto = itemView.findViewById(R.id.tv_pathfoto);
            ivGambar = itemView.findViewById(R.id.iv_foto);
            tvUkuran = itemView.findViewById(R.id.tv_ukuran);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Operasi apa yang akan dilakukan?");
                    pesan.setCancelable(true);

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            hapusBreeds(tvId.getText().toString());
                            dialog.dismiss();
                        }
                    });

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent pindah = new Intent(ctx, UbahActivity.class);
                            pindah.putExtra("xID", tvId.getText().toString());
                            pindah.putExtra("xNama", tvNama.getText().toString());
                            pindah.putExtra("xPathFoto", tvpathFoto.getText().toString());
                            pindah.putExtra("xFoto", (String) ivGambar.getTag());
                            pindah.putExtra("xUkuran", tvUkuran.getText().toString());
                            pindah.putExtra("xDeskripsi", tvDeskripsi.getText().toString());
                            ctx.startActivity(pindah);
                        }
                    });

                    pesan.show();
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detail = new Intent(ctx, DetailActivity.class);
                    detail.putExtra("xID", tvId.getText().toString());
                    detail.putExtra("xNama", tvNama.getText().toString());
                    detail.putExtra("xPathFoto", tvpathFoto.getText().toString());
                    detail.putExtra("xFoto", (String) ivGambar.getTag());
                    detail.putExtra("xUkuran", tvUkuran.getText().toString());
                    detail.putExtra("xDeskripsi", tvDeskripsi.getText().toString());
                    ctx.startActivity(detail);
                }
            });
        }
        private void hapusBreeds(String idBreeds){
            APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<Root> proses = ARD.ardDelete(idBreeds);

            proses.enqueue(new Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {
                    String kode = String.valueOf(response.body().getKode());
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode: " + kode + "Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity)ctx).retrieveDogBreeds();
                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

}
