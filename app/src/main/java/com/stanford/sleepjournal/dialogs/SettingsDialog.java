package com.stanford.sleepjournal.dialogs;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.stanford.sleepjournal.dialogs.DialogEditorAdapter.leftPad;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class SettingsDialog extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_settings);

        Button update = (Button) findViewById(R.id.info_ok);
        assert update != null;
        update.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.info_ok:
                finish();
                break;
        }
    }
}
