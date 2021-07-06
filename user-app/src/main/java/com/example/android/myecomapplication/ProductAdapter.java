package com.example.android.myecomapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.models.Cart;
import com.example.android.models.Product;
import com.example.android.models.ProductType;
import com.example.android.myecomapplication.databinding.ItemVbProductBinding;
import com.example.android.myecomapplication.databinding.ItemWbProductBinding;
import com.example.android.myecomapplication.viewholders.VBProductViewHolder;
import com.example.android.myecomapplication.viewholders.WBProductViewHolder;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private Cart cart;
    public WBProductBinder wbProductBinder;
    public VBProductBinder vbProductBinder;
    private List<Product> products;

    public ProductAdapter(Context context, Cart cart, List<Product> products) {
        this.context = context;
        this.cart = cart;
        this.products = products;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ProductType.TYPE_WBP){
            ItemWbProductBinding binding = ItemWbProductBinding.inflate(LayoutInflater.from(context),parent,false);
            return new WBProductViewHolder(binding);
        }
        else {
            ItemVbProductBinding binding = ItemVbProductBinding.inflate(LayoutInflater.from(context),parent,false);
            return new VBProductViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WBProductViewHolder){
            wbProductBinder.Bind(products.get(position), ((WBProductViewHolder)holder).binding,position);
            return;
        }
        vbProductBinder.Bind(products.get(position), ((VBProductViewHolder)holder).binding,position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}