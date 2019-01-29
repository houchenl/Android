package com.yulin.viewpager.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class TextFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> data;

    public TextFragmentPagerAdapter(FragmentManager fm, List<String> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int i) {
        return TextFragment.getInstance(data.get(i));
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }
}
