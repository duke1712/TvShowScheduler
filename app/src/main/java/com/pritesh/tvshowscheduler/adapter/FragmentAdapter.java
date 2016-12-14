package com.pritesh.tvshowscheduler.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pritesh.tvshowscheduler.ui.MainFragment;
import com.pritesh.tvshowscheduler.ui.ReminderFragment;

/**
 * Created by prittesh on 14/12/16.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show image
                return new MainFragment();
            case 1: // Fragment # 1 - This will show image
                return new ReminderFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 2;
    }
}
