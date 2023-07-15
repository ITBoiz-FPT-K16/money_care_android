package com.example.money_care_android.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.money_care_android.R;
import com.example.money_care_android.models.Category;
import com.example.money_care_android.models.Income;
import com.example.money_care_android.navigation.addTransaction.CategoryAdapter;
import com.example.money_care_android.navigation.addTransaction.CustomSpinner;

import java.text.SimpleDateFormat;

public class AddTransactionActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener {

    private ImageView backButton;
    private Button addButton;
    private EditText amountText, descriptionText, dateText;
    private int amount;

    boolean type;
    private String description, date;
    private Category category;
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
                category = (Category) categorySpinner.getSelectedItem();
                Log.d("AddTransactionActivity", "onClick: " + amount + " " + description + " " + date + " " + category + " " + type);
                Object transaction = new Object();
                SimpleDateFormat formatDate =new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                if (type) {
                    //add income
                    transaction = new Income(amount, description, Date., category);
                } else {
                    //add expense
                }
            }
        });
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