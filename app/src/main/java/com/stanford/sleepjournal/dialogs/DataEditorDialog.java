package com.stanford.sleepjournal.dialogs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stanford.sleepjournal.Application;
import com.stanford.sleepjournal.R;
import com.stanford.sleepjournal.utils.Day;
import com.stanford.sleepjournal.utils.Editable;

import java.util.ArrayList;
import java.util.List;

import static com.stanford.sleepjournal.utils.Editable.TYPE_TIME;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class DataEditorDialog extends AppCompatActivity implements DialogEditorAdapter.OnItemClickListener, View.OnClickListener {

    public static final int REQUEST_DATE_DATA = 0;

    private DialogEditorAdapter mAdapter;
    private Day mCurrentDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_info_editor);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new DialogEditorAdapter(this);
        recyclerView.setAdapter(mAdapter);

        List<Day> finds = Day.find(Day.class, "date = ?", Application.getSelectedDay().getDate());
        if(finds.isEmpty()){
            Toast.makeText(getApplicationContext(), "Error loading this day.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mCurrentDay = finds.get(0);

        TextView title = (TextView) findViewById(R.id.info_editor_title);
        assert title != null;
        title.setText(mCurrentDay.getFormatDate());

        Button cancel = (Button) findViewById(R.id.info_editor_cancel);
        Button update = (Button) findViewById(R.id.info_editor_update);
        assert cancel != null;
        assert update != null;
        cancel.setOnClickListener(this);
        update.setOnClickListener(this);

        init();
    }

    private void init(){
        List<Editable> editableEntries = new ArrayList<>();

        editableEntries.add(new Editable("I went to bed at", getValue(mCurrentDay.getInBed()), TYPE_TIME, new Editable.EditableAction() {
            @Override
            public void saveAction(String value) {
                mCurrentDay.setInBed(value);
            }
        }));

        editableEntries.add(new Editable("Fell asleep at", getValue(mCurrentDay.getAsleep()), TYPE_TIME, new Editable.EditableAction() {
            @Override
            public void saveAction(String value) {
                mCurrentDay.setAsleep(value);
                Log.d(DataEditorDialog.class.toString(), "Set asleep to: " + value);
            }
        }));

        editableEntries.add(new Editable("Woke up at", getValue(mCurrentDay.getAwake()), TYPE_TIME, new Editable.EditableAction() {
            @Override
            public void saveAction(String value) {
                mCurrentDay.setAwake(value);
            }
        }));

        editableEntries.add(new Editable("Got out of bed at", getValue(mCurrentDay.getOutOfBed()), TYPE_TIME, new Editable.EditableAction() {
            @Override
            public void saveAction(String value) {
                mCurrentDay.setOutOfBed(value);
            }
        }));

        mAdapter.setData(editableEntries);
    }

    private String getValue(String val){
        return val.isEmpty() ? "?" : val;
    }

    @Override
    public void itemClicked(Editable editable, String value) {
        editable.getAction().saveAction(value);
        Log.d(DataEditorDialog.class.toString(), "\"" + editable.getName() + "\" pressed with value " + value);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.info_editor_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.info_editor_update:
                mCurrentDay.save();
                Toast.makeText(getApplicationContext(), "Data successfully set", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
