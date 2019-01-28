package com.yulin.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by liulei0905 on 2016/2/5.
 */
public class MyPagerAdapter extends PagerAdapter {

    private ArrayList<View> viewContainer = new ArrayList<>();
    private ArrayList<String> titleContainer = new ArrayList<>();

    public MyPagerAdapter(ArrayList<View> viewContainer, ArrayList<String> titleContainer) {
        this.viewContainer = viewContainer;
        this.titleContainer = titleContainer;
    }

    @Override
    public int getCount() {
        return viewContainer.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // 滑动切换的时候销毁当前的组件
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewContainer.get(position));
    }

    // 每次滑动的时候生成组件
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewContainer.get(position));
        return viewContainer.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleContainer.get(position);
    }

}
