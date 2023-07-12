package com.example.money_care_android.models;

import java.util.ArrayList;

public class CategoryList {

    private Category category;
    private ArrayList<Payment> payments;

    public CategoryList(Category category, ArrayList<Payment> payments) {
        this.category = category;
        this.payments = payments;
    }

    public CategoryList() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }

    //Add payment to list
    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }

    //Remove payment from list

    public void removePayment(Payment payment) {
        this.payments.remove(payment);
    }

    //Get total amount of payments in list (if type = true, get total income, else get total expense)
    public long getTotalAmount(boolean type) {
        long total = 0;
        for (Payment payment : payments) {
            if (payment.isType() == type) {
                total += payment.getAmount();
            }
        }
        return total;
    }

    //Get total amount of payments in list (plus if type = true, minus if type = false)
    public long getTotalAmount() {
        long total = 0;
        for (Payment payment : payments) {
            if (payment.isType()) {
                total += payment.getAmount();
            } else {
                total -= payment.getAmount();
            }
        }
        return total;
    }
}
