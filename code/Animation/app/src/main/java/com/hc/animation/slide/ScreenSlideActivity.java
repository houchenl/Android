package com.hc.animation.slide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.hc.animation.R;

/**
 * 使用ViewPager在两个全屏界面之间切换
 * */
public class ScreenSlideActivity extends AppCompatActivity {

    private ViewPager mPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
        mPager.setPageTransformer(true, new ZoomOutPagerTransformer());
//        mPager.setPageTransformer(true, new DepthPagerTransformer());
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            /*
            * If the user is currently looking at the first step, allow the system to handle the
            * Back button. This calls finish() on this activity and pops the back stack.
            * */
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

}
