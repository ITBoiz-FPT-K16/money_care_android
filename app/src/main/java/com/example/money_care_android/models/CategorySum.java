package com.example.money_care_android.models;

public class CategorySum extends Category {

    private long total;

    public CategorySum(String id, String name, String image, boolean type, long total) {
        super(id, name, image, type);
        this.total = total;
    }

    public CategorySum(String id, String name, String image, boolean type) {
        super(id, name, image, type);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
