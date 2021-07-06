package com.example.android.models;

import java.util.List;
//import java.util.Objects;

public class Product {
    public String name;
    public int imageUrl;
    public int type;

    //Properties of Weight Based Products
    public float pricePerKg, minimumQuantity;

    //List of Variant Based Products
    public List<Variant> variants;

    public Product(String name, int imageUrl, float pricePerKg, float minimumQuantity) {
        type = ProductType.TYPE_WBP;
        this.name = name;
        this.imageUrl = imageUrl;
        this.pricePerKg = pricePerKg;
        this.minimumQuantity = minimumQuantity;
    }

    public Product(String name, int imageUrl, List<Variant> variants) {
        type = ProductType.TYPE_VBP;
        this.name = name;
        this.imageUrl = imageUrl;
        this.variants = variants;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (type == ProductType.TYPE_WBP)
            builder.append("Weight Based { ");
        else
            builder.append("Variant Based { ");

        builder.append("Name = ").append(name);

        if (type == ProductType.TYPE_WBP) {
            builder.append(", Minimum Quantity = ").append(minimumQuantity);
            builder.append(", Price Per Kg = ").append(pricePerKg);
        }
        else {
            builder.append(", Variants = ").append(variants);
        }
        builder.append(" } ");
        return builder.toString();
    }

}