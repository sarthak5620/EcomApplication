package com.example.android.myecomapplication;

import android.content.Context;
import android.view.View;

import com.example.android.models.Cart;
import com.example.android.models.Product;
import com.example.android.myecomapplication.databinding.ItemWbProductBinding;


public class WBProductBinder {
    private Context context;
    private Cart cart;
    private Product product ;
    private AdapterCallbacksListener listener;

    public WBProductBinder(Context context, Cart cart, Product product, AdapterCallbacksListener listener) {
        this.context = context;
        this.cart = cart;
        this.product = product;
        this.listener = listener;
    }

    public void Bind(Product product, ItemWbProductBinding binding, int position){
        binding.productName.setText(product.name);
        binding.imageView.setImageResource(Integer.parseInt(product.imageUrl));
        binding.priceOfProduct.setText(String.valueOf("₹" + product.pricePerKg + "/kg")) ;
        eventHandlers(binding, product,position);
        checkProducts(binding, product);
    }

    public void checkProducts(ItemWbProductBinding binding, Product product) {
        if(cart.cartItems.containsKey(product.name)){
            binding.nonZeroQuantityGroup.setVisibility(View.VISIBLE);
            binding.incBtn.setVisibility(View.GONE);
            binding.qty.setText(String.valueOf(cart.cartItems.get(product.name).quantity + "Kg"));
        }
        else{
            binding.nonZeroQuantityGroup.setVisibility(View.GONE);
            binding.incBtn.setVisibility(View.VISIBLE);
        }

    }

    private void eventHandlers(ItemWbProductBinding b, Product product, int position) {
        b.incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(product,position);
            }
        });

        b.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(product,position);
            }
        });
    }

    private void showDialog(Product product, int position) {
        new WeightPickerDialog(context,cart,position,product,listener).show();
    }

    public interface AdapterCallbacksListener {
        void onCartUpdated(int position);
    }
}
