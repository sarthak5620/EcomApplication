package com.example.android.models;

public class Variant {
     public String name;
        float price;

        public Variant(String name, float price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return String.format("%s @ Rs. %.2f",name, price);
        }
}
