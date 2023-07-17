package com.example.money_care_android.navigation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.money_care_android.adapter.CategoryTransactionAdapter;
import com.example.money_care_android.api.ApiService;
import com.example.money_care_android.authentication.LoginActivity;
import com.example.money_care_android.authentication.LogoutActivity;
import com.example.money_care_android.models.CategoryList;
import com.example.money_care_android.models.TransactionOverall;
import com.example.money_care_android.navigation.addTransaction.AddTransactionActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kal.rackmonthpicker.MonthType;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    TextView transaction, report, export, logout, hello, inflowAmount, outflowAmount, totalAmount, viewReport;
    FirebaseUser mUser;

    ExtendedFloatingActionButton addTransaction;

    RecyclerView recyclerView;
    ArrayList<CategoryList> categoryLists;
    private CategoryTransactionAdapter categoryTransactionAdapter;

    int month, year;
    Calendar calendar;
    private RackMonthPicker rackMonthPicker;
    Button btnMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        drawerLayout = findViewById(R.id.drawer_layout);
        menu = findViewById(R.id.menu);
        transaction = findViewById(R.id.transaction);
        recyclerView = findViewById(R.id.transaction_recycler_view);
        report = findViewById(R.id.report);
        export = findViewById(R.id.export);
        logout = findViewById(R.id.logout);
        hello = findViewById(R.id.hello);
        inflowAmount = findViewById(R.id.inflowAmount);
        outflowAmount = findViewById(R.id.outflowAmount);
        totalAmount = findViewById(R.id.totalAmount);
        addTransaction = findViewById(R.id.add_transaction);
        btnMonth = findViewById(R.id.selectMonth);
        viewReport = findViewById(R.id.viewReport);
        // user
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            if (mUser.getDisplayName() != null)
                hello.setText("Hello, " + mUser.getDisplayName());
            else if (mUser.getEmail() != null)
                hello.setText("Hello, " + mUser.getEmail());
        } else {
            startActivity(new Intent(TransactionActivity.this, LoginActivity.class));
        }
        viewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this, ReportActivity.class);
                intent.putExtra("month", month);
                intent.putExtra("year", year);
                startActivity(intent);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });

        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(TransactionActivity.this, ReportActivity.class);
            }
        });

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(TransactionActivity.this, ExportActivity.class);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(TransactionActivity.this, LogoutActivity.class);
            }
        });

        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionActivity.this, AddTransactionActivity.class));
            }
        });

        calendar = Calendar.getInstance();
        month = getIntent().getIntExtra("month", calendar.get(Calendar.MONTH)+1);
        year = getIntent().getIntExtra("year", calendar.get(Calendar.YEAR));
        btnMonth.setText(month + "/" + year);
        rackMonthPicker = new RackMonthPicker(TransactionActivity.this)
                .setMonthType(MonthType.TEXT)
                .setPositiveButton(new DateMonthDialogListener() {
                    @Override
                    public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                        // log all in 1 log
                        Log.d("TAG", "onDateMonth: " + month + " " + startDate + " " + endDate + " " + year + " " + monthLabel);
                        Intent intent = new Intent(TransactionActivity.this, TransactionActivity.class);
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

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public String getToken() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    void getChartData() {
        ApiService.apiService.getTransactionOverall(getToken(), year, month).enqueue(new Callback<TransactionOverall>() {
            @Override
            public void onResponse(Call<TransactionOverall> call, Response<TransactionOverall> response) {
                if (response.raw().code() == 403 || response.raw().code() == 401) {
                    Toast.makeText(TransactionActivity.this, "Please login again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TransactionActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }

                TransactionOverall transactionOverall = response.body();

                if (transactionOverall == null) return;
                categoryLists = transactionOverall.getCategoryLists();
                LinearLayoutManager layoutManager = new LinearLayoutManager(TransactionActivity.this, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                categoryTransactionAdapter = new CategoryTransactionAdapter(categoryLists, TransactionActivity.this);
                recyclerView.setAdapter(categoryTransactionAdapter);

                inflowAmount.setText(String.valueOf(transactionOverall.getTotalIncomes()));
                outflowAmount.setText(String.valueOf(transactionOverall.getTotalExpenses()));
                totalAmount.setText(String.valueOf(transactionOverall.getTotalIncomes() - transactionOverall.getTotalExpenses()));

            }

            @Override
            public void onFailure(Call<TransactionOverall> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });

    }
}
