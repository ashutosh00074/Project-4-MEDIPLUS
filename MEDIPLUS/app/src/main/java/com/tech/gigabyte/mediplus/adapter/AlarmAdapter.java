package com.tech.gigabyte.mediplus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech.gigabyte.mediplus.model.Alarm;
import com.tech.gigabyte.mediplus.R;

import java.util.ArrayList;

/**
 * Created by GIGABYTE on 4/7/2017.
 * AlarmAdapter for RecyclerView .
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmInfoViewHolder> {
    private Context context;
    private ArrayList<Alarm> infoList;
    private LongClickInterface LongClick;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public interface LongClickInterface {
        void OnLongClicked(int position);
    }

    public void setLongClickInterface(LongClickInterface longclickinterface) {
        this.LongClick = longclickinterface;
    }

    public AlarmAdapter(Context context, ArrayList<Alarm> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    @Override
    //place within the RecyclerView.
    public AlarmInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_row_alarm_info, parent, false);
        return new AlarmInfoViewHolder(view);
    }

    @Override
    //Called by RecyclerView to display the data at the specified position.
    public void onBindViewHolder(AlarmInfoViewHolder holder, int position) {
        Alarm alarm_information = infoList.get(position);
        holder.al_name.setText(alarm_information.getAl_name());
        holder.al_msg.setText(alarm_information.getAl_message());
        holder.al_date.setText(alarm_information.getAl_date());
        holder.al_time.setText(alarm_information.getAl_time());
        holder.al_pi_no.setText(alarm_information.getAl_pending_no());
        holder.al_id.setText(String.valueOf(alarm_information.getAl_id()));
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class AlarmInfoViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView al_name, al_msg, al_date, al_time, al_pi_no, al_id;

        //place within the RecyclerView.
        AlarmInfoViewHolder(View itemView) {
            super(itemView);
            al_name = (TextView) itemView.findViewById(R.id.al_name);
            al_msg = (TextView) itemView.findViewById(R.id.al_message);
            al_date = (TextView) itemView.findViewById(R.id.al_date);
            al_time = (TextView) itemView.findViewById(R.id.al_time);
            al_pi_no = (TextView) itemView.findViewById(R.id.info_pending_intent);
            al_id = (TextView) itemView.findViewById(R.id.al_id);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            LongClick.OnLongClicked(getAdapterPosition());
            return true;
        }
    }
}
