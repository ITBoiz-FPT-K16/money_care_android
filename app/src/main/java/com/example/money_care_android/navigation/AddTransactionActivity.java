package com.example.money_care_android.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.money_care_android.R;
import com.example.money_care_android.api.ApiService;
import com.example.money_care_android.authentication.LoginActivity;
import com.example.money_care_android.models.Category;
import com.example.money_care_android.models.Expense;
import com.example.money_care_android.models.Income;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.example.money_care_android.navigation.addTransaction.CategoryAdapter;
import com.example.money_care_android.navigation.addTransaction.CustomSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTransactionActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener {

    private ImageView backButton;
    private Button addButton;
    private EditText amountText, descriptionText, dateText;
    private int amount;

    boolean type;
    private String description, date;
    private String category;
    private CustomSpinner categorySpinner;


    private CategoryAdapter categoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        categoryAdapter = new CategoryAdapter(this, Category.getCategoryList());
        backButton = findViewById(R.id.back_button);
        amountText = findViewById(R.id.amount);
        descriptionText = findViewById(R.id.description);
        dateText = findViewById(R.id.date);
        addButton = findViewById(R.id.add_transaction);
        categorySpinner = findViewById(R.id.category);
        categorySpinner.setSpinnerEventsListener(this);
        categorySpinner.setAdapter(categoryAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = Integer.parseInt(amountText.getText().toString());
                description = descriptionText.getText().toString();
                date = dateText.getText().toString();
                category = ((Category) categorySpinner.getSelectedItem()).getId();
                type = ((Category) categorySpinner.getSelectedItem()).isType();
                Log.d("AddTransactionActivity", "onClick: " + amount + " " + description + " " + date + " " + category + " " + type);
                Object transaction = new Object();

                SimpleDateFormat formatDate = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

                Date d = new Date();
                try {
                    d = formatDate.parse(date);
                } catch (ParseException e) {
                    Toast.makeText(AddTransactionActivity.this, "Invalid Date", Toast.LENGTH_SHORT).show();
                }
                if (type) {
                    //add income
                    transaction = new Income("", amount, description, d, category);
                } else {
                    //add expense
                    transaction = new Expense("", amount, description, d, category);
                }


                if (type==false){
                    ApiService.apiService.addExpense(getToken(), (Expense) transaction).enqueue(new Callback<Expense>() {
                        @Override
                        public void onResponse(Call<Expense> call, Response<Expense> response) {
                            if (response.raw().code() == 200) {
                                Toast.makeText(AddTransactionActivity.this, "Add Expense Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddTransactionActivity.this, TransactionActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AddTransactionActivity.this, "Add Expense Failed", Toast.LENGTH_SHORT).show();
                            }
                            if (response.raw().code() == 403 || response.raw().code()==401) {
                                Toast.makeText(AddTransactionActivity.this, "Session Expired", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddTransactionActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onFailure(Call<Expense> call, Throwable t) {
                            Toast.makeText(AddTransactionActivity.this, "Add Expense Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    ApiService.apiService.addIncome(getToken(), (Income) transaction).enqueue(new Callback<Income>() {
                        @Override
                        public void onResponse(Call<Income> call, Response<Income> response) {
                            if (response.raw().code() == 200) {
                                Toast.makeText(AddTransactionActivity.this, "Add Income Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddTransactionActivity.this, TransactionActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AddTransactionActivity.this, "Add Income Failed", Toast.LENGTH_SHORT).show();
                            }
                            if (response.raw().code() == 403 || response.raw().code()==401) {
                                Toast.makeText(AddTransactionActivity.this, "Session Expired", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddTransactionActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onFailure(Call<Income> call, Throwable t) {
                            Toast.makeText(AddTransactionActivity.this, "Add Income Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public String getToken() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        categorySpinner.setBackground(getResources().getDrawable(R.drawable.bg_category));
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        categorySpinner.setBackground(getResources().getDrawable(R.drawable.bg_category_up));
    }


}