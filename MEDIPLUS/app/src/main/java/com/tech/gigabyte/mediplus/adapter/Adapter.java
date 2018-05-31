package com.tech.gigabyte.mediplus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech.gigabyte.mediplus.model.MedicineData;
import com.tech.gigabyte.mediplus.R;

import java.util.ArrayList;

/**
 * Created by GIGABYTE on 4/7/2017.
 * Adapter for Recycler View.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context con;
    private ArrayList<MedicineData> arrayList;
    private ClickInterface Click;


    private void delDrug(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
    }

    public interface ClickInterface {
        void OnSingleClick(int position);

        void OnLongClick(int position);
    }

    public void setClickInterface(final ClickInterface clickinterface) {
        this.Click = clickinterface;
    }

    public Adapter(Context con, ArrayList<MedicineData> arrayList) {
        this.con = con;
        this.arrayList = arrayList;
    }

    @Override
    //A ViewGroup is a special view that can contain other views (called children.)
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_row_main, parent, false);
        return new ViewHolder(view, con);
    }

    @Override
    //Called by RecyclerView to display the data at the specified position.
    public void onBindViewHolder(ViewHolder holder, int position) {
        MedicineData mData = arrayList.get(position);
        holder.d_name.setText(mData.getName());
        holder.d_id.setText(String.valueOf(mData.getId()));
    }

    @Override
    //Returns the total number of items in the data set held by the adapter.
    public int getItemCount() {
        return arrayList.size();
    }

    //place within the RecyclerView.
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        Context ctc;
        TextView d_name, d_id;

        ViewHolder(View itemView, Context con) {
            super(itemView);
            this.ctc = con;
            d_name = (TextView) itemView.findViewById(R.id.tv_DrugName);
            d_id = (TextView) itemView.findViewById(R.id.id_main);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        //On SingleClick
        public void onClick(View view) {
            Click.OnSingleClick(getAdapterPosition());
        }

        @Override
        //On LongClick
        public boolean onLongClick(View view) {
            Click.OnLongClick(getAdapterPosition());
            delDrug(getAdapterPosition());
            return true;
        }
    }
}
