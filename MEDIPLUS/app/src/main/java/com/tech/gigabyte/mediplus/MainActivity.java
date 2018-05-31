package com.tech.gigabyte.mediplus;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tech.gigabyte.mediplus.adapter.Adapter;
import com.tech.gigabyte.mediplus.database.DatabaseHelper;
import com.tech.gigabyte.mediplus.diaglogbox.SearchDialog;
import com.tech.gigabyte.mediplus.model.MedicineData;

import java.util.ArrayList;

/**
 * Created by GIGABYTE on 4/7/2017.
 * **************************************************************************************************
 * IN This all saved drug data will be shown , on click on drug will show drug details
 * Application Main Activity.
 * *************************************************************************************************
 */

public class MainActivity extends AppCompatActivity implements Adapter.ClickInterface {
    Toolbar t_main;
    RecyclerView rv;
    ArrayList<MedicineData> list;
    DatabaseHelper helper;
    Adapter adapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing views and other components .
        setContentView(R.layout.activity_main);
        t_main = (Toolbar) findViewById(R.id.toolbar);

        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(t_main);
        //Fab Button to ADD DRUG DATA
        fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentfab = new Intent(MainActivity.this, AddDrugDetails.class);
                startActivity(intentfab);
            }
        });

        rv = (RecyclerView) findViewById(R.id.rv_main);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        helper = DatabaseHelper.getInstance(this);
        list = helper.getAllDrugNames();
        helper = DatabaseHelper.getInstance(this);
        adapter = new Adapter(MainActivity.this, list);
        rv.setAdapter(adapter);
        adapter.setClickInterface(this);
    }

    @Override
    //Initializing the contents of the Activity's defined options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    //ON Menu Item Selected .
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.alarm_mainpage:
                Intent alarm = new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(alarm);
                break;
            case R.id.search_for_medicines:
                SearchDialog searchDialog = new SearchDialog();
                searchDialog.show(getFragmentManager(), "SearchDialog");
                break;

            case R.id.menu_shop:
                Intent shop = new Intent(MainActivity.this, ShoppingActivity.class);
                startActivity(shop);
                break;
            case R.id.menu_pharmacy:
                Intent pharmacy = new Intent(MainActivity.this, PharmacyInfo.class);
                startActivity(pharmacy);
                break;
            case R.id.logout:
                String Lmsg = getIntent().getStringExtra("LoginActivity");
                if (Lmsg != null) {
                    Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                    Intent gologin = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(gologin);
                } else {
                    Intent gologin = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(gologin);
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    //ON List single click
    public void OnSingleClick(int position) {

        String m_name = list.get(position).getName();
        Intent get_data = new Intent(MainActivity.this, DrugActivity.class);
        get_data.putExtra("Name", list.get(position).getName());
        get_data.putExtra("Description", list.get(position).getDesc());
        get_data.putExtra("Price", list.get(position).getPrice());
        get_data.putExtra("Category", list.get(position).getCat());
        get_data.putExtra("Type", list.get(position).getType());
        get_data.putExtra("Instructions", list.get(position).getInst());
        get_data.putExtra("Id", list.get(position).getId());
        startActivity(get_data);
    }

    @Override
    // Deleting Drug data on LongClick
    public void OnLongClick(int position) {
        String m_name = list.get(position).getName();
        int d_id = list.get(position).getId();
        long del_res = helper.deletedrugfromTable(d_id);
        if (del_res != -1) {
            Snackbar snackbar = Snackbar
                    .make(rv, " Successfully Deleted ", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder exit = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        exit.setCancelable(false);
        exit.setIcon(R.mipmap.ic_launcher);
        exit.setTitle(" EXIT");
        exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                MainActivity.super.onBackPressed();
                MainActivity.this.finish();
                moveTaskToBack(true);
                System.exit(0);
            }
        });
        exit.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = exit.create();
        alert.show();
    }
}
