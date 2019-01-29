package com.yulin.viewpager.image;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.yulin.viewpager.R;

import java.util.ArrayList;
import java.util.List;

public class ImagePreviewActivity extends AppCompatActivity {

    private static final String TAG = "houchenl-Activity";

    private List<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_preview);

        initData();

        ImageFragmentPagerAdapter adapter = new ImageFragmentPagerAdapter(getSupportFragmentManager(), images);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

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

    @Override
    protected void onStop() {
        super.onStop();

        Glide.with(this).onStop();
    }

    private void initData() {
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu01.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu02.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu03.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu04.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu05.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu06.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu07.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu08.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu09.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu10.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu11.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu12.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu13.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu14.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu15.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu16.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu17.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu18.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu19.jpg");
    }

}
