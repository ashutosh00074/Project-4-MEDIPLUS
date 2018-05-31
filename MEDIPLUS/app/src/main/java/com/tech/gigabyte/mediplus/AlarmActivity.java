package com.tech.gigabyte.mediplus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tech.gigabyte.mediplus.alarmreceiver.AlarmReceiver;
import com.tech.gigabyte.mediplus.database.DatabaseHelper;
import com.tech.gigabyte.mediplus.util.Utility;

import java.text.MessageFormat;
import java.util.Calendar;

/**
 * Created by GIGABYTE on 4/7/2017.
 * *************************************************************************************************
 * Setting Alarm For Drug/Medicine
 * *************************************************************************************************
 */

public class AlarmActivity extends AppCompatActivity {
    Toolbar toolbar;
    DatePicker Date;
    TimePicker Time;
    TextView tvInfo, tvDate, tvDis;
    EditText nam, msg;
    Button SetAlarm, SetDate, SetTime;
    CheckBox ed;
    DatabaseHelper helper;
    int ev, pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing views and other components .
        setContentView(R.layout.activity_alarm_);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        helper = DatabaseHelper.getInstance(AlarmActivity.this);
        SetDate = (Button) findViewById(R.id.al_date);
        SetTime = (Button) findViewById(R.id.al_time);
        tvInfo = (TextView) findViewById(R.id.info);
        tvDate = (TextView) findViewById(R.id.display_date);
        tvDis = (TextView) findViewById(R.id.display_time);
        Date = (DatePicker) findViewById(R.id.al_DatePicker);
        Time = (TimePicker) findViewById(R.id.al_TimePicker);
        nam = (EditText) findViewById(R.id.al_name);
        msg = (EditText) findViewById(R.id.al_message);
        ed = (CheckBox) findViewById(R.id.al_checkbox);


        //On SetDate Button Click Date picker will be Visible.
        SetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Time.setVisibility(View.GONE);
                Date.setVisibility(View.VISIBLE);
            }
        });

        //On SetTime Button Click Time picker will be Visible.
        SetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date.setVisibility(View.GONE);
                Time.setVisibility(View.VISIBLE);

            }
        });

        //Converting between a specific instant in time and a set of calendar fields
        Calendar now = Calendar.getInstance();

        Date.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);

        Time.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        Time.setCurrentMinute(now.get(Calendar.MINUTE));

        SetAlarm = (Button) findViewById(R.id.btn_setAlarm);
        SetAlarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Calendar current = Calendar.getInstance();

                Calendar cal = Calendar.getInstance();
                cal.set(Date.getYear(),
                        Date.getMonth(),
                        Date.getDayOfMonth(),
                        Time.getCurrentHour(),
                        Time.getCurrentMinute(),
                        0);

                //A mutable sequence of characters. This class provides an API compatible with StringBuffer
                StringBuilder buffer = new StringBuilder();
                String day_final = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
                String month_final = String.valueOf(cal.get(Calendar.MONTH) + 1);
                String year_final = String.valueOf(cal.get(Calendar.YEAR));
                buffer.append(day_final).append("/");
                buffer.append(month_final).append("/");
                buffer.append(year_final);
                tvDate.setText(buffer.toString());

                String hour_final = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
                String minute_final = String.valueOf(cal.get(Calendar.MINUTE));
                String buffer_time = (hour_final + ":") +
                        minute_final;
                tvDis.setText(buffer_time);


                if (cal.compareTo(current) <= 0) {
                    //The set Date-Time if already passed
                    Toast.makeText(getApplicationContext(),
                            "Invalid Date/Time",
                            Toast.LENGTH_LONG).show();
                } else {
                    setAlarm(cal);
                }

            }
        });
    }

    //Setting Alarm
    private void setAlarm(Calendar targetCal) {
        pi = Utility.RanNum();
        String al_name = nam.getText().toString();
        String al_msg = msg.getText().toString();
        String al_date = tvDate.getText().toString();
        String al_time = tvDis.getText().toString();


        if (ed.isChecked()) {
            ev = 0;
        } else {
            ev = 1;
        }
        if (ed.isChecked()) {

            Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), pi, intent, 0);
            AlarmManager alarmManager_everyday = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager_everyday.setRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        } else {

            Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), pi, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
        }
        //Concatenated messages in a language-neutral way
        tvInfo.setText(MessageFormat.format("", targetCal.getTime()));
        String pinum = String.valueOf(pi);
        String fin = String.valueOf(ev);
        long insres = helper.al_table(al_name, al_msg, al_date, al_time, pinum, fin);
        if (insres != -1) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    //Initializing the contents of the Activity's defined options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_alarm, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //ON Menu Item Selected .
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.menu_alarm_save:
                if (nam.length() != 0 && msg.length() != 0 && tvDate.length() != 0 && tvDis.length() != 0) {
                    Intent AlarmList = new Intent(AlarmActivity.this, Alarm_Information.class);
                    startActivity(AlarmList);
                } else {
                    Toast.makeText(this, "Please enter the details before saving", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.menu_home_alarm:
                Intent home = new Intent(AlarmActivity.this, MainActivity.class);
                startActivity(home);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
