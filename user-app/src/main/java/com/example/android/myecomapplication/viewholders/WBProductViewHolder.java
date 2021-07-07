package com.example.android.myecomapplication.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myecomapplication.databinding.ItemWbProductBinding;

public class WBProductViewHolder extends RecyclerView.ViewHolder {
    public ItemWbProductBinding b;
    //view holder for weight based products
    public WBProductViewHolder(@NonNull ItemWbProductBinding b) {
        super(b.getRoot());
        this.b = b;
    }
}
