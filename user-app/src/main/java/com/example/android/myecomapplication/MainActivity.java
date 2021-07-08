package com.example.android.myecomapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.android.models.Cart;
import com.example.android.models.Product;
import com.example.android.models.Variant;
import com.example.android.myecomapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   ActivityMainBinding binding;
   Cart cart;
   List<Product> productList=new ArrayList<>();

    ProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        adapterCallbackForProducts();
    }
    /*
    *
    * Callback of adapter created for product
     */
    private void adapterCallbackForProducts() {
        //get the products added by admin
        this.productList = ProductHelper.getProducts();
        AdapterCallbacksListener listener = new AdapterCallbacksListener() {
            @Override
            public void onCartUpdated(int position) {
                updateCartSummary();
                adapter.notifyDataSetChanged();
            }
        };
        //object of cart to initialise cart
        cart = new Cart();
        adapter = new ProductAdapter(this,cart,productList,listener);
        binding.list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.list.setAdapter(adapter);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
    }
    /*
    Update cart summary every single time it is called
     */
    private void updateCartSummary() {
        //check if cart is empty
        if(!cart.cartItems.isEmpty()){
            binding.noOfItems.setText(cart.noOfItems+"items");
            binding.total.setText("₹"+String.format("%.2f",cart.total));

            binding.cartSummary.setVisibility(View.VISIBLE);
        }
        else {
            binding.cartSummary.setVisibility(View.GONE);
        }
    }
}