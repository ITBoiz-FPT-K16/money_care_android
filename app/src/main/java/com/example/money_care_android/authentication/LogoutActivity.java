package com.example.money_care_android.authentication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.money_care_android.MainActivity;
import com.example.money_care_android.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    GoogleSignInOptions gso;
    GoogleSignInClient gClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gClient = GoogleSignIn.getClient(this, this.gso);
        mAuth.signOut();
        gClient.signOut();
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(LogoutActivity.this, LoginActivity.class));
        Toast.makeText(LogoutActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
    }
}