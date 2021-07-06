package com.example.android.myecomapplication;

import androidx.appcompat.app.AppCompatActivity;

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
   ProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        adapterCallbackForProducts();
    }

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


    private void adapterCallbackForProducts() {

        AdapterCallbacksListener listener = new AdapterCallbacksListener() {
            @Override
            public void onCartUpdated(int position) {
                updateCartSummary();
            }
        };
        adapter = new ProductAdapter(this,cart,getProducts().subList(0,getProducts().size()));

    }

    private void updateCartSummary() {
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