package com.yulin.viewpager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liulei0905 on 2016/2/5.
 */
public class WeiboActivity extends Activity implements View.OnClickListener {

    private ViewPager viewPager;
    private ImageView imageView;
    private List<View> viewContainer = new ArrayList<>();
    private int offset = 0;    // 动画图片偏移量
    private int bmpWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weibo);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        imageView = (ImageView) findViewById(R.id.iv_cursor);
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        TextView tv3 = (TextView) findViewById(R.id.tv3);

        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.layout_tab1, null);
        View view2 = inflater.inflate(R.layout.layout_tab2, null);
        View view3 = inflater.inflate(R.layout.layout_tab3, null);
        viewContainer.add(view1);
        viewContainer.add(view2);
        viewContainer.add(view3);

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        // 获取图片和图片宽度
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bottom_line);
        bmpWidth = bitmap.getWidth();

        // 获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;    // 获取屏幕分辨率宽度

        offset = (screenWidth / 3 - bmpWidth) / 2;
        Log.d("houchen", "bmpWidth is " + bmpWidth + ", screenWidth is " + screenWidth + ", offset is " + offset);

        Matrix matrix = new Matrix();
        // 设置动画初始位置
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv3:
                viewPager.setCurrentItem(2);
                break;
            default:
        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewContainer.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewContainer.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewContainer.get(position), 0);
            return viewContainer.get(position);
        }
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        private float one = offset * 2 + bmpWidth;
        private float currentX = 0;    // 初始位置

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // position表示两个相关联view中左边那个view的position
            // positionOffset表示两个相关联view中右边那个view已经显示出来的百分比(0.0~1.0)
            float toX = one * position + positionOffset * one;

            Animation animation = new TranslateAnimation(currentX, toX, 0, 0);
            animation.setFillAfter(true);    // 图片停留在动画结束位置
            animation.setDuration(0);
            imageView.startAnimation(animation);

            currentX = toX;
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    }

}
