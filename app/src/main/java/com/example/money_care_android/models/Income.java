package com.example.money_care_android.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Income {

    @SerializedName("_id")
    private String id;
    private long amount;
    private String description;
    private Date date;
    private String category;

    public Income(String id, long amount, String description, Date date, String category) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.category = category;
    }

    public Income() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDay() {
        return (String) android.text.format.DateFormat.format("dd", date);
    }

    public String dateToString() {
        return (String) android.text.format.DateFormat.format("EEE MMM yyyy", date);
    }
}
