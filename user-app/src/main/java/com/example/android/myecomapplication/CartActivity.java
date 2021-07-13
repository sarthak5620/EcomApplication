package com.example.android.myecomapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.myecomapplication.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCartBinding binding ;
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Cart");
    }


}