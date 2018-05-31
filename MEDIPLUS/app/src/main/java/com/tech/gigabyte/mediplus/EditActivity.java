package com.tech.gigabyte.mediplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.tech.gigabyte.mediplus.database.DatabaseHelper;
import com.tech.gigabyte.mediplus.util.Utility;

/**
 * Created by GIGABYTE on 4/7/2017.
 * *************************************************************************************************
 * EDIT Activity for editing Drug Details
 * *************************************************************************************************
 */

public class EditActivity extends AppCompatActivity {
    Toolbar edit_toolbar;
    EditText e_name, e_desc, e_price;
    RadioGroup e_cat;
    RadioButton r_generic, r_specific;
    String getName, getDesc, getPrice, getCat, getType, getInstr;
    String up_name, up_desc, up_price, up_cat, up_type, up_inst;
    int getId;
    Spinner s_type, s_inst;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing views and other components .
        setContentView(R.layout.activity_edit_data);
        edit_toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(edit_toolbar);
        helper = DatabaseHelper.getInstance(this);
        e_name = (EditText) findViewById(R.id.et_name);
        e_desc = (EditText) findViewById(R.id.et_desc);
        e_price = (EditText) findViewById(R.id.et_price);
        e_cat = (RadioGroup) findViewById(R.id.rg_cat);
        r_generic = (RadioButton) findViewById(R.id.r_generic);
        r_specific = (RadioButton) findViewById(R.id.r_specific);

        s_type = (Spinner) findViewById(R.id.s_type);
        ArrayAdapter type = ArrayAdapter.createFromResource(EditActivity.this,
                R.array.type, android.R.layout.simple_spinner_item);
        s_type.setAdapter(type);

        s_inst = (Spinner) findViewById(R.id.s_info);
        ArrayAdapter spinner_instructions = ArrayAdapter.createFromResource(EditActivity.this,
                R.array.instruction, android.R.layout.simple_spinner_item);
        s_inst.setAdapter(spinner_instructions);
        init();
    }

    @Override
    //Initializing the contents of the Activity's defined options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_drug, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //ON Menu Item Selected .
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.save_add_drug:
                validate(e_name.getText().toString(), e_desc.getText().toString(), e_price.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init() {
        //Return the intent that started this activity.
        //Retrieve extended data from the intent.
        getName = getIntent().getStringExtra("Name");
        getDesc = getIntent().getStringExtra("Description");
        getPrice = getIntent().getStringExtra("Price");
        getCat = getIntent().getStringExtra("Category");
        getType = getIntent().getStringExtra("Type");
        getInstr = getIntent().getStringExtra("Instructions");
        getId = getIntent().getIntExtra("Id", 0);
        e_name.setText(getName);
        e_desc.setText(getDesc);
        e_price.setText(getPrice);

        if (getCat.equalsIgnoreCase("Generic")) {
            r_generic.setChecked(true);
        }
        if (getCat.equalsIgnoreCase("Specific")) {
            r_specific.setChecked(true);
        }

        //to set value to spinner TYPE
        for (int i = 0; i < s_type.getCount(); i++) {
            if (getType.equals(s_type.getItemAtPosition(i))) {
                s_type.setSelection(i);
            }
        }

        for (int j = 0; j < s_inst.getCount(); j++) {
            if (getInstr.equals(s_inst.getItemAtPosition(j))) {
                s_inst.setSelection(j);
            }
        }
    }

    //Getting Updated Values.
    public void get_up_values() {
        up_name = e_name.getText().toString();
        up_desc = e_desc.getText().toString();
        up_price = e_price.getText().toString();
        up_cat = ((RadioButton) findViewById(e_cat.getCheckedRadioButtonId())).getText().toString();
        up_type = s_type.getSelectedItem().toString();
        up_inst = s_inst.getSelectedItem().toString();


        long up_res = helper.update(up_name, up_desc, up_price, up_cat, up_type, up_inst, getId);
        if (up_res > -1) {
            Toast.makeText(this, "Successfully updated", Toast.LENGTH_LONG).show();
            Intent gotoMain = new Intent(EditActivity.this, MainActivity.class);
            startActivity(gotoMain);
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    //Validation for fields
    public void validate(String d_name, String d_desc, String d_price) {
        boolean isNUM = Utility.isNum(d_price);
        if (!d_name.isEmpty()) {
            if (!d_desc.isEmpty() || d_desc.length() > 5) {
                if (!d_price.isEmpty() && isNUM) {
                    get_up_values();

                } else {
                    Toast.makeText(this, "Enter valid price", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Enter minimum 5 characters", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Enter valid drug name", Toast.LENGTH_SHORT).show();
        }
    }

}


