package com.example.money_care_android.models;
import android.graphics.Color;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class ChartSdize {

    public static BarData lastThisMonth(int lastMonth, int thisMonth) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        //two colors for two bar entry, the first one is darker than the second one
        int[] colorClassArray = new int[]{
                Color.parseColor("#ff5e5e"),
                Color.parseColor("#ff9d9d")
        };
        barEntries.add(new BarEntry(1, lastMonth));
        barEntries.add(new BarEntry(2, thisMonth));
        BarDataSet barDataSet = new BarDataSet(barEntries, "Tổng chi");
        barDataSet.setColors(colorClassArray);
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);

        return barData;
    }

    //Generate PieData base on Category
    //transactionOverall.getCategoryLists() is a list of CategoryList contains Category and list of Payment
    public static PieData monthDetail(TransactionOverall transactionOverall) {
        int[] colorClassArray = new int[]{
                Color.parseColor("#f44336"),
                Color.parseColor("#e91e63"),
                Color.parseColor("#9c27b0"),
                Color.parseColor("#673ab7"),
                Color.parseColor("#3f51b5"),
                Color.parseColor("#2196f3"),
                Color.parseColor("#03a9f4"),
                Color.parseColor("#00bcd4"),
                Color.parseColor("#009688"),
                Color.parseColor("#4caf50"),
                Color.parseColor("#8bc34a"),
                Color.parseColor("#cddc39"),
                Color.parseColor("#ffeb3b"),
                Color.parseColor("#ffc107"),
                Color.parseColor("#ff9800"),
                Color.parseColor("#ff5722"),
                Color.parseColor("#795548"),
                Color.parseColor("#9e9e9e"),
                Color.parseColor("#607d8b")
        };
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (CategoryList categoryList : transactionOverall.getCategoryLists()) {
            pieEntries.add(new PieEntry(categoryList.getTotalPayment(), categoryList.getName()));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(colorClassArray);
        PieData pieData = new PieData();
        pieData.addDataSet(pieDataSet);
        return pieData;
    }

//    public static BarData monthBarData() {}
}
