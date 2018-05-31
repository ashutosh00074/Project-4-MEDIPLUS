package com.tech.gigabyte.mediplus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech.gigabyte.mediplus.R;

/**
 * Created by GIGABYTE on 4/7/2017.
 * Shopping Adapter for RecyclerView .
 */

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShopViewHolder> {
    private Context ctx;
    private String[] ArrayString;

    public ShoppingAdapter(Context ctx, String[] ArrayString) {
        this.ctx = ctx;
        this.ArrayString = ArrayString;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_row_url, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        holder.url_text.setText(ArrayString[position]);

    }

    @Override
    public int getItemCount() {
        return ArrayString.length;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    class ShopViewHolder extends RecyclerView.ViewHolder {
        TextView url_text;

        ShopViewHolder(View itemView) {
            super(itemView);
            url_text = (TextView) itemView.findViewById(R.id.tv_url);
        }
    }
}
