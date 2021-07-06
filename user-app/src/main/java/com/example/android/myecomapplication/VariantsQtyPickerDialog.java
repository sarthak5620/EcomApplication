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
    private final int selectedPosition;
    private LayoutInflater inflater;
    private AdapterCallbacksListener listener ;
    private HashMap<String, Integer> saveQuantity = new HashMap<>();
    private DialogVariantsQtyPickerBinding binding;
    private ItemVariantBinding b;


    public VariantsQtyPickerDialog(Context context, Cart cart, int position, Product product, AdapterCallbacksListener listener) {
        this.cart = cart;
        this.selectedPosition = position ;
        this.product = product;
        this.listener = listener;
        this.context = context;
    }

    public void show() {
        binding= DialogVariantsQtyPickerBinding.inflate(((MainActivity)context).getLayoutInflater());

        dialog=new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme)
                .setCancelable(false)
                .setView(binding.getRoot())
                .show();

        binding.nameOfProduct.setText(product.name);

        inflateVariants();
        buttonEventHandlers();
        preSelectedQty(b);

    }

    private void inflateVariants() {
       for (Variant variants : product.variants ){
           b = ItemVariantBinding.inflate(inflater);
           b.chipVariantsName.setText(String.valueOf("₹" + variants.price + "-" + variants.name));
           binding.variantList.addView(binding.getRoot());
       }

    }


    private void buttonEventHandlers() {
            b.decBtn.setOnClickListener(v -> {
                if (saveQuantity.containsKey(Variant.name)) {
                    saveQuantity.put(Variant.name, saveQuantity.get(Variant.name) - 1);
                }
                //check variant quantity size
                if (saveQuantity.get(Variant.name) == 0) {
                    b.initialQuantityRoot.setVisibility(View.GONE);
                }

                b.qty.setText(String.valueOf(saveQuantity.get(Variant.name) + ""));
            });

            b.incBtn.setOnClickListener(v -> {
                if (saveQuantity.containsKey(Variant.name)) {
                    saveQuantity.put(Variant.name, saveQuantity.get(Variant.name) + 1);
                }
                //check variant quantity size
                if (saveQuantity.get(Variant.name) != 0) {
                    b.initialQuantityRoot.setVisibility(View.VISIBLE);
                }

                b.qty.setText(String.valueOf(saveQuantity.get(Variant.name) + ""));
            });

            binding.removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!saveQuantity.isEmpty()){
                        cart.removeAllVBP(product);
                        listener.onCartUpdated(selectedPosition);
                    }
                    dialog.dismiss();
                }
            });

            binding.SaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!saveQuantity.isEmpty()){
                        for (Variant variant : product.variants){
                            if(saveQuantity.containsKey(Variant.name)){
                                cart.add(product,variant, Integer.parseInt(b.qty.getText().toString()));
                            }
                        }
                        listener.onCartUpdated(selectedPosition);
                    }
                    else{
                        Toast.makeText(context,"Please add something!",Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
    }


    private void preSelectedQty(ItemVariantBinding binding) {
        if(cart.cartItems.containsKey(product.name + " " + Variant.name)){
            saveQuantity.put(Variant.name, (int) cart.cartItems.get(product.name + " " + Variant.name).quantity);
            binding.initialQuantityRoot.setVisibility(View.VISIBLE);
            binding.qty.setText(String.valueOf(saveQuantity.get(Variant.name) + ""));
        }
    }



}
