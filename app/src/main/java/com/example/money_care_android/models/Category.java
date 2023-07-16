package com.example.money_care_android.models;

import com.example.money_care_android.R;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("_id")
    private String id;
    private String name;
    private String image;
    private boolean type;

    public static ArrayList getCategoryList() {
        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("649a962cbea2ffe7898224af", "Food & Beverage", String.valueOf(R.drawable.ic_0), false));
        categoryList.add(new Category("649a962cbea2ffe7898224ba", "Vehicle Maintenance", String.valueOf(R.drawable.ic_1), false));
        categoryList.add(new Category("649a962cbea2ffe7898224b6", "Television Bill", String.valueOf(R.drawable.ic_2), false));
        categoryList.add(new Category("649a962cbea2ffe7898224b4", "Electricity Bill", String.valueOf(R.drawable.ic_3), false));
        categoryList.add(new Category("649a962cbea2ffe7898224b8", "Other Utility Bills", String.valueOf(R.drawable.ic_4), false));
        categoryList.add(new Category("649a962cbea2ffe7898224bd", "Education", String.valueOf(R.drawable.ic_5), false));
        categoryList.add(new Category("649a962cbea2ffe7898224c6", "Streaming Service", String.valueOf(R.drawable.ic_6), false));
        categoryList.add(new Category("649a962cbea2ffe7898224b2", "Water Bill", String.valueOf(R.drawable.ic_7), false));
        categoryList.add(new Category("649a962cbea2ffe7898224c8", "Investment", String.valueOf(R.drawable.ic_8), false));
        categoryList.add(new Category("649a962cbea2ffe7898224c9", "Pay Interest", String.valueOf(R.drawable.ic_9), false));
        categoryList.add(new Category("649a962cbea2ffe7898224cc", "Salary", String.valueOf(R.drawable.ic_10), true));
        categoryList.add(new Category("649a962cbea2ffe7898224ca", "Outgoing Transfer", String.valueOf(R.drawable.ic_11), false));
        categoryList.add(new Category("649a962cbea2ffe7898224b9", "Home Maintainance", String.valueOf(R.drawable.ic_12), false));
        categoryList.add(new Category("649a962cbea2ffe7898224cd", "Other Income", String.valueOf(R.drawable.ic_13), true));
        categoryList.add(new Category("649a962cbea2ffe7898224b0", "Transportation", String.valueOf(R.drawable.ic_14), false));
        categoryList.add(new Category("649a962cbea2ffe7898224b1", "Rentals", String.valueOf(R.drawable.ic_15), false));
        categoryList.add(new Category("649a962cbea2ffe7898224c1", "Home Services", String.valueOf(R.drawable.ic_16), false));
        categoryList.add(new Category("649a962cbea2ffe7898224c3", "Fitness", String.valueOf(R.drawable.ic_17), false));
        categoryList.add(new Category("649a962cbea2ffe7898224c0", "Pets", String.valueOf(R.drawable.ic_18), false));
        categoryList.add(new Category("649a962cbea2ffe7898224b3", "Phone Bill", String.valueOf(R.drawable.ic_19), false));
        categoryList.add(new Category("649a962cbea2ffe7898224b5", "Gas Bill", String.valueOf(R.drawable.ic_20), false));
        categoryList.add(new Category("649a962cbea2ffe7898224bb", "Medical Check Up", String.valueOf(R.drawable.ic_21), false));
        categoryList.add(new Category("649a962cbea2ffe7898224be", "Houseware", String.valueOf(R.drawable.ic_22), false));
        categoryList.add(new Category("649a962cbea2ffe7898224ce", "Incoming Transfer", String.valueOf(R.drawable.ic_23), true));
        categoryList.add(new Category("649a962cbea2ffe7898224c2", "Other Expense", String.valueOf(R.drawable.ic_24), false));
        categoryList.add(new Category("649a962cbea2ffe7898224b7", "Internet Bill", String.valueOf(R.drawable.ic_25), false));
        categoryList.add(new Category("649a962cbea2ffe7898224c7", "Fun Money", String.valueOf(R.drawable.ic_26), false));
        categoryList.add(new Category("649a962cbea2ffe7898224bc", "Insurances", String.valueOf(R.drawable.ic_27), false));
        categoryList.add(new Category("649a962cbea2ffe7898224bf", "Personal Items", String.valueOf(R.drawable.ic_28), false));
        categoryList.add(new Category("649a962cbea2ffe7898224c5", "Gifts & Donations", String.valueOf(R.drawable.ic_29), false));
        categoryList.add(new Category("649a962cbea2ffe7898224cb", "Collect Interest", String.valueOf(R.drawable.ic_30), true));
        categoryList.add(new Category("649a962cbea2ffe7898224c4", "Makeup", String.valueOf(R.drawable.ic_31), false));
        return categoryList;
    }

    public Category(String id, String name, String image, boolean type) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
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

    public static int getIcon(String id) {
        switch (id) {
            case "649a962cbea2ffe7898224ba":
                return R.drawable.ic_1;
            case "649a962cbea2ffe7898224b6":
                return R.drawable.ic_2;
            case "649a962cbea2ffe7898224b4":
                return R.drawable.ic_3;
            case "649a962cbea2ffe7898224b8":
                return R.drawable.ic_4;
            case "649a962cbea2ffe7898224bd":
                return R.drawable.ic_5;
            case "649a962cbea2ffe7898224c6":
                return R.drawable.ic_6;
            case "649a962cbea2ffe7898224b2":
                return R.drawable.ic_7;
            case "649a962cbea2ffe7898224c8":
                return R.drawable.ic_8;
            case "649a962cbea2ffe7898224c9":
                return R.drawable.ic_9;
            case "649a962cbea2ffe7898224cc":
                return R.drawable.ic_10;
            case "649a962cbea2ffe7898224ca":
                return R.drawable.ic_11;
            case "649a962cbea2ffe7898224b9":
                return R.drawable.ic_12;
            case "649a962cbea2ffe7898224cd":
                return R.drawable.ic_13;
            case "649a962cbea2ffe7898224b0":
                return R.drawable.ic_14;
            case "649a962cbea2ffe7898224b1":
                return R.drawable.ic_15;
            case "649a962cbea2ffe7898224c1":
                return R.drawable.ic_16;
            case "649a962cbea2ffe7898224c3":
                return R.drawable.ic_17;
            case "649a962cbea2ffe7898224c0":
                return R.drawable.ic_18;
            case "649a962cbea2ffe7898224b3":
                return R.drawable.ic_19;
            case "649a962cbea2ffe7898224b5":
                return R.drawable.ic_20;
            case "649a962cbea2ffe7898224bb":
                return R.drawable.ic_21;
            case "649a962cbea2ffe7898224be":
                return R.drawable.ic_22;
            case "649a962cbea2ffe7898224ce":
                return R.drawable.ic_23;
            case "649a962cbea2ffe7898224c2":
                return R.drawable.ic_24;
            case "649a962cbea2ffe7898224b7":
                return R.drawable.ic_25;
            case "649a962cbea2ffe7898224c7":
                return R.drawable.ic_26;
            case "649a962cbea2ffe7898224bc":
                return R.drawable.ic_27;
            case "649a962cbea2ffe7898224bf":
                return R.drawable.ic_28;
            case "649a962cbea2ffe7898224c5":
                return R.drawable.ic_29;
            case "649a962cbea2ffe7898224cb":
                return R.drawable.ic_30;
            case "649a962cbea2ffe7898224c4":
                return R.drawable.ic_31;
            default:
                return R.drawable.ic_1;

        }

    }
}
