package com.example.android.models;

public class CartItem {
    String name;
    public float unitPrice, quantity;

    //Details of items in cart
    public CartItem(String name, float unitPrice, float quantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
    //Total cost of the items
    float cost() {
        return unitPrice*quantity;
    }

    @Override
    public String toString() {
        return "\n " + name + String.format(" ( cost = %f X %f = %f", unitPrice, quantity, cost()) + " )";
    }
}
