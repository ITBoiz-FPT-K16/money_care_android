package com.example.money_care_android.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.money_care_android.MainActivity;
import com.example.money_care_android.R;
import com.example.money_care_android.api.ApiService;
import com.example.money_care_android.api.ApiService2;
import com.example.money_care_android.authentication.LoginActivity;
import com.example.money_care_android.authentication.LogoutActivity;
import com.example.money_care_android.models.ChartSdize;
import com.example.money_care_android.models.TransactionDetail;
import com.example.money_care_android.models.TransactionOverall;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.time.Month;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    TextView transaction, report, export, logout, hello;
    FirebaseUser mUser;

    ExtendedFloatingActionButton addTransaction;

    BarChart barChart;
    PieChart pieChart1;
    PieChart pieChart2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        drawerLayout = findViewById(R.id.drawer_layout);
        menu = findViewById(R.id.menu);
        transaction = findViewById(R.id.transaction);
        report = findViewById(R.id.report);
        export = findViewById(R.id.export);
        logout = findViewById(R.id.logout);
        hello = findViewById(R.id.hello);
        barChart = findViewById(R.id.bar_chart);
        pieChart1 = findViewById(R.id.pie_chart1);
        pieChart2 = findViewById(R.id.pie_chart2);
        addTransaction = findViewById(R.id.add_transaction);
        // user
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            if (mUser.getDisplayName() != null)
                hello.setText("Hello, " + mUser.getDisplayName());
            else if (mUser.getEmail() != null)
                hello.setText("Hello, " + mUser.getEmail());
        } else {
            startActivity(new Intent(ReportActivity.this, LoginActivity.class));
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });

        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ReportActivity.this, TransactionActivity.class);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ReportActivity.this, ReportActivity.class);
            }
        });

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ReportActivity.this, LogoutActivity.class);
            }
        });

        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this, AddTransactionActivity.class));
            }
        });

        getChartData();
        Log.d("TAG", "onCreate: " + getToken());
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer((int) GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            drawerLayout.closeDrawer((int) GravityCompat.START);
        }
    }

    public static void redirectActivity(AppCompatActivity a1, Class a2) {
        Intent intent = new Intent(a1, a2);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        a1.startActivity(intent);
        a1.finish();
    }

    public String getToken() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    void getChartData() {
        ApiService2.apiService.getTransactionDetail(getToken(), 2023, 7).enqueue(new Callback<TransactionDetail>() {
            @Override
            public void onResponse(Call<TransactionDetail> call, Response<TransactionDetail> response) {
                if (response.raw().code() == 403 || response.raw().code() == 401) {
                    Toast.makeText(ReportActivity.this, "Please login again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReportActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }

                TransactionDetail transactionDetail = response.body();

                barChart.setData(ChartSdize.monthBarData(transactionDetail, barChart));
            }

            @Override
            public void onFailure(Call<TransactionDetail> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });

        ApiService.apiService.getTransactionOverall(getToken(), 2023, 07).enqueue(new Callback<TransactionOverall>() {
            @Override
            public void onResponse(Call<TransactionOverall> call, Response<TransactionOverall> response) {
                if (response.raw().code() == 403 || response.raw().code() == 401) {
                    Toast.makeText(ReportActivity.this, "Please login again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReportActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }

                TransactionOverall transactionOverall = response.body();

                if (transactionOverall == null) return;
                pieChart1.setData(ChartSdize.monthDetail(transactionOverall, false, pieChart1, getApplicationContext(), 2023, 07));
                pieChart2.setData(ChartSdize.monthDetail(transactionOverall, true, pieChart2, getApplicationContext(), 2023, 07));

            }

            @Override
            public void onFailure(Call<TransactionOverall> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }
}
