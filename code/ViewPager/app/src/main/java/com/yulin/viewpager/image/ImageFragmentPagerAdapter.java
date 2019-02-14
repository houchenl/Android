package com.yulin.viewpager.image;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ImageFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> images;

    ImageFragmentPagerAdapter(FragmentManager fm, List<String> images) {
        super(fm);
        this.images = images;
    }

    @Override
    public Fragment getItem(int i) {
        return ImageFragment.getInstance(images.get(i), i);
    }

    @Override
    public int getCount() {
        return images != null ? images.size() : 0;
    }

}
