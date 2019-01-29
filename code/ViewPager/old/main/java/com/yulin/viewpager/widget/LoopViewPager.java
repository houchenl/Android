package com.yulin.viewpager.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by liulei0905 on 2016/3/23.
 */
public class LoopViewPager extends ViewPager {

    private OnPageChangeListener outOnPageChangeListener;

    public LoopViewPager(Context context) {
        this(context, null);
    }

    public LoopViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        super.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        outOnPageChangeListener = onPageChangeListener;
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (outOnPageChangeListener != null)
                outOnPageChangeListener.onPageSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (outOnPageChangeListener != null)
                outOnPageChangeListener.onPageScrollStateChanged(state);
        }

    };

}
