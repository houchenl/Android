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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yulin.viewpager.R;
import com.yulin.viewpager.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作状态栏：https://blog.csdn.net/chazihong/article/details/70228933
 * */
public class ImagePreviewActivity extends FragmentActivity {

    private static final String TAG = "houchenl-PreviewActivit";

    private static final String EXTRA_POSITION = "extra_position";
    private static final String EXTRA_ITEM_X = "extra_pivot_x";
    private static final String EXTRA_ITEM_Y = "extra_pivot_y";
    private static final String EXTRA_ITEM_WIDTH = "extra_width";
    private static final String EXTRA_ITEM_HEIGHT = "extra_height";
    private static final String EXTRA_TITLE_BAR_HEIGHT = "extra_title_bar_height";

    private List<String> images = new ArrayList<>();

    private View maskView;
    private ViewPager viewPager;
    private TextView mTvIndex;

    /**
     * 点击的待预览图片在原界面的x,y及宽高
     * */
    private float mItemX;
    private float mItemY;
    private int mItemWidth, mItemHeight;

    private float mScreenWidth, mScreenHeight;
    private int mTitleBarHeight;    // 上个界面标题栏高度

    private int mEnterPosition, mCurrentPosition;

    public static void startActivity(Activity activity, int position, float x, float y, int width, int height, int titleBarHeight) {
        Intent intent = new Intent(activity, ImagePreviewActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        intent.putExtra(EXTRA_ITEM_X, x);
        intent.putExtra(EXTRA_ITEM_Y, y);
        intent.putExtra(EXTRA_ITEM_WIDTH, width);
        intent.putExtra(EXTRA_ITEM_HEIGHT, height);
        intent.putExtra(EXTRA_TITLE_BAR_HEIGHT, titleBarHeight);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        Intent intent = getIntent();
        if (intent != null) {
            mCurrentPosition = intent.getIntExtra(EXTRA_POSITION, 0);
            mEnterPosition = mCurrentPosition;
            mItemX = intent.getFloatExtra(EXTRA_ITEM_X, 0f);
            mItemY = intent.getFloatExtra(EXTRA_ITEM_Y, 0f);
            mItemWidth = intent.getIntExtra(EXTRA_ITEM_WIDTH, 0);
            mItemHeight = intent.getIntExtra(EXTRA_ITEM_HEIGHT, 0);
            mTitleBarHeight = intent.getIntExtra(EXTRA_TITLE_BAR_HEIGHT, 0);
        }

        initData();
        mScreenWidth = Tool.getScreenWidth(this);
        mScreenHeight = Tool.getScreenHeight(this);

        ImageFragmentPagerAdapter adapter = new ImageFragmentPagerAdapter(getSupportFragmentManager(), images);

        maskView = findViewById(R.id.mask_view);
        viewPager = findViewById(R.id.view_pager);
        mTvIndex = findViewById(R.id.tv_index);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mEnterPosition);

        mTvIndex.setText((mCurrentPosition + 1) + "/" + images.size());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mCurrentPosition = i;
                Log.d(TAG, "onPageSelected: " + i);
                mTvIndex.setText((mCurrentPosition + 1) + "/" + images.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        enterAnim();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Glide.with(this).onStop();
    }

    public void finishActivity() {
        /*
         * 图片在ViewPager中预览时，图片左右顶边显示，正好完整显示，上下完全显示之外，还有背景色填充空间。
         * 缩小时，是整个ViewPager在缩小，应该保证图片垂直有效部分缩小到原来图片高度，而不是整个ViewPager缩小到
         * 原图片高度
         * */
        float scaledItemRealHeight = mScreenWidth * mItemHeight / mItemWidth;
        float scaleX = mItemWidth / mScreenWidth;
        float scaleY = mItemHeight / scaledItemRealHeight;

        AnimatorSet set = new AnimatorSet();

        // 停止预览时，背景色从透明到黑色渐变
        final ObjectAnimator maskAnim = ObjectAnimator.ofFloat(maskView, "alpha", 1, 0);
        final ObjectAnimator indexAnim = ObjectAnimator.ofFloat(mTvIndex, "alpha", 1, 0);
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(viewPager, "scaleX", 1f, scaleX);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(viewPager, "scaleY", 1f, scaleY);

        if (mCurrentPosition != mEnterPosition) {
            // 退出动画不再退到原图位置，直接退到屏幕正中央
            final ObjectAnimator viewPagerAnim = ObjectAnimator.ofFloat(viewPager, "alpha", 1, 0);
            set.play(maskAnim).with(indexAnim).with(scaleXAnim).with(scaleYAnim).with(viewPagerAnim);
        } else {
            float translationX = mItemX - (mScreenWidth - mItemWidth) / 2;
            float scaledY = (mScreenHeight - mItemHeight) / 2;  // ViewPager缩放后图片上边缘的y
            float translationY = mItemY + mTitleBarHeight - scaledY;
            ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(viewPager, "translationX", translationX);
            ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(viewPager, "translationY", translationY);
            set.play(maskAnim).with(indexAnim).with(scaleXAnim).with(scaleYAnim).with(translationXAnim).with(translationYAnim);
        }

        set.setDuration(350);
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

    private void enterAnim() {
        float scaledItemRealHeight = mScreenWidth * mItemHeight / mItemWidth;
        float scaleX = mItemWidth / mScreenWidth;
        float scaleY = mItemHeight / scaledItemRealHeight;

        float translationX = mItemX - (1 - scaleX) * mScreenWidth / 2;
        float scaledY = (mScreenHeight - mItemHeight) / 2;  // ViewPager缩放后图片上边缘的y
        float translationY = mItemY + mTitleBarHeight - scaledY;

        // 启动预览时，背景色从透明到黑色渐变
        viewPager.setTranslationX(translationX);
        viewPager.setTranslationY(translationY);
        ObjectAnimator maskAnim = ObjectAnimator.ofFloat(maskView, "alpha", 0, 1);
        ObjectAnimator indexAnim = ObjectAnimator.ofFloat(mTvIndex, "alpha", 0, 1);
        ObjectAnimator xAnim = ObjectAnimator.ofFloat(viewPager, "scaleX", scaleX, 1f);
        ObjectAnimator yAnim = ObjectAnimator.ofFloat(viewPager, "scaleY", scaleY, 1f);
        ObjectAnimator xPivot = ObjectAnimator.ofFloat(viewPager, "translationX", 0 );
        ObjectAnimator yPivot = ObjectAnimator.ofFloat(viewPager, "translationY", 0);
        AnimatorSet set = new AnimatorSet();
        set.play(maskAnim).with(indexAnim).with(xAnim).with(yAnim).with(xPivot).with(yPivot);
        set.setDuration(350);
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
