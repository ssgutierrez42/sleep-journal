package com.stanford.sleepjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stanford.sleepjournal.dialogs.DataEditorDialog;
import com.stanford.sleepjournal.dialogs.SaveDialog;
import com.stanford.sleepjournal.fragments.FragmentPageAdapter;
import com.stanford.sleepjournal.utils.Day;
import com.stanford.sleepjournal.utils.ExcelManager;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.stanford.sleepjournal.dialogs.DialogEditorAdapter.leftPad;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnDateChangeListener {

    private TextView mDateText;
    private ViewPager mViewPager;
    private Calendar mLastSelectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.main_data_container);
        mDateText = (TextView) findViewById(R.id.main_data_date);
        assert mDateText != null;
        assert mViewPager != null;
        mViewPager.setOffscreenPageLimit(3);

        CalendarView calendar = (CalendarView) findViewById(R.id.main_calendar_view);
        assert calendar != null;
        calendar.setOnDateChangeListener(this);

        ImageView saveButton = (ImageView) findViewById(R.id.app_bar_save);
        assert saveButton != null;
        saveButton.setOnClickListener(this);

        ImageView settings = (ImageView) findViewById(R.id.app_bar_settings);
        assert settings != null;
        settings.setOnClickListener(this);

        ImageButton editButton = (ImageButton) findViewById(R.id.main_data_edit);
        assert editButton != null;
        editButton.setOnClickListener(this);

        LinearLayout mainDataEdit = (LinearLayout) findViewById(R.id.main_data_tap_edit);
        assert mainDataEdit != null;
        mainDataEdit.setOnClickListener(this);

        loadDay(Calendar.getInstance());
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        loadDay(calendar);
    }

    public static String getKeyFromCalendar(Calendar calendar){
        return leftPad(calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR);
    }

    private void loadDay(Calendar calendar) {
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
        String date = month + " " + getDayOfMonthSuffix(calendar.get(Calendar.DAY_OF_MONTH)) + ", " + calendar.get(Calendar.YEAR);
        mDateText.setText(date);

        String dateKey = getKeyFromCalendar(calendar);
        Log.d(MainActivity.class.toString(), dateKey);
        List<Day> finds = Day.find(Day.class, "date = ?", dateKey);
        Day day;
        if (finds.isEmpty()) {
            day = new Day(dateKey);
            day.setFormatDate(date);
            day.setDayOfWeek(Day.DAYS[calendar.get(Calendar.DAY_OF_WEEK)-1]);
            day.save();
        } else {
            day = finds.get(0);
        }

        Application.setSelectedDay(day);

        TextView asleep = (TextView) findViewById(R.id.main_data_asleep);
        TextView awake = (TextView) findViewById(R.id.main_data_awake);
        TextView slept_for = (TextView) findViewById(R.id.main_data_slept_for);
        TextView napped_for = (TextView) findViewById(R.id.main_data_napped_for);
        TextView groggy_for = (TextView) findViewById(R.id.main_data_groggy_for);

        assert asleep != null;
        assert awake != null;
        assert slept_for != null;
        assert napped_for != null;
        assert groggy_for != null;

        asleep.setText(getValue(day.getAsleep(), R.string.format_asleep));
        awake.setText(getValue(day.getAwake(), R.string.format_awake));
        slept_for.setText(getValue(day.getSleptFor(), R.string.format_slept_for));
        napped_for.setText(getValue(day.getNappedFor(), R.string.format_napped_for));
        groggy_for.setText(getValue(day.getGroggyFor(), R.string.format_groggy_for));

        FragmentPageAdapter adapter = new FragmentPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mLastSelectedDay = calendar;
    }

    private String getValue(String source, int stringId) {
        if (source.isEmpty()) source = getString(R.string.tap_to_edit);
        return String.format(getString(stringId), source);
    }

    private String getValue(int source, int stringId) {
        if (source == -1)
            return String.format(getString(stringId), getString(R.string.tap_to_edit));
        return String.format(getString(stringId), source);
    }

    private String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) return n + "th";
        switch (n % 10) {
            case 1:
                return n + "st";
            case 2:
                return n + "nd";
            case 3:
                return n + "rd";
            default:
                return n + "th";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case DataEditorDialog.REQUEST_DATE_DATA:
                if (resultCode == RESULT_OK) {
                    loadDay(mLastSelectedDay);
                    Log.d(MainActivity.class.toString(), "Returning with a result of OK...");
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_data_edit: case R.id.main_data_tap_edit:
                Intent intent = new Intent(MainActivity.this, DataEditorDialog.class);
                startActivityForResult(intent, DataEditorDialog.REQUEST_DATE_DATA);
                break;
            case R.id.app_bar_save:
                Intent save = new Intent(MainActivity.this, SaveDialog.class);
                startActivity(save);
                break;
            case R.id.app_bar_settings:
                Toast.makeText(getApplicationContext(), "More info coming soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
