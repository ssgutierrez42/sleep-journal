package com.stanford.sleepjournal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Santiago Gutierrez on 5/16/16.
 */
public class StartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent startServiceIntent = new Intent(context, NotificationService.class);
            context.startService(startServiceIntent);
        }
    }
}
