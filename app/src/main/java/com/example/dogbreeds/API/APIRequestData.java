package com.example.dogbreeds.API;

import com.example.dogbreeds.Model.Root;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve_dogbreeds.php")
    Call<Root> ardRetrieve();

    @FormUrlEncoded
    @POST("create_dogbreeds.php")
    Call<Root> ardCreate(
            @Field("nama") String nama,
            @Field("foto") String foto,
            @Field("ukuran") String ukuran,
            @Field("deskripsi") String deskripsi
    );

    @FormUrlEncoded
    @POST("update_dogbreeds.php")
    Call<Root> ardUpdate(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("foto") String foto,
            @Field("ukuran") String ukuran,
            @Field("deskripsi") String deskripsi
    );

    @FormUrlEncoded
    @POST("delete_dogbreeds.php")
    Call<Root> ardDelete(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("login_dogbreeds.php")
    Call<Root> ardLogin(
            @Field("username") String username,
            @Field("password") String password
    );
}
