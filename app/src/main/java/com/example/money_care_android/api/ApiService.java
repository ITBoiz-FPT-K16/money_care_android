package com.example.money_care_android.api;

import com.example.money_care_android.models.CategoryList;
import com.example.money_care_android.models.Expense;
import com.example.money_care_android.models.Income;
import com.example.money_care_android.models.TransactionDetail;
import com.example.money_care_android.models.TransactionOverall;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

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

    @GET("export")
    Call<ResponseBody> exportAll(
            @Header("Authorization") String accessToken
    );

    @GET("export/{year}")
    Call<ResponseBody> exportYear(
            @Header("Authorization") String accessToken,
            @Path("year") int year
    );

    @GET("export/{year}/{month}")
    Call<ResponseBody> exportMonth(
            @Header("Authorization") String accessToken,
            @Path("year") int year,
            @Path("month") int month
    );

    @POST("incomes")
    Call<Income> addIncome(
            @Header("Authorization") String accessToken,
            @Body Income income
    );

    @POST("expenses")
    Call<Expense> addExpense(
            @Header("Authorization") String accessToken,
            @Body Expense expense
            );


}
