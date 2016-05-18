package com.stanford.sleepjournal.dialogs;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.stanford.sleepjournal.MainActivity;
import com.stanford.sleepjournal.R;
import com.stanford.sleepjournal.utils.Day;
import com.stanford.sleepjournal.utils.ExcelManager;
import com.vi.swipenumberpicker.OnValueChangeListener;
import com.vi.swipenumberpicker.SwipeNumberPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.stanford.sleepjournal.dialogs.DialogEditorAdapter.leftPad;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class SaveDialog extends AppCompatActivity implements View.OnClickListener {

    private TextView mDateHolder;
    private SwipeNumberPicker mPicker;
    private Calendar mSelectedDay = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_save);

        Button cancel = (Button) findViewById(R.id.info_save_cancel);
        Button update = (Button) findViewById(R.id.info_save_update);
        assert cancel != null;
        assert update != null;
        cancel.setOnClickListener(this);
        update.setOnClickListener(this);

        mDateHolder = (TextView) findViewById(R.id.dialog_save_date);
        assert mDateHolder != null;
        mDateHolder.setOnClickListener(this);

        mPicker = (SwipeNumberPicker) findViewById(R.id.dialog_save_number);
        mPicker.setOnValueChangeListener(new OnValueChangeListener() {
            @Override
            public boolean onValueChange(SwipeNumberPicker view, int oldValue, int newValue) {
                return true;
            }
        });
        assert mPicker != null;
    }

    private List<Day> getRequestedDays(){
        List<Day> result = new ArrayList<>();

//        Log.d(SaveDialog.class.toString(), "Picker Val: " + mPicker.getValue());
        for(int i = 0; i < mPicker.getValue(); i++){
            Calendar instance = mSelectedDay;
            instance.set(mSelectedDay.get(Calendar.YEAR), mSelectedDay.get(Calendar.MONTH), mSelectedDay.get(Calendar.DAY_OF_MONTH)+i);
            List<Day> finds = Day.find(Day.class, "date = ?", MainActivity.getKeyFromCalendar(instance));
            if(!finds.isEmpty()) result.add(finds.get(0));
        }

//        Log.d(SaveDialog.class.toString(), "Result Size: " + result.size());
        return result;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.dialog_save_date:
                Calendar c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(SaveDialog.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = leftPad(++monthOfYear) + "/" + leftPad(dayOfMonth) + "/" + leftPad(year);
                        mDateHolder.setText(date);
                        mSelectedDay = Calendar.getInstance();
                        mSelectedDay.set(year, monthOfYear, dayOfMonth);
                    }
                }, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
            case R.id.info_save_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.info_save_update:
                List<Day> requestedDays = getRequestedDays();
                for(Day d : requestedDays){
                    Log.d(SaveDialog.class.toString(), d.toString());
                }

                try {
                    ExcelManager manager = new ExcelManager(getApplicationContext(), requestedDays);
                    manager.createSheet();
                    Toast.makeText(getApplicationContext(), "Saving data test successful.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed saving.", Toast.LENGTH_SHORT).show();
                }
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}