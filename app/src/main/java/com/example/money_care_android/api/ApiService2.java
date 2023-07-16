package com.example.money_care_android.api;

import com.example.money_care_android.models.TransactionDetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiService2 {


    Gson gson2 = new GsonBuilder()
            .setDateFormat("EEE MMM dd yyyy").create();

    ApiService2 apiService = new Retrofit.Builder()
            .baseUrl("http://18.139.221.196:80/")
            .addConverterFactory(GsonConverterFactory.create(gson2))
            .build()
            .create(ApiService2.class);

    @GET("transactions/{year}/{month}/detail")
    Call<TransactionDetail> getTransactionDetail(
            @Header("Authorization") String accessToken,
            @Path("year") int year,
            @Path("month") int month
    );
}
