package com.example.money_care_android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_care_android.R;
import com.example.money_care_android.models.Expense;
import com.example.money_care_android.models.Income;

import java.util.List;

public class NestedTransactionAdapter extends RecyclerView.Adapter<NestedTransactionAdapter.ViewHolder> {

    private List<Expense> expenses;
    private List<Income> incomes;

    public NestedTransactionAdapter(List<Expense> expenses, List<Income> incomes) {
        this.expenses = expenses;
        this.incomes = incomes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.nested_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = null;
        Income income = null;
        if (expenses != null) {
            expense = expenses.get(position);
        } else if (incomes != null) {
            income = incomes.get(position);
        }
        holder.transactionDay.setText(expense != null ? expense.getDay() : income.getDay());
        holder.transactionDate.setText(expense != null ? expense.dateToString() : income.dateToString());
        holder.transactionAmount.setText(expense != null ? String.valueOf(expense.getAmount()) : String.valueOf(income.getAmount()));
        holder.transactionDescription.setText(expense != null ? expense.getDescription() : income.getDescription());
    }

    @Override
    public int getItemCount() {
        if (expenses != null) {
            return expenses.size();
        } else if (incomes != null) {
            return incomes.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView transactionDay, transactionDate, transactionAmount, transactionDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionDay = itemView.findViewById(R.id.idTVTransactionDay);
            transactionDate = itemView.findViewById(R.id.idTVTransactionDate);
            transactionAmount = itemView.findViewById(R.id.idTVTransactionAmount);
            transactionDescription = itemView.findViewById(R.id.idTVTransactionDescription);
        }
    }

}
