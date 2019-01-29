package com.yulin.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by liulei0905 on 2016/2/5.
 */
public class PagerAdapterActivity extends Activity {

    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;

    private ArrayList<View> viewContainer = new ArrayList<>();
    private ArrayList<String> titleContainer = new ArrayList<>();

    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_adapter);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.tab_strip);

        // 取消tab下面的长横线
        pagerTabStrip.setDrawFullUnderline(false);
        // 设置tab的背景色
        pagerTabStrip.setBackgroundResource(R.color.tab_strip_bg);
        // 设置当前tab页签下划线的颜色
        pagerTabStrip.setTabIndicatorColorResource(R.color.red);

        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_tab1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.layout_tab2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.layout_tab3, null);
        View view4 = LayoutInflater.from(this).inflate(R.layout.layout_tab4, null);
        viewContainer.add(0, view1);
        viewContainer.add(1, view2);
        viewContainer.add(2, view3);
        viewContainer.add(3, view4);

        titleContainer.add(0, "网易新闻");
        titleContainer.add(1, "网易财经");
        titleContainer.add(2, "网易体育");
        titleContainer.add(3, "网易女人");

        adapter = new MyPagerAdapter(viewContainer, titleContainer);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // positionOffset表示右边界面显示的百分比，范围是0到1，0时表示右边界面完全未显示，
                // 1表示右边界面完全显示，0.5表示右边界面显示一半
                // position 表示左右切换时，左边页面的position
                Log.d("houchen", "onPageScrolled()--> position is " + position + ", positionOffset is " + positionOffset + ", positionOffsetPixels is " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                // position 表示左右切换时目的页面的position
                Log.d("houchen", "onPageSelected()--> position is " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("houchen", "onPageScrollStateChanged()--> state is " + state);
            }
        });
    }

}
