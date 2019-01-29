package com.yulin.viewpager.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yulin.viewpager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用fragment实现ViewPager的基础使用
 * */
public class BasicFragmentSlideActivity extends AppCompatActivity {

    private static final String TAG = "houchenl-Activity";

    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_fragment_slide);

        initData();

        TextFragmentPagerAdapter pagerAdapter = new TextFragmentPagerAdapter(getSupportFragmentManager(), data);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.d(TAG, "onPageSelected: " + i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initData() {
        data.add("1月 January");
        data.add("2月 February");
        data.add("3月 March");
        data.add("4月 April");
        data.add("5月 May");
        data.add("6月 June");
        data.add("7月 July");
        data.add("8月 August");
        data.add("9月 September");
        data.add("10月 October");
        data.add("11月 November");
        data.add("12月 December");
    }

}
