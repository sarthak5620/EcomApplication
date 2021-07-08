package com.example.android.myecomapplication;

import android.content.Context;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.android.models.Cart;
import com.example.android.models.Product;
import com.example.android.models.Variant;
import com.example.android.myecomapplication.databinding.ChipVariantBinding;
import com.example.android.myecomapplication.databinding.DialogVariantsQtyPickerBinding;
import com.example.android.myecomapplication.databinding.ItemVariantBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.HashMap;
import java.util.List;

public class VariantsQtyPickerDialog {
    private Context context;
    private Cart cart;
    private AlertDialog dialog;
    private Product product;
    private int position;
    private AdapterCallbacksListener listener ;
    private HashMap<String, Integer> saveQuantity = new HashMap<>();
    private DialogVariantsQtyPickerBinding b;


    public VariantsQtyPickerDialog(Context context, Cart cart, int position, Product product, AdapterCallbacksListener listener) {
        this.cart = cart;
        this.position = position ;
        this.product = product;
        this.listener = listener;
        this.context = context;
    }

    public void show() {
        b= DialogVariantsQtyPickerBinding.inflate(((MainActivity)context).getLayoutInflater());

        dialog=new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme)
                .setCancelable(false)
                .setView(b.getRoot())
                .show();

        b.nameOfProduct.setText(product.name);

        inflateVariants();
        buttonEventHandlers();
       // preSelectedQty();

    }

    private void inflateVariants() {
       for (Variant variants : product.variants ){
           ItemVariantBinding binding = ItemVariantBinding.inflate(((MainActivity) context).getLayoutInflater());
           binding.chipVariantsName.setText("Rs." + variants.price + " - " + variants.name);
           b.variantList.addView(binding.getRoot());
           preSelectedQty(binding, variants.name);

           //add quantity
           addQuantity(binding, variants.name);

           //dec quantity
           decQuantity(binding, variants.name);
       }
    }

    private void decQuantity(ItemVariantBinding binding, String name) {
        binding.decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save qty of variants
                //check variant present or not
                if (saveQuantity.containsKey(name)) {
                    saveQuantity.put(name, saveQuantity.get(name) + 1);

                }
                if(saveQuantity.get(name) == 0){
                    binding.initialQuantityRoot.setVisibility(View.GONE);
                }
                //update views
                binding.qty.setText(saveQuantity.get(name) + "");

            }
        });
    }

    private void addQuantity(ItemVariantBinding binding, String name) {
        binding.incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save qty of variants
                //check variant present or not
                if (saveQuantity.containsKey(name)) {
                    saveQuantity.put(name, saveQuantity.get(name) + 1);

                } else {
                    saveQuantity.put(name, 1);
                }
                //update views
                binding.qty.setText(saveQuantity.get(name) + "");
                binding.initialQuantityRoot.setVisibility(View.VISIBLE);
            }
        });
    }


    private void buttonEventHandlers() {
            b.removeButton.setOnClickListener(v -> {
                if (!saveQuantity.isEmpty()) {
                    cart.removeAllVBP(product);
                    //update views
                    listener.onCartUpdated(position);
                }
                dialog.dismiss();
            });

            b.SaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!saveQuantity.isEmpty()){
                        for (Variant variant : product.variants){
                            if(saveQuantity.containsKey(variant.name)){
                                cart.add(product,variant,saveQuantity.get(variant.name));
                            }
                        }
                        listener.onCartUpdated(position);
                    }
                    else{
                        Toast.makeText(context,"Please add something!",Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
    }


    private void preSelectedQty(ItemVariantBinding binding, String name) {
        if(cart.cartItems.containsKey(product.name + " " + name)){
            saveQuantity.put(product.name, cart.cartItems.get(product.name + " " + name).quantity);
            binding.initialQuantityRoot.setVisibility(View.VISIBLE);
            binding.qty.setText(String.valueOf(saveQuantity.get(name) + ""));
        }
    }



}
