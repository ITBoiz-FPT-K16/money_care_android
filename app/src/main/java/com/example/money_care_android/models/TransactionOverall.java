package com.example.money_care_android.models;

import java.util.ArrayList;

public class TransactionOverall {

    private long totalExpenses;
    private long totalIncomes;

    private ArrayList<CategoryList> categoryLists;

    public TransactionOverall() {
    }

    public TransactionOverall(long totalExpenses, long totalIncomes, ArrayList<CategoryList> categoryLists) {
        this.totalExpenses = totalExpenses;
        this.totalIncomes = totalIncomes;
        this.categoryLists = categoryLists;
    }

    public long getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(long totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public long getTotalIncomes() {
        return totalIncomes;
    }

    public void setTotalIncomes(long totalIncomes) {
        this.totalIncomes = totalIncomes;
    }

    public ArrayList<CategoryList> getCategoryLists() {
        return categoryLists;
    }

    public void setCategoryLists(ArrayList<CategoryList> categoryLists) {
        this.categoryLists = categoryLists;
    }

}
