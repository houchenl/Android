package com.yulin.viewpager.image;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.yulin.viewpager.R;

import java.util.ArrayList;
import java.util.List;

public class ImagePreviewActivity extends FragmentActivity {

    private static final String TAG = "houchenl-PreviewActivit";

    private static final String EXTRA_POSITION = "extra_position";

    private List<String> images = new ArrayList<>();

    private View maskView;
    private ViewPager viewPager;

    public static void startActivity(Activity context, int position) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        Log.d(TAG, "onCreate: position " + position);

        initData();

        ImageFragmentPagerAdapter adapter = new ImageFragmentPagerAdapter(getSupportFragmentManager(), images);

        maskView = findViewById(R.id.mask_view);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

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

        // 启动预览时，背景色从透明到黑色渐变
        ObjectAnimator maskAnim = ObjectAnimator.ofFloat(maskView, "alpha", 0, 1);
        ObjectAnimator xAnim = ObjectAnimator.ofFloat(viewPager, "scaleX", 0.3f, 1f);
        ObjectAnimator yAnim = ObjectAnimator.ofFloat(viewPager, "scaleY", 0.3f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(maskAnim).with(xAnim).with(yAnim);
        set.setDuration(300);
        set.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Glide.with(this).onStop();
    }

    public void finishActivity() {
        // 启动预览时，背景色从透明到黑色渐变
        final ObjectAnimator maskAnim = ObjectAnimator.ofFloat(maskView, "alpha", 1, 0);
        ObjectAnimator pageAnim = ObjectAnimator.ofFloat(viewPager, "alpha", 1f, 0f);
        ObjectAnimator xAnim = ObjectAnimator.ofFloat(viewPager, "scaleX", 1f, 0.3f);
        ObjectAnimator yAnim = ObjectAnimator.ofFloat(viewPager, "scaleY", 1f, 0.3f);
        AnimatorSet set = new AnimatorSet();
        set.play(maskAnim).with(xAnim).with(yAnim).with(pageAnim);
        set.setDuration(300);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewPager.setVisibility(View.GONE);
                maskView.setVisibility(View.GONE);

                ImagePreviewActivity.this.finish();
            }
        });
        set.start();
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
