package com.stanford.sleepjournal.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.stanford.sleepjournal.Constants;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter {

    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SleepHourFragment.newInstance(hashKey(position));
    }

    private String hashKey(int position){
        Log.d(FragmentPageAdapter.class.toString(), "Getting: " + position);
        if(position < 0 || position > 11) return "ERR_GDI";
        return Constants.HOURS[position];
    }

    @Override
    public int getCount() {
        return 12;
    }
}
