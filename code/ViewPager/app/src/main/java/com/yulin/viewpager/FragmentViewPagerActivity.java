package com.yulin.viewpager;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by liulei0905 on 2016/2/6.
 */
public class FragmentViewPagerActivity extends FragmentActivity {

    private int offset;
    private int bmpWidth;

    private ArrayList<Fragment> listFragments = new ArrayList<>();

    private ImageView ivCursor;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_viewpager);

        initTextViews();
        initImageCursor();
        initViewPager();
    }

    private void initTextViews() {
        TextView tvTab1 = (TextView) findViewById(R.id.tv_tab1);
        TextView tvTab2 = (TextView) findViewById(R.id.tv_tab2);
        TextView tvTab3 = (TextView) findViewById(R.id.tv_tab3);
        TextView tvTab4 = (TextView) findViewById(R.id.tv_tab4);

        MyOnClickListener clickListener = new MyOnClickListener();
        tvTab1.setOnClickListener(clickListener);
        tvTab2.setOnClickListener(clickListener);
        tvTab3.setOnClickListener(clickListener);
        tvTab4.setOnClickListener(clickListener);
    }

    private void initImageCursor() {
        ivCursor = (ImageView) findViewById(R.id.iv_cursor);

        bmpWidth = BitmapFactory.decodeResource(getResources(), R.drawable.bottom_line).getWidth();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;

        offset = (screenWidth / 4 - bmpWidth) / 2;

        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        ivCursor.setImageMatrix(matrix);
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        // get and add fragment.
        Fragment fragment1 = TestFragment.getInstance("tab1", "#ff9999");
        Fragment fragment2 = TestFragment.getInstance("tab2", "#99ff99");
        Fragment fragment3 = TestFragment.getInstance("tab3", "#9999ff");
        Fragment fragment4 = TestFragment.getInstance("tab4", "#99ffff");

        listFragments.add(fragment1);
        listFragments.add(fragment2);
        listFragments.add(fragment3);
        listFragments.add(fragment4);

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), listFragments));
        viewPager.setOnPageChangeListener(new MyOnPagerChangeListener());
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_tab1:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.tv_tab2:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.tv_tab3:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.tv_tab4:
                    viewPager.setCurrentItem(3);
                    break;
                default:
                    break;
            }
        }
    }

    private class MyOnPagerChangeListener implements ViewPager.OnPageChangeListener {
        private int one = offset * 2 + bmpWidth;
        private float currentX;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            float toX = one * position + positionOffset * one;

            Animation animation = new TranslateAnimation(currentX, toX, 0, 0);
            animation.setFillAfter(true);
            animation.setDuration(0);
            ivCursor.startAnimation(animation);

            currentX = toX;
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
