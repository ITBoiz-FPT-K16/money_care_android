package com.example.money_care_android.navigation.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.money_care_android.MainActivity;
import com.example.money_care_android.R;
import com.example.money_care_android.adapter.PieDetailAdapter;
import com.example.money_care_android.api.ApiService;
import com.example.money_care_android.api.ApiService2;
import com.example.money_care_android.authentication.LoginActivity;
import com.example.money_care_android.models.CategorySum;
import com.example.money_care_android.models.ChartSdize;
import com.example.money_care_android.models.TransactionDetail;
import com.example.money_care_android.models.TransactionOverall;
import com.example.money_care_android.navigation.ReportActivity;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PieDetailActivity extends AppCompatActivity {

    PieChart pieChart;
    private ArrayList<CategorySum> categorySums;
    private PieDetailAdapter pieDetailAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_detail);
        pieChart = findViewById(R.id.pie_chart_detail);
        categorySums = new ArrayList<>();
        recyclerView = findViewById(R.id.idRVCategory);

        getChartData();
    }

    public String getToken() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    void getChartData() {
        int year = getIntent().getIntExtra("year", 2023); //hardcode
        int month = getIntent().getIntExtra("month", 07);
        boolean type = getIntent().getBooleanExtra("type", true);
        ApiService.apiService.getTransactionOverall(getToken(), year, month).enqueue(new Callback<TransactionOverall>() {
            @Override
            public void onResponse(Call<TransactionOverall> call, Response<TransactionOverall> response) {
                if (response.raw().code() == 403 || response.raw().code() == 401) {
                    Toast.makeText(PieDetailActivity.this, "Please login again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PieDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }

                TransactionOverall transactionOverall = response.body();

                if (transactionOverall == null) return;
                pieChart.setData(ChartSdize.monthDetail(transactionOverall, type, pieChart, null, year, month));



            }

            @Override
            public void onFailure(Call<TransactionOverall> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });

        ApiService2.apiService.getTransactionDetail(getToken(), year, month).enqueue(new Callback<TransactionDetail>() {
            @Override
            public void onResponse(Call<TransactionDetail> call, Response<TransactionDetail> response) {
                if (response.raw().code() == 403 || response.raw().code() == 401) {
                    Toast.makeText(PieDetailActivity.this, "Please login again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PieDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }

                TransactionDetail transactionDetail = response.body();
                assert transactionDetail != null;
                if (type) {
                    categorySums = transactionDetail.getIncomeList();
                } else {
                    categorySums = transactionDetail.getExpenseList();
                }
                pieDetailAdapter = new PieDetailAdapter(categorySums, PieDetailActivity.this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(PieDetailActivity.this, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(pieDetailAdapter);

            }

            @Override
            public void onFailure(Call<TransactionDetail> call, Throwable t) {

            }
        });
    }
}