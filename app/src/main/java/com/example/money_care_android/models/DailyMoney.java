package com.example.money_care_android.models;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyMoney {

    private Date date;
    private long totalExpenses;
    private long totalIncomes;

    public DailyMoney(Date date, long totalExpenses, long totalIncomes) {
        this.date = date;
        this.totalExpenses = totalExpenses;
        this.totalIncomes = totalIncomes;
    }

    public DailyMoney() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public int getDateString() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd");
        return Integer.parseInt(formatter.format(date));

    }
}
