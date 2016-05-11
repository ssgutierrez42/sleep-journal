package com.stanford.sleepjournal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnDateChangeListener {

    private TextView mDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDateText = (TextView) findViewById(R.id.main_data_date);
        assert mDateText != null;

        CalendarView calendar = (CalendarView) findViewById(R.id.main_calendar_view);
        assert calendar != null;
        calendar.setOnDateChangeListener(this);

//        QuoteView quote = (QuoteView) findViewById(R.id.main_quote_view);
//        assert quote != null;
//        quote.setQuote("Your life is a reflection of how you sleep, and how you sleep is a reflection of your life.", "Dr. Rafael Pelayo");

        ImageView saveButton = (ImageView) findViewById(R.id.app_bar_save);
        assert saveButton != null;
        saveButton.setOnClickListener(this);

        loadDay(Calendar.getInstance());
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        loadDay(calendar);
//        Log.d(MainActivity.class.toString(), month + "/" + dayOfMonth + "/" + year);
    }

    private void loadDay(Calendar calendar){
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
        String date = month + " " + getDayOfMonthSuffix(calendar.get(Calendar.DAY_OF_MONTH)) + ", " + calendar.get(Calendar.YEAR);
        mDateText.setText(date);
    }

    private String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return n+"th";
        }
        switch (n % 10) {
            case 1:  return n+"st";
            case 2:  return n+"nd";
            case 3:  return n+"rd";
            default: return n+"th";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.app_bar_save:
                Toast.makeText(getApplicationContext(), "Save Data", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
