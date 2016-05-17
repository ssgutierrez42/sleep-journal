package com.stanford.sleepjournal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Santiago Gutierrez on 5/16/16.
 */
public class NotificationService extends Service {

    AlertnessAlarm alarm = new AlertnessAlarm();
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        alarm.setAlarm(this);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

}
