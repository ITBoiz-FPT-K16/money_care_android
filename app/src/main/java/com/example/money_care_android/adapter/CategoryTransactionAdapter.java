package com.example.money_care_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_care_android.R;
import com.example.money_care_android.models.Category;
import com.example.money_care_android.models.CategoryList;
import com.example.money_care_android.models.Expense;
import com.example.money_care_android.models.Income;

import java.util.ArrayList;

public class CategoryTransactionAdapter extends RecyclerView.Adapter<CategoryTransactionAdapter.ViewHolder> {

    private ArrayList<CategoryList> categoryLists;
    private ArrayList<Expense> expenses;
    private ArrayList<Income> incomes;

    private Context context;

    public CategoryTransactionAdapter(ArrayList<CategoryList> categoryLists, Context context) {
        this.categoryLists = categoryLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_rv_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryList modal = categoryLists.get(position);
        holder.categoryIcon.setImageResource(Category.getIcon(modal.getId()));
        holder.categoryName.setText(modal.getName());
        holder.categoryAmount.setText(String.valueOf(modal.getTotalPayment()));
        String transactionAmount = "";
        if(modal.isType()) {
            transactionAmount = modal.getIncomes().size() + " Transactions";
            incomes = modal.getIncomes();
        } else {
            transactionAmount = modal.getExpenses().size() + " Transactions";
            expenses = modal.getExpenses();
        }
        holder.transactionAmount.setText(transactionAmount);

        NestedTransactionAdapter adapter = new NestedTransactionAdapter(expenses, incomes);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerView.setAdapter(adapter);



    }

    @Override
    public int getItemCount() {
        return (categoryLists == null) ? 0 : categoryLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName, categoryAmount, transactionAmount;
        private ImageView categoryIcon;
        private RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.idTVCategoryName);
            categoryAmount = itemView.findViewById(R.id.idTVCategoryAmount);
            transactionAmount = itemView.findViewById(R.id.idTVTransactionAmount);
            categoryIcon = itemView.findViewById(R.id.idIVCategoryImage);
            recyclerView = itemView.findViewById(R.id.idRVTransaction);

        }
    }

}
