package com.stanford.sleepjournal.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.orm.SugarContext;
import com.orm.util.SugarConfig;
import com.stanford.sleepjournal.Application;
import com.stanford.sleepjournal.Constants;
import com.stanford.sleepjournal.MainActivity;
import com.stanford.sleepjournal.utils.AlertnessEntry;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Santiago Gutierrez on 5/16/16.
 */
public class AlertnessFeedBackProcessor extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();
        context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

        Calendar calendar = Calendar.getInstance();
        String hourMap = getHourMapping(calendar);
        String dateMap = MainActivity.getKeyFromCalendar(calendar);

        Log.d(AlertnessFeedBackProcessor.class.toString(), "Pressed: " + intent.getIntExtra("mood", -1));
        List<AlertnessEntry> finds = AlertnessEntry.find(AlertnessEntry.class, "date = ? AND hour = ?", dateMap, hourMap);
        AlertnessEntry hourObject;
        if(finds.size() == 0) {
            hourObject = new AlertnessEntry(dateMap, hourMap);
        } else {
            hourObject = finds.get(0);
        }

        hourObject.setMood(intent.getIntExtra("mood", 0));
        hourObject.save();

        Toast.makeText(context, "Successfully set mood for " + hourMap + " to " + intent.getIntExtra("mood", 0), Toast.LENGTH_SHORT).show();
    }

    public String getHourMapping(Calendar calendar){
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        return Constants.HOURS[hourOfDay/2];
    }


}
