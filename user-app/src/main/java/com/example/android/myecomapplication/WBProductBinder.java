package com.example.android.myecomapplication;

import android.content.Context;
import android.view.View;

import com.example.android.models.Cart;
import com.example.android.models.Product;
import com.example.android.myecomapplication.databinding.ItemWbProductBinding;

import java.util.Objects;


public class WBProductBinder {
    private Context context;
    private Cart cart;
    private AdapterCallbacksListener listener;

    /*
    Parameterised constructor for weight based binder
     */
    public WBProductBinder(Context context, Cart cart, AdapterCallbacksListener listener) {
        this.context = context;
        this.cart = cart;
        this.listener = listener;
    }

    /*
    Bind method to bind data and views of weight based products
     */
    public void Bind(Product product, ItemWbProductBinding binding, int position) {
        binding.productName.setText(product.name);
        binding.imageView.setImageResource(Integer.parseInt(String.valueOf(product.imageUrl)));
        binding.priceOfProduct.setText(String.valueOf("₹" + product.pricePerKg + "/kg"));

        //button click event handlers
        eventHandlers(binding, product, position);
        checkProducts(binding, product);
    }
    /*
    Method to check whether item/product present in cart initially
     */
    public void checkProducts(ItemWbProductBinding binding, Product product) {
        //items accessed using key
        if (cart.cartItems.containsKey(product.name)) {
            binding.nonZeroQuantityGroup.setVisibility(View.VISIBLE);
            binding.incBtn.setVisibility(View.GONE);
            binding.qty.setText(String.valueOf(Objects.requireNonNull(cart.cartItems.get(product.name)).quantity + "Kg"));
        } else {
            binding.nonZeroQuantityGroup.setVisibility(View.GONE);
            binding.incBtn.setVisibility(View.VISIBLE);
        }

    }
    /*
  Method for event handlers when any button is clicked
   */
    private void eventHandlers(ItemWbProductBinding b, Product product, int position) {
        b.incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show the weight picker dialog
                showDialog(product, position);
            }
        });

        b.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show the weight picker dialog
                showDialog(product, position);
            }
        });
    }

    private void showDialog(Product product, int position) {
        new WeightPickerDialog(context, cart, position, product, listener).show();
    }

}
