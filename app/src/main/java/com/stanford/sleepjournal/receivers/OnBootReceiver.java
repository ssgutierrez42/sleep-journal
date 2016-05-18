package com.stanford.sleepjournal.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.stanford.sleepjournal.receivers.NotificationService;

/**
 * Created by Santiago Gutierrez on 5/16/16.
 */
public class OnBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent startServiceIntent = new Intent(context, NotificationService.class);
            context.startService(startServiceIntent);
        }
    }
}
