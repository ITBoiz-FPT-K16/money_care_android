package com.example.money_care_android.api;

import com.example.money_care_android.models.CategoryList;
import com.example.money_care_android.models.TransactionDetail;
import com.example.money_care_android.models.TransactionOverall;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();


    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://18.139.221.196:80/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("transactions/{year}/{month}/overall")
    Call<TransactionOverall> getTransactionOverall(
            @Header("Authorization") String accessToken,
            @Path("year") int year,
            @Path("month") int month
    );

    @GET("transactions/total")
    Call<Long> getTransactionTotal(
            @Header("Authorization") String accessToken
    );

    @GET("transactions/{year}/{month}/detail")
    Call<TransactionDetail> getTransactionDetail(
            @Header("Authorization") String accessToken,
            @Path("year") int year,
            @Path("month") int month
    );

}