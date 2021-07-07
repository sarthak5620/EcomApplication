package com.example.android.myecomapplication;

import android.content.Context;
import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AlertDialog;

import com.example.android.models.Cart;
import com.example.android.models.Product;
import com.example.android.myecomapplication.databinding.DialogWeightPickerBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class WeightPickerDialog {
    private Context context;
    private Cart cart;
    private AlertDialog dialog;
    private Product product;
    private int position;
    private int minValueInKg;
    private int minValueInG;
    private int selectedPosition=0;
    private DialogWeightPickerBinding binding;
    private AdapterCallbacksListener listener;

    public WeightPickerDialog(Context context, Cart cart, int position, Product product, AdapterCallbacksListener listener) {
        this.context = context;
        this.cart = cart;
        this.position = position;
        this.product = product;
        this.listener = listener;
    }


    public void show() {
        binding=DialogWeightPickerBinding.inflate(((MainActivity)context).getLayoutInflater());

        dialog=new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme)
                .setCancelable(false)
                .setView(binding.getRoot())
                .show();

        binding.nameOfProduct.setText(product.name);

        minQty();

        buttonEventHandlers();

        preSelectedQty();

    }

    private void minQty() {
        String[] minValues=String.valueOf(product.minimumQuantity).split("\\.");

        minValueInKg =Integer.parseInt(minValues[0]);
        eventNumberPickerKg();

        String minQtyGram="0." + minValues[1];

        minValueInG =(int)(Float.parseFloat(minQtyGram)*1000);
        eventNumberPickerG();
    }

    private void eventNumberPickerG() {
        int numberOfValues = 20- (minValueInG /50);

        int pickerRange = minValueInG;
        String[] ValueToDisplay= new String[numberOfValues];

        ValueToDisplay[0]= minValueInG + "g";
        for(int i=1; i<numberOfValues;i++){

            ValueToDisplay[i] = (pickerRange + 50) + "gm";
            pickerRange += 50;
        }

        binding.gPicker.setDisplayedValues(null);
        binding.gPicker.setMinValue(0);
        binding.gPicker.setMaxValue(numberOfValues-1);
        binding.gPicker.setDisplayedValues(ValueToDisplay);
        binding.gPicker.setValue(selectedPosition);
    }

    private void eventNumberPickerKg() {
        int numberOfValues= 11 - minValueInKg;

        int pickerRange= minValueInKg;
        String[] ValueToDisplay = new String[numberOfValues];

        ValueToDisplay[0]= minValueInKg +"Kg";
        for(int i=1; i<numberOfValues; i++){

            ValueToDisplay[i] =(++pickerRange) + "Kg";
        }
        binding.KgPicker.setMinValue(0);
        binding.KgPicker.setMaxValue(ValueToDisplay.length-1);
        binding.KgPicker.setDisplayedValues(ValueToDisplay);

        binding.KgPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(picker.getValue()+ minValueInKg != minValueInKg){
                    if(minValueInG ==0){
                        return;
                    }
                    selectedPosition=((minValueInG /50+binding.gPicker.getValue())*50)/50;
                    minValueInG =0;
                    eventNumberPickerG();
                }
                else if(picker.getValue()+ minValueInKg == minValueInKg){
                    minValueInG =(int)((product.minimumQuantity- minValueInKg)*1000);

                    selectedPosition=((binding.gPicker.getValue()*50)- minValueInG)/50;
                    if(selectedPosition<0){
                        selectedPosition=0;
                    }
                    eventNumberPickerG();
                }
            }
        });
    }

    private void buttonEventHandlers() {
        binding.SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float qty=(minValueInKg + binding.KgPicker.getValue())
                        +((((minValueInG /50f)+binding.gPicker.getValue())*50)/1000f);

                if(cart.cartItems.containsKey(product.name) && (cart.cartItems.get(product.name).quantity==qty)){
                    dialog.dismiss();
                    return;
                }
                cart.add(product,qty);

                listener.onCartUpdated(position);
                dialog.dismiss();
            }
        });

        binding.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.cartItems.containsKey(product.name)){
                    cart.removeWBP(product);
                    listener.onCartUpdated(position);
                }
                dialog.dismiss();
            }

        });
    }

    private void preSelectedQty() {

        if(cart.cartItems.containsKey(product.name)){
            String[] minValues= String.valueOf(cart.cartItems.get(product.name).quantity).split("\\.");

            String minQtyG ="0."+ minValues[1];

            int gram=(int) (Float.parseFloat(minQtyG)*1000);

            binding.KgPicker.setValue(Integer.parseInt(minValues[0])- minValueInKg);

            if(Integer.parseInt(minValues[0])!= minValueInKg){
                if(minValueInG !=0){
                    minValueInG = 0;
                    eventNumberPickerG();
                }
            }
            binding.gPicker.setValue((gram- minValueInG)/50);
        }
    }

}
