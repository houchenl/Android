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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yulin.viewpager.R;
import com.yulin.viewpager.Tool;

import java.util.ArrayList;

/**
 * 操作状态栏：https://blog.csdn.net/chazihong/article/details/70228933
 */
public class ImagePreviewActivity extends FragmentActivity {

    private static final String TAG = "houchenl-PreviewActivit";

    private static final String EXTRA_IMAGES = "extra_images";
    private static final String EXTRA_POSITION = "extra_position";
    private static final String EXTRA_ITEM_X = "extra_pivot_x";
    private static final String EXTRA_ITEM_Y = "extra_pivot_y";
    private static final String EXTRA_ITEM_WIDTH = "extra_width";
    private static final String EXTRA_ITEM_HEIGHT = "extra_height";
    private static final String EXTRA_TITLE_BAR_HEIGHT = "extra_title_bar_height";

    private ArrayList<String> mImageUrls = new ArrayList<>();

    private View maskView;
    private ViewPager viewPager;
    private TextView mTvIndex;

    /**
     * 点击的待预览图片在原界面的x,y及宽高
     */
    private float mItemX;
    private float mItemY;
    private int mItemWidth, mItemHeight;

    private float mScreenWidth, mScreenHeight;
    private int mTitleBarHeight;    // 上个界面标题栏高度

    private int mEnterPosition, mCurrentPosition;

    public static void startActivity(Activity activity, ArrayList<String> urls, int position,
                                     float x, float y, int width, int height, int titleBarHeight) {
        if (activity == null) {
            return;
        }
        if (urls == null || urls.size() < 1) {
            Toast.makeText(activity, "预览图片为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (position >= urls.size() || position < 0) {
            Toast.makeText(activity, "预览图片下标越界", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(activity, ImagePreviewActivity.class);
        intent.putStringArrayListExtra(EXTRA_IMAGES, urls);
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

        initData();
        initView();
        startEnterAnim();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Glide.with(this).onStop();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mImageUrls = intent.getStringArrayListExtra(EXTRA_IMAGES);
            mCurrentPosition = intent.getIntExtra(EXTRA_POSITION, 0);
            mEnterPosition = mCurrentPosition;
            mItemX = intent.getFloatExtra(EXTRA_ITEM_X, 0f);
            mItemY = intent.getFloatExtra(EXTRA_ITEM_Y, 0f);
            mItemWidth = intent.getIntExtra(EXTRA_ITEM_WIDTH, 0);
            mItemHeight = intent.getIntExtra(EXTRA_ITEM_HEIGHT, 0);
            mTitleBarHeight = intent.getIntExtra(EXTRA_TITLE_BAR_HEIGHT, 0);
        }

        mScreenWidth = Tool.getScreenWidth(this);
        mScreenHeight = Tool.getScreenHeight(this);
    }

    private void initView() {
        maskView = findViewById(R.id.mask_view);
        viewPager = findViewById(R.id.view_pager);
        mTvIndex = findViewById(R.id.tv_index);

        ImageFragmentPagerAdapter adapter = new ImageFragmentPagerAdapter(getSupportFragmentManager(), mImageUrls);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mCurrentPosition);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mCurrentPosition = i;
                Log.d(TAG, "onPageSelected: " + i);
                mTvIndex.setText(getResources().getString(R.string.photo_number_format, mCurrentPosition + 1, mImageUrls.size()));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        if (mImageUrls.size() == 1) {
            // 如果只有一张图片，不显示序号
            mTvIndex.setVisibility(View.GONE);
        } else {
            mTvIndex.setText(getResources().getString(R.string.photo_number_format, mCurrentPosition + 1, mImageUrls.size()));
        }
    }

    private void startEnterAnim() {
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
        ObjectAnimator xPivot = ObjectAnimator.ofFloat(viewPager, "translationX", 0);
        ObjectAnimator yPivot = ObjectAnimator.ofFloat(viewPager, "translationY", 0);
        AnimatorSet set = new AnimatorSet();
        set.play(maskAnim).with(indexAnim).with(xAnim).with(yAnim).with(xPivot).with(yPivot);
        set.setDuration(350);
        set.start();
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

}
