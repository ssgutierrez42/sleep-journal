package com.stanford.sleepjournal.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stanford.sleepjournal.Application;
import com.stanford.sleepjournal.R;
import com.stanford.sleepjournal.utils.Entry;
import com.vi.swipenumberpicker.OnValueChangeListener;
import com.vi.swipenumberpicker.SwipeNumberPicker;

import java.util.List;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class SleepHourFragment extends Fragment implements OnValueChangeListener {

    private ImageView mMood;
    private TextView mMoodText;
    private TextView mHourText;
    private Entry mHourObject = null;

    public SleepHourFragment() { }

    public static SleepHourFragment newInstance(String hour) {
        SleepHourFragment fragment = new SleepHourFragment();
        Bundle args = new Bundle();
        args.putString("hour", hour);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_sleep_hour, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadHourObject();

        mMood = (ImageView) view.findViewById(R.id.item_sleep_hour_mood);
        mMoodText = (TextView) view.findViewById(R.id.item_sleep_hour_alertness_text);
        mHourText = (TextView) view.findViewById(R.id.item_sleep_hour_text);

        SwipeNumberPicker picker = (SwipeNumberPicker) view.findViewById(R.id.item_sleep_hour_alertness);
        picker.setOnValueChangeListener(this);
        picker.setValue(mHourObject.getMood(), false);

        if(mHourObject.getMood() > 0) picker.setMinValue(1);

        setAlertness(mHourObject.getMood(), false); //default
    }

    private void setAlertness(int level, boolean save){
        mMood.setImageDrawable(getMoodIcon(level));
        mMoodText.setText(getMoodText(level));
        mHourText.setText(mHourObject.getHour());

        if(save) {
            mHourObject.setMood(level);
            mHourObject.save();
            Log.d(SleepHourFragment.class.toString(), mHourObject.toString());
        }
    }

    private void loadHourObject(){
        List<Entry> finds = Entry.find(Entry.class, "date = ? AND hour = ?", Application.getSelectedDay().getDate(), getArguments().getString("hour"));
        if(finds.size() == 0) {
            mHourObject = new Entry(Application.getSelectedDay().getDate(), getArguments().getString("hour"));
        } else {
            mHourObject = finds.get(0);
        }
    }

    private Drawable getMoodIcon(int value){
        switch(value){
            case 1:
                return ContextCompat.getDrawable(getContext(), R.drawable.ic_mood_1);
            case 2:
                return ContextCompat.getDrawable(getContext(), R.drawable.ic_mood_2);
            case 3:
                return ContextCompat.getDrawable(getContext(), R.drawable.ic_mood_3);
            case 4:
                return ContextCompat.getDrawable(getContext(), R.drawable.ic_mood_4);
            case 5:
                return ContextCompat.getDrawable(getContext(), R.drawable.ic_mood_5);
            case 6:
                return ContextCompat.getDrawable(getContext(), R.drawable.ic_mood_6);
            default:
                return ContextCompat.getDrawable(getContext(), R.drawable.ic_mood_unknown);
        }
    }

    private String getMoodText(int value){
        switch(value){
            case 1:
                return getString(R.string.mood_1);
            case 2:
                return getString(R.string.mood_2);
            case 3:
                return getString(R.string.mood_3);
            case 4:
                return getString(R.string.mood_4);
            case 5:
                return getString(R.string.mood_5);
            case 6:
                return getString(R.string.mood_6);
            default:
                return getString(R.string.mood_default);
        }
    }

    @Override
    public boolean onValueChange(SwipeNumberPicker view, int oldValue, int newValue) {
        if(oldValue == 0 && newValue > 0) view.setMinValue(1);
        setAlertness(newValue, true);
        return true;
    }
}
