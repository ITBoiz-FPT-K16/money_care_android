package com.example.money_care_android.models;

import java.util.ArrayList;

public class CategoryList {

    private Category category;
    private ArrayList<Expense> expenses;
    private ArrayList<Income> incomes;

    public CategoryList(Category category, ArrayList<Expense> expenses, ArrayList<Income> incomes) {
        this.category = category;
        this.expenses = expenses;
        this.incomes = incomes;
    }

    public CategoryList() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public long getTotalPayment() {
        if (category.isType()) {
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
