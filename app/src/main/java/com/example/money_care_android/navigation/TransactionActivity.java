package com.example.money_care_android.navigation;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.money_care_android.R;
import com.example.money_care_android.adapter.CategoryTransactionAdapter;
import com.example.money_care_android.adapter.PieDetailAdapter;
import com.example.money_care_android.api.ApiService;
import com.example.money_care_android.api.ApiService2;
import com.example.money_care_android.authentication.LoginActivity;
import com.example.money_care_android.authentication.LogoutActivity;
import com.example.money_care_android.models.CategoryList;
import com.example.money_care_android.models.ChartSdize;
import com.example.money_care_android.models.TransactionDetail;
import com.example.money_care_android.models.TransactionOverall;
import com.example.money_care_android.navigation.details.PieDetailActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.time.Month;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    TextView transaction, report, export, logout, hello, inflowAmount, outflowAmount, totalAmount;
    FirebaseUser mUser;

    ExtendedFloatingActionButton addTransaction;

    RecyclerView recyclerView;
    ArrayList<CategoryList> categoryLists;
    private CategoryTransactionAdapter categoryTransactionAdapter;

    int month, year;


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
        month = (new Date()).getMonth()+1;
        year = (new Date()).getYear()+1900;
        inflowAmount = findViewById(R.id.inflowAmount);
        outflowAmount = findViewById(R.id.outflowAmount);
        totalAmount = findViewById(R.id.totalAmount);
        if (getIntent()!=null){
            month = getIntent().getIntExtra("month", month);
            year = getIntent().getIntExtra("year", year);
        }
        addTransaction = findViewById(R.id.add_transaction);
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
        int year = getIntent().getIntExtra("year", 2023); //hardcode
        int month = getIntent().getIntExtra("month", 07);
        boolean type = getIntent().getBooleanExtra("type", true);
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
