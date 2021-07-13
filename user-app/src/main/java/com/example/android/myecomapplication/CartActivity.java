package com.example.android.myecomapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.models.Cart;
import com.example.android.models.CartItem;
import com.example.android.myecomapplication.databinding.ActivityCartBinding;
import com.example.android.myecomapplication.databinding.CartItemBinding;

import java.util.HashMap;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding ;
    Cart cart;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Cart");
        Intent intent=getIntent();
        cart=(Cart)intent.getSerializableExtra(Constants.KEY);
        showCart();
        displayCartList();
    }

    private void displayCartList() {
        for(HashMap.Entry<String, CartItem>map : cart.cartItems.entrySet()){
            CartItemBinding b= CartItemBinding.inflate(getLayoutInflater());

            b.productName.setText(""+map.getKey());

            b.productPrice.setText("₹"+map.getValue().cost());


            b.productWeight.setText((int) (map.getValue().quantity) + " x ₹" + (map.getValue().cost()) / ((int) (map.getValue().quantity)) + "/kg");
            binding.CartList.addView(b.getRoot());

        }
    }

    /*
    Check number of items in cart
     */
    private void showCart() {
        //If cart is empty
        if(cart.noOfItems == 0){
            Toast.makeText(context,"Nothing to show here!",Toast.LENGTH_SHORT).show();
        }
        //If cart contains exactly one item
        else if(cart.noOfItems == 1){
            binding.noOfItems.setText(String.valueOf(cart.noOfItems + "Item"));
            binding.cartValue.setText(String.valueOf("₹" + cart.total));
        }
        //if cart contains more then one product
        else{
            binding.noOfItems.setText(String.valueOf(cart.noOfItems + "Items"));
            binding.cartValue.setText(String.valueOf("₹" + cart.total));
        }
    }


}