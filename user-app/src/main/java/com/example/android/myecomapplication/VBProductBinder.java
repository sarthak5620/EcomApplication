package com.example.android.myecomapplication;

import android.content.Context;
import android.view.View;

import com.example.android.models.Cart;
import com.example.android.models.Product;
import com.example.android.models.Variant;
import com.example.android.myecomapplication.databinding.ItemVariantBinding;
import com.example.android.myecomapplication.databinding.ItemVbProductBinding;

public class VBProductBinder {
    private Context context;
    private Cart cart;
    private Product product ;
    private AdapterCallbacksListener listener;

    public VBProductBinder(Context context, Cart cart, Product product, AdapterCallbacksListener listener) {
        this.context = context;
        this.cart = cart;
        this.product = product;
        this.listener = listener;
    }

    public void Bind(Product product, ItemVbProductBinding binding, int position){
        binding.productName.setText(product.name);
        binding.imageView.setImageResource(Integer.parseInt(String.valueOf(product.imageUrl)));
        binding.numberOfVariants.setText(String.valueOf(product.variants.size() + " variants"));
        binding.dropBtn.setVisibility(View.VISIBLE);
        binding.dropBtn.setRotation(0);
        binding.numberOfVariants.setVisibility(View.GONE);


        //show and gone variant group
        showAndHideVariants(binding, product);
        inflateVariants(product, binding,position);
        eventHandlers(binding, product,position);
        checkProducts(binding, product);

    }

    private void eventHandlers(ItemVbProductBinding binding, Product product, int position) {
        binding.incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the number of variants and apply if variants are greater than one
                if (product.variants.size() > 1)
                    showDialog(product, position);

                    //Steps for quantity of single variant
                else {
                    int qty = Integer.parseInt(binding.qty.getText().toString()) + 1;
                    cart.add(product, product.variants.get(0),qty);
                    listener.onCartUpdated(position);
                }
            }
        });

        binding.decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product.variants.size() > 1){
                    showDialog(product,position);
                }

                else{
                    int qty = Integer.parseInt(binding.qty.getText().toString()) - 1;
                    cart.add(product, product.variants.get(0),qty);
                    listener.onCartUpdated(position);
                }
            }
        });

    }



    private void inflateVariants(Product product, ItemVbProductBinding binding,int position) {
        for (Variant variants : product.variants ){
            ItemVariantBinding b  = ItemVariantBinding.inflate(((MainActivity)context).getLayoutInflater());
            b.chipVariantsName.setText(String.valueOf("₹" + variants.price + "-" +variants.name));
            binding.variantChips.addView(binding.getRoot());
        }
        if(product.variants.size() >0){
           showDialog(product,position);
        }
        else{
            binding.dropBtn.setVisibility(View.GONE);
            binding.numberOfVariants.setText("₹" + product.variants.get(0).price);
            binding.productName.setText(product.name + " " + product.variants.get(0).name);
        }

    }

    private void showAndHideVariants(ItemVbProductBinding binding, Product product) {
        binding.dropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.variantChips.getVisibility() == View.GONE) {
                    binding.variantChips.setVisibility(View.VISIBLE);
                    binding.dropBtn.setRotation(180);
                }
                else {
                    binding.variantChips.setVisibility(View.GONE);
                    binding.dropBtn.setRotation(0);
                }
            }
        });
    }

    private void checkProducts(ItemVbProductBinding binding, Product product) {
        int qty=0;

        for (Variant variants : product.variants) {
            //check qty present in cart
            if (cart.cartItems.containsKey(product.name + " " + variants.name)) {
                qty = cart.cartItems.get(product.name + " " + variants.name).quantity;
            }
        }
        //update views
        if (qty > 0) {
            binding.nonZeroQtyGrp.setVisibility(View.VISIBLE);
            binding.qty.setText(qty + "");
        } else {
            binding.nonZeroQtyGrp.setVisibility(View.GONE);
            binding.qty.setText(0 + "");
        }
    }

    private void showDialog(Product product, int position) {
        new VariantsQtyPickerDialog(context,cart,position,product, (AdapterCallbacksListener) listener).show();
    }
}
