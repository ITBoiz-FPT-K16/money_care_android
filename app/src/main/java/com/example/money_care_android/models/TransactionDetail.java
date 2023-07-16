package com.example.money_care_android.models;

import java.util.ArrayList;

public class TransactionDetail {

    private long totalExpenses;
    private long totalIncomes;

    private ArrayList<DailyMoney> dateInMonth;

    private ArrayList<CategorySum> expenseList;

    private ArrayList<CategorySum> incomeList;

    public TransactionDetail() {
    }

    public TransactionDetail(long totalExpenses, long totalIncomes, ArrayList<DailyMoney> dateInMonth, ArrayList<CategorySum> expenseList, ArrayList<CategorySum> incomeList) {
        this.totalExpenses = totalExpenses;
        this.totalIncomes = totalIncomes;
        this.dateInMonth = dateInMonth;
        this.expenseList = expenseList;
        this.incomeList = incomeList;
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

    public ArrayList<DailyMoney> getDateInMonth() {
        return dateInMonth;
    }

    public void setDateInMonth(ArrayList<DailyMoney> dateInMonth) {
        this.dateInMonth = dateInMonth;
    }

    public ArrayList<CategorySum> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(ArrayList<CategorySum> expenseList) {
        this.expenseList = expenseList;
    }

    public ArrayList<CategorySum> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(ArrayList<CategorySum> incomeList) {
        this.incomeList = incomeList;
    }
}
