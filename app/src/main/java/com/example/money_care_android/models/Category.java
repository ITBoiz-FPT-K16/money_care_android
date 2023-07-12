package com.example.money_care_android.models;

public class Category {
    private String id;
    private String name;
    private String image;
    private boolean type;

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
}
