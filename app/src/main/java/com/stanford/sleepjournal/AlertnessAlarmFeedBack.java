package com.stanford.sleepjournal;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.util.Log;

/**
 * Created by Santiago Gutierrez on 5/16/16.
 */
public class AlertnessAlarmFeedBack extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        Log.d(AlertnessAlarmFeedBack.class.toString(), "Wazzaaaaap");

        wl.release();
    }


}
