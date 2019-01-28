package com.hc.animation.fade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hc.animation.R;

/**
 * 在淡出(fade out)一个控件的同时，淡入(fade in)另外一个控件
 * 适用场景：切换内容或view时
 * 虽然动画效果轻微，不易察觉，但使用淡出淡入使切换过程流畅自然，不再生硬
 *
 * 本例是从progress indicator到text的顺滑切换
 * */
public class CrossFadeActivity extends AppCompatActivity {

    private View mContentView;
    private View mLoadingView;
    private int mShortAnimationDuration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_fade);

        mContentView = findViewById(R.id.content);
        mLoadingView = findViewById(R.id.loading_spinner);

        /*
        * Initially hide the content view.
        * This prevents the view from taking up layout space and omits it from layout calculations, speeding up processing.
        * */
        mContentView.setVisibility(View.GONE);

        /*
        * This property defines a standard "short" duration for the animation. (200)
        * This duration is ideal for subtle animations or animations that occur very frequently.
        * */
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                crossFade();
            }
        }, 2000);
    }

    private void crossFade() {
        /*
        * 1. For the view that is fading in, set the alpha value to 0 and the visibility to VISIBLE.
        * (Remember that it was initially set to GONE.) This makes the view visible but completely transparent.
        * */
        mContentView.setAlpha(0f);
        mContentView.setVisibility(View.VISIBLE);

        /*
        * 2. For the view that is fading in, animate its alpha value from 0 to 1.
        * At the same time, for the view that is fading out, animate the alpha value from 1 to 0.
        * */
        mContentView.animate().alpha(1f).setDuration(mShortAnimationDuration).setListener(null);
        mLoadingView.animate().alpha(0f).setDuration(mShortAnimationDuration).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoadingView.setVisibility(View.GONE);
            }
        });
    }

}
