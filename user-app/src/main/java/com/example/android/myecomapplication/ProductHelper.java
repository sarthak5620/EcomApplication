package com.example.android.myecomapplication;

import com.example.android.models.Product;
import com.example.android.models.Variant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductHelper {
    public static List<Product> getProducts(){
        List<Product>products=new ArrayList<>(
                Arrays.asList(
                        new Product("Kiwi", R.drawable.kiwi, new ArrayList<>(Arrays.asList(
                                new Variant("500g", 90),
                                new Variant("1kg", 150)))),

                        new Product("Orange",R.drawable.orange,70,0.2f),
                        new Product("Apple",R.drawable.apple,new ArrayList<>(Arrays.asList(
                                new Variant("1Kg",100),
                                new Variant("2kg",180)
                        ))),
                        new Product("Onion",R.drawable.onion,new ArrayList<>(Arrays.asList(
                                new Variant("1.5Kg",70),
                                new Variant("5Kg",300)
                        ))),
                        new Product("Sugar",R.drawable.sugar,50,1),
                        new Product("Dal",R.drawable.dal,40,0.1f),
                        new Product("Dove Shampoo",R.drawable.dove,142,1),
                        new Product("Lux Soap",R.drawable.lux,50,1),
                        new Product("Potato", R.drawable.potato, new ArrayList<>(Arrays.asList(
                                new Variant("500g", 25),
                                new Variant("1kg", 40)))),
                        new Product("Tomato", R.drawable.tomato, new ArrayList<>(Arrays.asList(
                                new Variant("1kg", 20),
                                new Variant("2kg", 35)
                        ))),
                        new Product("Mango",R.drawable.mango,new ArrayList<>(Arrays.asList(
                                new Variant("1Kg",150),
                                new Variant("2kg",290))))

                ) );


        return products;
    }
}
