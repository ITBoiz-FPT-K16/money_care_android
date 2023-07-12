package com.example.money_care_android.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryList {

    @SerializedName("_id")
    private String id;
    private String name;
    private String image;
    private boolean type;
    private ArrayList<Expense> expenses;
    private ArrayList<Income> incomes;

    public CategoryList(String id, String name, String image, boolean type, ArrayList<Expense> expenses, ArrayList<Income> incomes) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
        this.expenses = expenses;
        this.incomes = incomes;
    }

    public CategoryList() {
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    public ArrayList<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(ArrayList<Income> incomes) {
        this.incomes = incomes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public long getTotalPayment() {
        if (type) {
            return getTotalIncome();
        } else {
            return getTotalExpense();
        }
    }

    private long getTotalExpense() {
        long total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    private long getTotalIncome() {
        long total = 0;
        for (Income income : incomes) {
            total += income.getAmount();
        }
        return total;
    }
}
