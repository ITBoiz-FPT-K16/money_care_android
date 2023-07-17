package com.example.money_care_android.navigation.addTransaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.example.money_care_android.navigation.TransactionActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTransactionActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener, DatePickerDialog.OnDateSetListener {

    private ImageView backButton;
    private Button addButton;
    private EditText amountText, descriptionText;

    private Button dateText;
    private long amount;
    boolean type;
    private String description;
    private String category;
    private CustomSpinner categorySpinner;

    private DatePickerDialog datePickerDialog;
    private Date d;
    private Calendar calendar;

    private CategoryAdapter categoryAdapter;

    private int Year, Month, Day;


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
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        d = calendar.getTime();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dateText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(AddTransactionActivity.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setTitle("Date Picker");


                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(AddTransactionActivity.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = Integer.parseInt(amountText.getText().toString());
                description = descriptionText.getText().toString();

                category = ((Category) categorySpinner.getSelectedItem()).getId();
                type = ((Category) categorySpinner.getSelectedItem()).isType();
                if (type==false){
                    Expense transaction = new Expense("",amount, description, d, category);
                    ApiService.apiService.addExpense(getToken(), transaction).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {

                            if (response.raw().code() == 403 || response.raw().code()==401) {
                                Toast.makeText(AddTransactionActivity.this, "Session Expired", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddTransactionActivity.this, LoginActivity.class);
                                startActivity(intent);
                                return;
                            }
                            if (response.raw().code() == 200) {
                                Toast.makeText(AddTransactionActivity.this, "Add Expense Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddTransactionActivity.this, TransactionActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AddTransactionActivity.this, "Add Expense Failed", Toast.LENGTH_SHORT).show();
                                Log.d("AddTransactionActivity", "onResponse: " + response.toString());
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(AddTransactionActivity.this, "Add Expense Failed", Toast.LENGTH_SHORT).show();
                            Log.d("AddTransactionActivity", "onFailure: " + t.getMessage());
                        }
                    });
                } else {

                    Income transaction = new Income("",amount, description, d, category);
                    ApiService.apiService.addIncome(getToken(), (Income) transaction).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {

                            if (response.raw().code() == 403 || response.raw().code()==401) {
                                Toast.makeText(AddTransactionActivity.this, "Session Expired", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddTransactionActivity.this, LoginActivity.class);
                                startActivity(intent);
                                return;
                            }
                            if (response.raw().code() == 200) {
                                Toast.makeText(AddTransactionActivity.this, "Add Income Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddTransactionActivity.this, TransactionActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AddTransactionActivity.this, "Add Income Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(AddTransactionActivity.this, "Add Income Failed", Toast.LENGTH_SHORT).show();
                            Log.d("AddTransactionActivity", "onFailure: " + t.getMessage());
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        d = calendar.getTime();
        dateText.setText(sdf.format(calendar.getTime()));
    }
}