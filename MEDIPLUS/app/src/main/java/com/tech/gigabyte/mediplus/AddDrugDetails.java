package com.tech.gigabyte.mediplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.gigabyte.mediplus.database.DatabaseHelper;
import com.tech.gigabyte.mediplus.util.Utility;

import java.util.ArrayList;

/**
 * Created by GIGABYTE on 4/7/2017.
 * ***********************************************************************************************
 * Adding New Drug/Medicine Data ! Having - NAME,DESCRIPTION,PRICE,CATEGORY,TYPE and INSTRUCTION
 * Instruction added for batter understanding to user.
 * ***********************************************************************************************
 */

public class AddDrugDetails extends AppCompatActivity {
    Toolbar toolbar;
    RadioGroup rg_cat;
    RadioButton r_cat;
    EditText name, desc, price;
    Spinner s_type, s_inst;
    DatabaseHelper helper;
    ArrayList<String> StringList;
    String type_txt, inst_txt;
    boolean exists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing views and other components .
        setContentView(R.layout.activity_add_drugs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
        StringList = new ArrayList<>();
        exists = false;
        helper = DatabaseHelper.getInstance(AddDrugDetails.this);
        name = (EditText) findViewById(R.id.et_DrugName);
        desc = (EditText) findViewById(R.id.et_DrugDesc);
        price = (EditText) findViewById(R.id.et_DrugPrice);
        rg_cat = (RadioGroup) findViewById(R.id.r_cat);

        //A view that displays one child at a time and lets the user pick among them.
        s_type = (Spinner) findViewById(R.id.s_type);
        ArrayAdapter s_adapt = ArrayAdapter.createFromResource(AddDrugDetails.this, R.array.type, android.R.layout.simple_spinner_item);
        s_type.setAdapter(s_adapt);

        //Medicine type selection
        s_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView s_itm_selected = (TextView) view;
                type_txt = s_itm_selected.getText().toString();
            }

            @Override
            //On Nothing select from TYPE spinner
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner Instruction Selection .
        s_inst = (Spinner) findViewById(R.id.s_inst);
        ArrayAdapter spinner_instructions_adapter = ArrayAdapter.createFromResource(AddDrugDetails.this, R.array.instruction, android.R.layout.simple_spinner_item);
        s_inst.setAdapter(spinner_instructions_adapter);
        s_inst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView instruction = (TextView) view;
                inst_txt = instruction.getText().toString();
            }

            @Override
            //On Nothing selected
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                String name = this.name.getText().toString();
                String desc = this.desc.getText().toString();
                String price = this.price.getText().toString();
                int selectedcatid = rg_cat.getCheckedRadioButtonId();
                r_cat = (RadioButton) findViewById(selectedcatid);
                String cat = r_cat.getText().toString();
                validate(name, desc, price, cat, type_txt, inst_txt);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //Validating Fields.
    public void validate(String name, String desc, String price, String cat, String type, String inst) {
        boolean isNUM = Utility.isNum(price);
        if (!name.isEmpty()) {
            if (!desc.isEmpty() || desc.length() > 5) {
                if (!price.isEmpty() && isNUM) {


                    boolean check = checkDuplication(name);
                    if (!check) {
                        getValue(name, desc, price, cat, type, inst);
                    } else {
                        Toast.makeText(getApplicationContext(), "Could not add as data already exists", Toast.LENGTH_LONG).show();
                        this.name.setText("");
                        this.desc.setText("");
                        this.price.setText("");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Enter valid Price", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Description should be minimum 5 caracters", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Enter Drug name", Toast.LENGTH_LONG).show();
        }

    }

    //Getting Values
    public void getValue(String name, String desc, String price, String cat, String type, String inst) {

        long result = helper.insert(name, desc, price, cat, type, inst);
        if (result != -1) {
            int receive_id = helper.DB(name);
            Toast.makeText(this, "Detail Saved Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(AddDrugDetails.this, DrugActivity.class);

            //Add extended data to the intent.
            i.putExtra("Name", name);
            i.putExtra("Description", desc);
            i.putExtra("Price", price);
            i.putExtra("Category", cat);
            i.putExtra("Id", receive_id);
            i.putExtra("Type", type);
            i.putExtra("Instructions", inst);
            startActivity(i);
        } else {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
        }

    }

    //Checking Duplicity
    public boolean checkDuplication(String name) {
        StringList = helper.search();
        for (int i = 0; i < StringList.size(); i++) {
            if (name.equalsIgnoreCase(StringList.get(i))) {
                exists = true;
                break;
            }
            exists = false;
        }
        return exists;
    }

}
