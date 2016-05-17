package com.stanford.sleepjournal;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.util.Log;

/**
 * Created by Santiago Gutierrez on 5/16/16.
 */
public class AlertnessAlarm extends BroadcastReceiver {

    public static final int ALERTNESS_NOTIFICATION = 0;
    public static final int INTENT_TYPE_MOOD = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        if(intent.getIntExtra("type",-1) != -1){
            Log.d(AlertnessAlarm.class.toString(), intent.getIntExtra("mood", -1) + " pressed");
        } else {
            showPushNotification(context);
            Log.d(AlertnessAlarm.class.toString(), "Alarm Toggled");

        }

        wl.release();
    }

    public void showPushNotification(Context context){
        Notification notification = getNotification(context);
        if (context != null && notification != null) {
            // Fire off the notification
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            int notificationTag = ALERTNESS_NOTIFICATION;
            String notificationId = String.valueOf(System.currentTimeMillis()); //make this depend on the hour

            try {
                nm.notify(notificationId, notificationTag, notification);
            } catch (SecurityException e) {
                // Some phones throw an exception for unapproved vibration
                notification.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
                nm.notify(notificationId, notificationTag, notification);
            }
        }
    }

    public Notification getNotification(Context context){
        NotificationCompat.Builder parseBuilder = new NotificationCompat.Builder(context);
        parseBuilder.setContentTitle("How alert are you?")
                .setContentText(Html.fromHtml("\"Are we human or are we dancer?\""))
                .setTicker(Html.fromHtml("How are you feeling?"))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Html.fromHtml("How alert are ya?")))
                .setSmallIcon(R.drawable.ic_save)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_mood_6))
                .addAction(R.drawable.ic_mood_1, "1", getPendingAction(context, 1))
//                .setContentIntent(pContentIntent)
//                .setDeleteIntent(pDeleteIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);
        parseBuilder.setSubText("Sleep and Dreams");
        parseBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        return parseBuilder.build();
    }

    public PendingIntent getPendingAction(Context context, int mood) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(context, AlertnessAlarmFeedBack.class);
        intent.putExtra("type", INTENT_TYPE_MOOD);
        intent.putExtra("mood", mood);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    //TODO change to inexact repeating
    public void setAlarm(Context context){
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlertnessAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        //am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000*60 , pi);//1000 * 60 * 10, pi); // Millisec * Second * Minute
        Log.d(AlertnessAlarm.class.toString(), "Alarm Set");
    }

    public void cancelAlarm(Context context){
        Intent intent = new Intent(context, AlertnessAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

}
