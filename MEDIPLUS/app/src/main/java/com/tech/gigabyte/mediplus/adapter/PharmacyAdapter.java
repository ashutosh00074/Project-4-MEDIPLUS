package com.tech.gigabyte.mediplus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech.gigabyte.mediplus.model.PharmacyModel;
import com.tech.gigabyte.mediplus.R;

import java.util.ArrayList;

/**
 * Created by GIGABYTE on 4/7/2017.
 * Pharmacy Adapter for RecyclerView
 */

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder> {
    private Context context;
    private ArrayList<PharmacyModel> PharmacyList;
    private ClickInterface Click;

    public interface ClickInterface {

        void OnSingleClick(int position);

        void OnLongClick(int position);
    }

    private void delete(int position) {
        PharmacyList.remove(position);
        notifyItemRemoved(position);
    }

    public void del_all() {
        PharmacyList.clear();
        notifyDataSetChanged();
    }

    public void setClickInterface(ClickInterface clickInterface) {
        this.Click = clickInterface;
    }

    public PharmacyAdapter(Context context, ArrayList<PharmacyModel> PharmacyList) {
        this.context = context;
        this.PharmacyList = PharmacyList;
    }

    @Override
    //When RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    public PharmacyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext().getApplicationContext()).inflate(R.layout.eacg_row_pharmacy, parent, false);
        return new PharmacyViewHolder(view, context);
    }

    @Override
    //Called by RecyclerView to display the data at the specified position.
    public void onBindViewHolder(PharmacyViewHolder holder, int position) {

        holder.p_name.setText(PharmacyList.get(position).getP_name());
        holder.p_number.setText(PharmacyList.get(position).getP_number());
        holder.p_address.setText(PharmacyList.get(position).getP_address());
        holder.p_id.setText(String.valueOf(PharmacyList.get(position).getP_id()));
    }

    @Override
    public int getItemCount() {
        return PharmacyList.size();
    }

    class PharmacyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView p_name, p_number, p_address, p_id;
        Context context;

        PharmacyViewHolder(View itemView, Context c) {
            super(itemView);
            this.context = c;
            p_name = (TextView) itemView.findViewById(R.id.p_name);
            p_number = (TextView) itemView.findViewById(R.id.p_number);
            p_address = (TextView) itemView.findViewById(R.id.p_address);
            p_id = (TextView) itemView.findViewById(R.id.p_id);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            Click.OnLongClick(getAdapterPosition());
            delete(getAdapterPosition());
            return true;
        }


        @Override
        public void onClick(View v) {
            Click.OnSingleClick(getAdapterPosition());

        }
    }
}
