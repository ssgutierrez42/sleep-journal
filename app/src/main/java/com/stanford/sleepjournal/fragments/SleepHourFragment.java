package com.stanford.sleepjournal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stanford.sleepjournal.R;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class SleepHourFragment extends Fragment {

    public SleepHourFragment() {} // Required empty public constructor

    public static SleepHourFragment newInstance() {
        return new SleepHourFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_sleep_hour, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
