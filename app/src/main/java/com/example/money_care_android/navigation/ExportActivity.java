package com.example.money_care_android.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money_care_android.R;
import com.example.money_care_android.authentication.LoginActivity;
import com.example.money_care_android.authentication.LogoutActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ExportActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    TextView transaction, report, export, logout, hello;
    FirebaseUser mUser;

    ExtendedFloatingActionButton addTransaction;

    Button btnMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        drawerLayout = findViewById(R.id.drawer_layout);
        menu = findViewById(R.id.menu);
        transaction = findViewById(R.id.transaction);
        report = findViewById(R.id.report);
        export = findViewById(R.id.export);
        logout = findViewById(R.id.logout);
        hello = findViewById(R.id.hello);
        addTransaction = findViewById(R.id.add_transaction);
        btnMonth = findViewById(R.id.selectMonth);
        btnMonth.setVisibility(View.INVISIBLE);
        // user
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            if (mUser.getDisplayName() != null)
                hello.setText("Hello, " + mUser.getDisplayName());
            else if (mUser.getEmail() != null)
                hello.setText("Hello, " + mUser.getEmail());
        } else {
            startActivity(new Intent(ExportActivity.this, LoginActivity.class));
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
                redirectActivity(ExportActivity.this, TransactionActivity.class);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ExportActivity.this, ReportActivity.class);
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
                redirectActivity(ExportActivity.this, LogoutActivity.class);
            }
        });

        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExportActivity.this, AddTransactionActivity.class));
            }
        });
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
}
