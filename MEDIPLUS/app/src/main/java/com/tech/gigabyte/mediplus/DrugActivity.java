package com.tech.gigabyte.mediplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by GIGABYTE on 4/7/2017.
 * **********************************************************************
 * Saved DRUG Data/Medicine Data ,
 * OptionMenu - HOME, SHARE and Edit.
 * Sharing Saved Drug Details , Editing Saved Data .
 * **********************************************************************
 */

public class DrugActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView e_name, e_desc, e_price, e_cat, e_id, e_type, e_inst;
    String in_name, in_desc, in_price, in_cat, in_type, in_inst;
    int in_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing views and other components .
        setContentView(R.layout.activity_drug_data);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        e_name = (TextView) findViewById(R.id.tv_disp_name);
        e_desc = (TextView) findViewById(R.id.tv_disp_desc);
        e_price = (TextView) findViewById(R.id.tv_disp_price);
        e_cat = (TextView) findViewById(R.id.tv_disp_cat);
        e_id = (TextView) findViewById(R.id.id_drug_data);
        e_type = (TextView) findViewById(R.id.tv_disp_type);
        e_inst = (TextView) findViewById(R.id.tv_disp_inst);
        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        in_name = getIntent().getStringExtra("Name");
        e_name.setText(in_name);
        in_desc = getIntent().getStringExtra("Description");
        e_desc.setText(in_desc);
        in_price = getIntent().getStringExtra("Price");
        e_price.setText(in_price);
        in_cat = getIntent().getStringExtra("Category");
        if (in_cat.equalsIgnoreCase("Generic")) {
            e_cat.setText("Generic");
        } else {
            e_cat.setText("Specific");
        }
        in_id = getIntent().getIntExtra("Id", 0);
        e_id.setText(String.valueOf(in_id));

        in_type = getIntent().getStringExtra("Type");
        e_type.setText(in_type);
        in_inst = getIntent().getStringExtra("Instructions");
        e_inst.setText(in_inst);
    }

    @Override
    //Initializing the contents of the Activity's defined options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_drug_data, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //ON Menu Item Selected .
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_home:
                Intent in = new Intent(DrugActivity.this, MainActivity.class);
                startActivity(in);
                break;

            case R.id.menu_share:
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBodyText = "Medicine Name : " + in_name + "\tPurpose : " + in_desc + "," + in_price + "/-rs, " + in_cat + "" + "-" + in_type + "Take" + in_inst;
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Medicine Information");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(intent, "Share via"));
                break;

            case R.id.menu_edit:
                edit(in_name, in_desc, in_price, in_cat, in_id, in_type, in_inst);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //when Clicking on Edit , Putting data
    public void edit(String name, String desc, String price, String category, int id, String type, String instructions) {
        Intent edit = new Intent(DrugActivity.this, EditActivity.class);
        edit.putExtra("Name", name);
        edit.putExtra("Description", desc);
        edit.putExtra("Price", price);
        edit.putExtra("Category", category);
        edit.putExtra("Id", id);
        edit.putExtra("Type", type);
        edit.putExtra("Instructions", instructions);
        startActivity(edit);
    }


}
