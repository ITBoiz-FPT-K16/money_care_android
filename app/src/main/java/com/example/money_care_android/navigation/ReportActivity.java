package com.example.money_care_android.navigation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kal.rackmonthpicker.MonthType;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;

import java.util.Calendar;

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
    private int month, year;
    private Calendar calendar;
    private RackMonthPicker rackMonthPicker;
    Button btnMonth;


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
        btnMonth = findViewById(R.id.selectMonth);
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
                recreate();
            }
        });

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ReportActivity.this, ExportActivity.class);
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

        calendar = Calendar.getInstance();
        month = getIntent().getIntExtra("month", calendar.get(Calendar.MONTH)+1);
        year = getIntent().getIntExtra("year", calendar.get(Calendar.YEAR));
        btnMonth.setText(month + "/" + year);

        rackMonthPicker = new RackMonthPicker(ReportActivity.this)
                .setMonthType(MonthType.TEXT)
                .setPositiveButton(new DateMonthDialogListener() {
                    @Override
                    public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                        // log all in 1 log
                        Log.d("TAG", "onDateMonth: " + month + " " + startDate + " " + endDate + " " + year + " " + monthLabel);
                        Intent intent = new Intent(ReportActivity.this, ReportActivity.class);
                        intent.putExtra("month", month);
                        intent.putExtra("year", year);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(new OnCancelMonthDialogListener() {
                    @Override
                    public void onCancel(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rackMonthPicker.show();
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
        ApiService2.apiService.getTransactionDetail(getToken(), year, month).enqueue(new Callback<TransactionDetail>() {
            @Override
            public void onResponse(Call<TransactionDetail> call, Response<TransactionDetail> response) {
                Log.d("TAG", "onResponse: " + response);
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

        ApiService.apiService.getTransactionOverall(getToken(), year, month).enqueue(new Callback<TransactionOverall>() {
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
                pieChart1.setData(ChartSdize.monthDetail(transactionOverall, false, pieChart1, getApplicationContext(), year, month));
                pieChart2.setData(ChartSdize.monthDetail(transactionOverall, true, pieChart2, getApplicationContext(), year, month));

            }

            @Override
            public void onFailure(Call<TransactionOverall> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }


}
