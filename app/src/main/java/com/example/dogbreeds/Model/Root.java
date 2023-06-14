package com.example.dogbreeds.Model;

import java.util.ArrayList;
import java.util.List;

public class Root{
    public int kode;
    public String pesan;
    public ArrayList<Datum> data;
    private List<ModelUser> dataPengguna;
    public List<ModelUser> getDataPengguna() {
        return dataPengguna;
    }
    public int getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public ArrayList<Datum> getData() {
        return data;
    }
}
