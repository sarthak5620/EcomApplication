package com.example.android.models;

import java.util.HashMap;
import java.util.Objects;

public class Cart {
   public HashMap<String, CartItem> cartItems = new HashMap<>();
    public float total;
    public int noOfItems;
    private int quantity;

    //Method to add Weight Based Products
    public void add(Product product, float quantity) {

        //If selected item is already present in the cart
        if(cartItems.containsKey(product.name)) {
            total += cartItems.get(product.name).cost();
            cartItems.get(product.name).quantity = (int) quantity;
        }

        //Else if adding it for the first time
        else {
            CartItem item = new CartItem(product.name, product.pricePerKg, (int) quantity);
            cartItems.put(product.name, item);
            noOfItems++;
        }
        //Update Cart Summary where total is the total cost of the cart
        total += product.pricePerKg*quantity;

    }

    //Method to add Variant Based Products
    public void add(Product product, Variant variant, int quantity) {
        this.quantity = quantity;

        String key = product.name + " " + variant.name;

        //If selected item is already present in the cart
        if(cartItems.containsKey(key)) {
            Objects.requireNonNull(cartItems.get(key)).quantity++;
        }

        //Else if adding for the first time
        else {
            CartItem item = new CartItem(product.name, variant.price, 1);
            cartItems.put(key, item);
        }

        //Update Cart Summary
        noOfItems++;
        total += variant.price;
    }

    //Method to remove Products
    public void remove(Product product) {
        if(product.type == ProductType.TYPE_WBP)
            removeWBP(product);
        else
            removeAllVBP(product);
    }

    //Method to remove Weight Based Products
    public void removeWBP(Product product) {
     //   total -= cartItems.get(product.name).cost();
      //  noOfItems--;
        cartItems.remove(product.name);
    }

    //Method to remove all Variant Based Products
    //Iterate loop and reach every variant and remove
    public void removeAllVBP(Product product) {
        for(Variant variant : product.variants) {
            String key = product.name + " " + variant.name;
            if(cartItems.containsKey(key)) {
                total -= cartItems.get(key).cost();
                noOfItems -= cartItems.get(key).quantity;
                cartItems.remove(key);
            }
        }
    }

    //Method to decrease the Quantity of Variant Based Products
    public void decrement(Product product, Variant variant) {

        String key = product.name + " " + variant.name;
        cartItems.get(key).quantity--;

        //Update Cart Summary
        total -= variant.price;
        noOfItems--;
        if(cartItems.get(key).quantity == 0)
            cartItems.remove(key);
    }
    @Override
    public String toString() {
        return cartItems.values() + String.format("\n Total Cost is %.2f,\nTotal number of items is %d.", total, noOfItems);
    }
}
