package com.example.money_care_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_care_android.R;
import com.example.money_care_android.models.Category;
import com.example.money_care_android.models.CategorySum;

import java.util.ArrayList;

public class PieDetailAdapter extends RecyclerView.Adapter<PieDetailAdapter.ViewHolder> {

    private ArrayList<CategorySum> categorySumArrayList;
    private Context context;

    public PieDetailAdapter(ArrayList<CategorySum> categorySumArrayList, Context context) {
        this.categorySumArrayList = categorySumArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_idvd_pay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategorySum modal = categorySumArrayList.get(position);
        holder.categoryIcon.setImageResource(Category.getIcon(modal.getId()));
        holder.categoryName.setText(modal.getName());
        holder.categoryAmount.setText(String.valueOf(modal.getTotal()));
    }

    @Override
    public int getItemCount() {
        return (categorySumArrayList == null) ? 0 : categorySumArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryIcon;
        private TextView categoryName, categoryAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.idIVCategoryImage);
            categoryName = itemView.findViewById(R.id.idTVCategoryName);
            categoryAmount = itemView.findViewById(R.id.idTVCategoryAmount);

        }
    }
}
