package com.tech.gigabyte.mediplus;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tech.gigabyte.mediplus.adapter.PharmacyAdapter;
import com.tech.gigabyte.mediplus.database.DatabaseHelper;
import com.tech.gigabyte.mediplus.diaglogbox.PharmacyDialog;
import com.tech.gigabyte.mediplus.model.PharmacyModel;

import java.util.ArrayList;

/**
 * Created by GIGABYTE on 4/7/2017.
 **************************************************************************************************
 *  To Store Name,Contact,Address Of Pharmacy so that user can communicate  easily
 **************************************************************************************************
 */

public class PharmacyInfo extends AppCompatActivity implements PharmacyAdapter.ClickInterface {
    FloatingActionButton fab_pharma;
    RecyclerView recyclerView;
    Toolbar toolbar;
    DatabaseHelper helper;
    String number;
    ArrayList<PharmacyModel> ArrayList;
    PharmacyAdapter pharmacy_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Imposing View and other components.
        setContentView(R.layout.activity_pharmacy);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab_pharma = (FloatingActionButton) findViewById(R.id.fab_pharmacy);
        fab_pharma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PharmacyDialog pharmacy_dialog = new PharmacyDialog();
                pharmacy_dialog.show(getFragmentManager(), "Pharma_FAB");
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.rv_pharmacy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList = new ArrayList<>();
        helper = DatabaseHelper.getInstance(this);
        ArrayList = helper.p_get();
        pharmacy_adapter = new PharmacyAdapter(PharmacyInfo.this, ArrayList);
        recyclerView.setAdapter(pharmacy_adapter);
        pharmacy_adapter.setClickInterface(this);
    }

    @Override
    //Initialize the contents of the defined options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pharma, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //When Menu Item selected .
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_pharma_gohome:
                Intent home = new Intent(PharmacyInfo.this, MainActivity.class);
                startActivity(home);
                break;

            case R.id.menu_pharma_shopping:
                Intent shop = new Intent(PharmacyInfo.this, ShoppingActivity.class);
                startActivity(shop);
                break;
            case R.id.delete_all:
                long del_all = helper.p_del_all();
                if (del_all != -1) {
                    Toast.makeText(this, "Successfully deleted all items", Toast.LENGTH_SHORT).show();
                    pharmacy_adapter.del_all();

                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    //SingleClick on arrayList Showing Alert Dialog .
    public void OnSingleClick(int position) {
        number = ArrayList.get(position).getP_number();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Do you want to make a call?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //On click on number will take to dial .
                Uri numURI = Uri.parse("tel:" + number);
                Intent dial = new Intent(Intent.ACTION_DIAL, numURI);
                startActivity(dial);
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    //Deleting data On LongClick .
    public void OnLongClick(int position) {
        String name = ArrayList.get(position).getP_name();
        int id = ArrayList.get(position).getP_id();

        long del = helper.deletepharmadetail(id);
        if (del != -1) {
            Toast.makeText(this, "Successfully deleted" + name, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

}
