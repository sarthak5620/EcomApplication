package com.example.android.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AddProduct {
    public static void AddProducts(HashMap<String, Product> products) {
        Scanner sc = new Scanner(System.in);
        System.out.println("To add Weight based product choose 1 else 2");
        int choice = sc.nextInt();
        if (choice == 1) AddWBP(products);
        if (choice == 2) AddVBP(products);
    }
    //To add weight based product
    public static void AddWBP(HashMap<String, Product> products) {
        Scanner sc = new Scanner(System.in);
        //Details of the product
        System.out.println("\nEnter the name of product: ");
        String key = sc.next();
        System.out.print("Enter image URL of the product: ");
        String imageURL = sc.next();
        System.out.print("Enter minimum quantity of the product: ");
        float minQty = sc.nextFloat();

        System.out.print("Enter price per kg: ");
        float pricePerKg = sc.nextFloat();
        Product WProduct = new Product(key, imageURL, minQty, pricePerKg);
        products.put(key, WProduct);
        System.out.println("Product added successfully!");
    }
    //To add variant based product
    public static void AddVBP(HashMap<String, Product> products) {
        //Details of the product
        System.out.println("Enter the name of the product you want to add :");
        Scanner sc = new Scanner(System.in);
        String key = sc.next();
        System.out.println("Enter image URL :");
        String imageURL = sc.next();
        System.out.println("Enter the number of variants : ");
        int totalVariants = sc.nextInt();
        //Keeping all kinds of variants in a list
        List<Variant> var = new ArrayList<>();
        System.out.println("Enter the variants : ");
        for (int i = 0; i < totalVariants; i++) {
            //Details of price of product
            String amount = sc.next();
            float price = sc.nextFloat();
            Variant variants = new Variant(amount, price);
            var.add(variants);
        }
        Product product2 = new Product(key, imageURL, var);
        products.put(key, product2);

        System.out.println("Your product is successfully added ");
    }
    //To edit a product present
    public static void editProducts(HashMap<String, Product> products) {
        Scanner sc = new Scanner(System.in);
        List<Variant> var = new ArrayList<>();
        //Details of the product
        System.out.println("Enter the product name you want to edit :");
        String productName = sc.next();
        String key1 = productName;
        //Check whether product present in list
        if (!products.containsKey(productName)) {
            System.out.println("The product is not present in the list");
            //If product is weight based
        } else if (products.get(productName).type == ProductType.TYPE_WBP) {
            System.out.println("Enter new name : ");
            productName = sc.next();
            String key2 = productName;
            System.out.println("Enter new image URL :");
            String imageURL = sc.next();
            System.out.println("Enter new minimum quantity");
            float minQty = sc.nextFloat();
            System.out.println("Enter price per kg");
            float pricerPerKg = sc.nextFloat();
            Product product = new Product(productName, imageURL, minQty, pricerPerKg);
            products.put(key2, product);
            System.out.println("Successfully edited");
            //If product is variant based
        } else if (products.get(productName).type == ProductType.TYPE_VBP) {
            //Details of the product
            System.out.println("Enter new name - ");
            productName = sc.next();
            String key2 = productName;
            System.out.println("Enter new image URL - ");
            String imageURL = sc.next();
            System.out.println("Enter new no of variants - ");
            int noOfVariants = sc.nextInt();
            System.out.println("Enter new variants - ");
            for (int i = 0; i < noOfVariants; i++) {
                String amount = sc.next();
                float price = sc.nextFloat();
                Variant variants = new Variant(amount, price);
                var.add(variants);
            }
            Product product = new Product(productName, imageURL, var);
            products.put(key2, product);
            System.out.println("Successfully edited");
        } else {
            System.out.println("Invalid product");
        }
        products.remove(key1);
    }
}
