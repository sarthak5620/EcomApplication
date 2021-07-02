package com.example.android.myecomapplication.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myecomapplication.databinding.ItemVbProductBinding;

public class VBProductViewHolder extends RecyclerView.ViewHolder {
    public ItemVbProductBinding b;

    public VBProductViewHolder(@NonNull ItemVbProductBinding b) {
        super(b.getRoot());
        this.b = b;
    }
}
