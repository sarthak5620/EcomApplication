package com.example.android.models;
import java.util.*;
public class CartConsole {
    public static void addItemInCart(Cart cart, HashMap<String, Product> products){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the product name you want to add in the cart - ");
        String productName = sc.next();

        if(!products.containsKey(productName)){
            System.out.println("Entered product is unavailable");
        }
        //If product is weight based
        else if(products.get(productName).type == ProductType.TYPE_WBP){
            System.out.println("Enter the quantity you want to add");
            float quantity = sc.nextFloat();
            cart.add(products.get(productName),quantity);
            System.out.println("Your product have been successfully added");
        }
        //If product is variant based
        else if(products.get(productName).type == ProductType.TYPE_VBP){
            int i=0;
            System.out.println("Choose among the following variants - ");
            for(Variant variants : products.get(productName).variants){
                System.out.println(i + " : " + variants);
                i++;
            }
            i = sc.nextInt();
            cart.add(products.get(productName),products.get(productName).minimumQuantity);
            System.out.println("Your product have been successfully added");
        }
    }
    //method to edit cart items
    public static void editCartItems(HashMap<String, Product> products) {
        Scanner sc = new Scanner(System.in);
        String productName;
        //Details of the product
        System.out.println("Enter the product name you want to edit - ");
        productName = sc.next();
        //Check if product is available in cart
        if(!products.containsKey(productName)){
            System.out.println("Entered product is unavailable");
        }
        //Check is product is a weight based product
        else if(products.get(productName).type == ProductType.TYPE_WBP){
            Cart cart = new Cart();
            float quantity;
            System.out.println("Enter the new quantity you want to add - ");
            quantity = sc.nextFloat();
            cart.add(products.get(productName),quantity);
        }
        else{
            Cart cart2 = new Cart();
            int i=0;
            System.out.println("Choose among the following variants given below: ");
            for(Variant variants : products.get(productName).variants){
                System.out.println(i + " : " + variants);
                i++;
            }
            i = sc.nextInt();
            cart2.add(products.get(productName),products.get(productName).minimumQuantity);
        }
    }
}
