package com.android.crystal.cafe;

/**
 * Created by Crystal on 7/23/16.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    // Tab Titles
    private String tabtitles[] = new String[] { "Tab1", "Tab2"};
    Context context;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

   //@Override
    public int getCount() {
        return PAGE_COUNT;
    }

    //@Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                MapFragment fragmenttab1 = new MapFragment();
                return fragmenttab1;

            case 1:
                ListFragment fragmenttab2 = new ListFragment();
                return fragmenttab2;

        }
        return null;
    }

   // @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }


}
