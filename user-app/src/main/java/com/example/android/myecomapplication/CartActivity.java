package com.example.android.myecomapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
        setupHideErrorForEditText();
        placeOrderButton();
        displayCartList();
    }
    private void setupHideErrorForEditText() {
        TextWatcher myTextWatcher =  new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hideError();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        binding.ContactNo.getEditText().addTextChangedListener(myTextWatcher);
    }

    private void hideError() {
        binding.ContactNo.setError(null);
    }

    /*
*
* Display cart items in linear layout consisting of material card view
 */
    private void displayCartList() {
        for(HashMap.Entry<String, CartItem>map : cart.cartItems.entrySet()){
            //Inflate cart items and add them dynamically
            CartItemBinding b= CartItemBinding.inflate(getLayoutInflater());
            //Set product name
            b.productName.setText(""+map.getKey());
            //Set total amount in card view
            b.productPrice.setText("₹"+map.getValue().cost());
            b.productWeight.setText((int) (map.getValue().quantity) + " x ₹" + (map.getValue().cost()) / ((int) (map.getValue().quantity)) + "/kg");
            //Set view and add the card view to linear layout
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

    public void placeOrderButton(){
        binding.OrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = binding.ContactNo.getEditText().getText().toString().trim();
                if (input.isEmpty()) {
                    binding.ContactNo.setError("Please Enter Something!");
                    return;
                }
                else if(!input.matches("^\\d{10}$")){
                    binding.ContactNo.setError("Please enter valid number ");
                    return;
                }
                else{
                    Toast.makeText(context,"yeah order placed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}