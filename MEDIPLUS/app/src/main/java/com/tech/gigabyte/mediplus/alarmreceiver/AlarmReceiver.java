package com.tech.gigabyte.mediplus.alarmreceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.tech.gigabyte.mediplus.model.Alarm;
import com.tech.gigabyte.mediplus.R;

/**
 * Created by GIGABYTE on 4/7/2017.
 * Alarm Receiver .
 * A broadcast receiver (receiver) is an Android component which allows us to register for system or application events.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Uri sound = Uri.parse("android.resource://" + context.getPackageName() + "/raw/alarm_notification");

        //Notify the user of event that happen.(ALARM_NOTIFICATION)
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, i, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Take your medicine")
                .setContentText("MEDICINE")
                .setSound(sound)
                .setAutoCancel(true);

        notificationManager.notify(0, builder.build());

    }
}
