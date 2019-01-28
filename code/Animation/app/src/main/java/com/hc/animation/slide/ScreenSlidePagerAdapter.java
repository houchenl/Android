package com.hc.animation.slide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private static final short NUM_PAGES = 5;

    ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new ScreenSlidePageFragment();
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

}
