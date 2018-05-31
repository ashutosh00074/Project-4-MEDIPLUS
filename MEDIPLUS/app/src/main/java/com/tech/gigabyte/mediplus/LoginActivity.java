package com.tech.gigabyte.mediplus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.gigabyte.mediplus.diaglogbox.RegisterDialog;

/**
 * Created by GIGABYTE on 4/7/2017.
 * ***************************************************************************************
 * A SIMPLE LoginActivity Screen Also have SKIP Option if user don't want to do this
 * LoginActivity Credentials are saved using SharedPreferences
 * A Beautiful Dialog For Register , DIM Define .
 * ****************************************************************************************
 */

public class LoginActivity extends AppCompatActivity {
    EditText UID, PASS;
    Button login_btn;
    TextView register;

    TextView skip;
    Button textregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing views and other components .
        setContentView(R.layout.activity_login);
        UID = (EditText) findViewById(R.id.et_UID);
        PASS = (EditText) findViewById(R.id.et_PASS);
        login_btn = (Button) findViewById(R.id.btn_login);

        //On LoginActivity Click Validating saved data using SharedPreferences
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Interface for accessing and modifying preference data returned by getSharedPreferences(String, int).
                SharedPreferences preferences = getSharedPreferences("USERLOGININFO", MODE_PRIVATE);
                String UID_pref = preferences.getString("USERNAME", "No data");
                String PASS_pref = preferences.getString("PASSWORD", "No data");


                if (UID.getText().toString().isEmpty() && PASS.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
                } else {
                    if (UID.getText().toString().equals(UID_pref) && PASS.getText().toString().equals(PASS_pref)) {
                        Toast.makeText(LoginActivity.this, "LoginActivity Successful", Toast.LENGTH_SHORT).show();
                        Intent gotomain = new Intent(LoginActivity.this, MainActivity.class);
                        gotomain.putExtra("LoginActivity", "LoginActivity");
                        startActivity(gotomain);

                    } else {
                        Toast.makeText(LoginActivity.this, "LoginActivity Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // Showing Dialog for Registration
        register = (Button) findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "register", Toast.LENGTH_SHORT).show();
                RegisterDialog register_dialog = new RegisterDialog();
                register_dialog.show(getFragmentManager(), "RegisterDialog");
            }
        });

        //Skipping LoginActivity Process , GOTO to MainActivity
        skip = (TextView) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skip = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(skip);
            }
        });
    }
}
