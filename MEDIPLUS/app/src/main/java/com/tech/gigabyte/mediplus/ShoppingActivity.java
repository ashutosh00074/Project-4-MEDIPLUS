package com.tech.gigabyte.mediplus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tech.gigabyte.mediplus.adapter.ShoppingAdapter;


/** Created by GIGABYTE on 4/7/2017.
 * ****************************************************************************************
 * Shopping Activity ,so that user can easily search Medicines on google .
 * Some Useful and popular Website Provided to user .
 * ****************************************************************************************
 */

public class ShoppingActivity extends AppCompatActivity {
RecyclerView rv;
    Toolbar shop_toolbar;
    String[] List;
    EditText Search;
    Button WebSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_);

        //Toolbar for Shopping Activity
        shop_toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(shop_toolbar);

        Search = (EditText) findViewById(R.id.et_web);
        WebSearch =(Button)findViewById(R.id.btn_google);

        rv =(RecyclerView)findViewById(R.id.rv_shopping);

        List =getResources().getStringArray(R.array.website);

        rv.setLayoutManager(new LinearLayoutManager(this));
        ShoppingAdapter shoppingAdapter =new ShoppingAdapter(ShoppingActivity.this, List);
        rv.setAdapter(shoppingAdapter);

        WebSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Open Browser for google search
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String query= Search.getText().toString();
                        Uri uri=Uri.parse("http://www.google.com/search?q="+query);
                        Intent i=new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(i);
                    }
                });
                thread.start();
            }
        });

    }


    @Override
    //When OptionsMenu Item Selected .
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_shopping,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.go_home:
                               Intent gohome=new Intent(ShoppingActivity.this,MainActivity.class);
                               startActivity(gohome);
                break;
            case R.id.menu_alarmlist:
                              Intent gotoalarmlist=new Intent(ShoppingActivity.this,Alarm_Information.class);
                              startActivity(gotoalarmlist);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
