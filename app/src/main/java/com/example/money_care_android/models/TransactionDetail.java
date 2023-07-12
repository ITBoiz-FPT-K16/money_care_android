package com.example.money_care_android.models;

import java.util.ArrayList;

public class TransactionDetail {

    private long totalExpenses;
    private long totalIncomes;

    private ArrayList<DailyMoney> dateInMonth;

    private ArrayList<Expense> expenseList;

    private ArrayList<Income> incomeList;

    public TransactionDetail() {
    }

    public TransactionDetail(long totalExpenses, long totalIncomes, ArrayList<DailyMoney> dateInMonth, ArrayList<Expense> expenseList, ArrayList<Income> incomeList) {
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

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(ArrayList<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public ArrayList<Income> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(ArrayList<Income> incomeList) {
        this.incomeList = incomeList;
    }
}
