package com.stanford.sleepjournal.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by Santiago Gutierrez on 5/16/16.
 */
public class AlertnessFeedBackProcessor extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(AlertnessFeedBackProcessor.class.toString(), "Pressed: " + intent.getIntExtra("mood", -1));

    }


}
