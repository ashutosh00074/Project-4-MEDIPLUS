package com.tech.gigabyte.mediplus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.tech.gigabyte.mediplus.adapter.AlarmAdapter;
import com.tech.gigabyte.mediplus.alarmreceiver.AlarmReceiver;
import com.tech.gigabyte.mediplus.database.DatabaseHelper;
import com.tech.gigabyte.mediplus.model.Alarm;

import java.util.ArrayList;

/**
 * Created by GIGABYTE on 4/7/2017.
 * *******************************************************************************************
 * Showing Saved Alarm/AlarmList to user
 * ********************************************************************************************
 */

public class Alarm_Information extends AppCompatActivity implements AlarmAdapter.LongClickInterface {
    RecyclerView rv;
    Toolbar toolbar;
    String name, pi_no;
    int pi_num;
    int alarm_id;

    DatabaseHelper databaseHelper;
    ArrayList<Alarm> al_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing views and other components .
        setContentView(R.layout.activity_alarm_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
        rv = (RecyclerView) findViewById(R.id.rv_AlarmInfo);
        databaseHelper = DatabaseHelper.getInstance(this);
        al_list = new ArrayList<>();
        al_list = databaseHelper.AlarmInfo();
        rv.setLayoutManager(new LinearLayoutManager(this));
        AlarmAdapter al_adapter = new AlarmAdapter(this, al_list);
        rv.setAdapter(al_adapter);
        al_adapter.setLongClickInterface(this);
    }

    @Override
    //Initializing the contents of the Activity's defined options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_alarm_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //On LongClick Showing AlertDialog for deleting particular Alarm .
    //AlertDialog
    public void OnLongClicked(final int position) {
        name = al_list.get(position).getAl_name();
        pi_no = al_list.get(position).getAl_pending_no();
        alarm_id = al_list.get(position).getAl_id();
        pi_num = Integer.parseInt(pi_no);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete this alarm?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Alarm_Information.this, "It's ok", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelAlarm(pi_num, name, alarm_id, position);
                Intent alarm = new Intent(Alarm_Information.this, MainActivity.class);
                startActivity(alarm);
            }
        });
        builder.create();
        builder.show();

    }

    public void cancelAlarm(int pi_no, String name, int alarm_id, int position) {
        long delres = databaseHelper.al_del(alarm_id);
        if (delres != -1) {
            Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), pi_no, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm " + name + " was cancelled", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Sorry!!!Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    //ON Menu Item Selected .
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)

        {
            case R.id.gotomainpage:
                Intent home = new Intent(Alarm_Information.this, MainActivity.class);
                startActivity(home);
                break;
            case R.id.menu_add_alarm:
                Intent al = new Intent(Alarm_Information.this, AlarmActivity.class);
                startActivity(al);
                break;
            case R.id.menu_shopping_alarm:
                Intent shopping = new Intent(Alarm_Information.this, ShoppingActivity.class);
                startActivity(shopping);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
