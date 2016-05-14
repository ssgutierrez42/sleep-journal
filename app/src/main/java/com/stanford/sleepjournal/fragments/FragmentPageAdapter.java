package com.stanford.sleepjournal.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter {

    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
                return SleepHourFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
