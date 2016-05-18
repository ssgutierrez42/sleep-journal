package com.stanford.sleepjournal;

import android.content.Intent;

import com.orm.SugarContext;
import com.stanford.sleepjournal.receivers.NotificationService;
import com.stanford.sleepjournal.utils.Day;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class Application extends android.app.Application {

    private static Day mSelected;

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());

        Intent startServiceIntent = new Intent(getApplicationContext(), NotificationService.class);
        getApplicationContext().startService(startServiceIntent);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    public static Day getSelectedDay() {
        return mSelected;
    }

    public static void setSelectedDay(Day mSelected) {
        Application.mSelected = mSelected;
    }
}
