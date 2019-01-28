package com.hc.animation.tween;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.hc.animation.R;

/**
 * Created by liulei0905 on 2016/7/14.
 *
 */
public class TweenAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImage;
    private TextView mTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween_animation);

        findViewById(R.id.btn_translate).setOnClickListener(this);
        findViewById(R.id.btn_scale).setOnClickListener(this);
        findViewById(R.id.btn_rotate).setOnClickListener(this);
        findViewById(R.id.btn_alpha).setOnClickListener(this);
        findViewById(R.id.btn_set).setOnClickListener(this);
        findViewById(R.id.btn_translate_xml).setOnClickListener(this);
        findViewById(R.id.btn_scale_xml).setOnClickListener(this);
        findViewById(R.id.btn_rotate_xml).setOnClickListener(this);
        findViewById(R.id.btn_alpha_xml).setOnClickListener(this);
        findViewById(R.id.btn_set_xml).setOnClickListener(this);
        mImage = (ImageView) findViewById(R.id.img);
        mTv = (TextView) findViewById(R.id.tv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_translate:
                translateAnimation();
                break;
            case R.id.btn_scale:
                scaleAnimation();
                break;
            case R.id.btn_rotate:
                rotateAnimation();
                break;
            case R.id.btn_alpha:
                alphaAnimation();
                break;
            case R.id.btn_set:
                setAnimation();
                break;
            case R.id.btn_translate_xml:
                xmlAnimation(R.anim.translate);
                break;
            case R.id.btn_scale_xml:
                xmlAnimation(R.anim.scale);
                break;
            case R.id.btn_rotate_xml:
                xmlAnimation(R.anim.rotate);
                break;
            case R.id.btn_alpha_xml:
                xmlAnimation(R.anim.alpha);
                break;
            case R.id.btn_set_xml:
                xmlAnimation(R.anim.set);
                break;
        }
    }

    /**
     * 平移动画
     * */
    private void translateAnimation() {
        /*
        * Animation.RELATIVE_TO_SELF，设置动画控件的左上角为坐标原点，
        * 动画旋转轴的坐标为(view.getWidth*pivotXValue, view.getHeight*pivotYValue)
        * 负数向左(X轴)/上(Y轴)偏移，正数向右(X轴)/下(Y轴)偏移
        * */
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        translateAnimation.setDuration(1000);

        mImage.startAnimation(translateAnimation);
    }

    /**
     * 缩放动画
     * */
    private void scaleAnimation() {
        /*
        * 第一个参数fromX为动画起始时 X坐标上的伸缩尺寸
        * 第二个参数toX为动画结束时 X坐标上的伸缩尺寸
        * 第三个参数fromY为动画起始时Y坐标上的伸缩尺寸
        * 第四个参数toY为动画结束时Y坐标上的伸缩尺寸
        *   说明:
        *       以上四种属性值
        *       0.0表示收缩到没有
        *       1.0表示正常无伸缩
        *       值小于1.0表示收缩
        *       值大于1.0表示放大
        * 第五个参数pivotXType为动画在X轴相对于物件位置类型
        * 第六个参数pivotXValue为动画相对于物件的X坐标的开始位置
        * 第七个参数pivotYType为动画在Y轴相对于物件位置类型
        * 第八个参数pivotYValue为动画相对于物件的Y坐标的开始位置
        * 后四个参数用于确定缩放基准点
        * */
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 1.2f,
                1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);

        mImage.startAnimation(scaleAnimation);
    }

    /**
     * 旋转动画
     * */
    private void rotateAnimation() {
        /*
        * 参数1： 起始角度
        * 参数2： 终止角度
        * 参数3/4/5/6： 确定旋转中心
        * */
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setRepeatMode(Animation.REVERSE);

        mImage.startAnimation(rotateAnimation);
    }

    /**
     * 透明度动画
     * */
    private void alphaAnimation() {
        /*
        * 参数1： 动画开始时透明度
        * 参数2： 动画结束时透明度
        * 说明: 0.0表示完全透明,1.0表示完全不透明
        * */
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);
        alphaAnimation.setDuration(1000);
        // 动画结束时保持在结束状态
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mTv.setText("onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTv.setText("onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                mTv.setText("onAnimationRepeat");
            }
        });

        mImage.startAnimation(alphaAnimation);
    }

    /**
     * 组合动画，同时包含以上四种基本动画中的一种或多种
     * */
    private void setAnimation() {
        /*
        * 动画执行完后，再重新执行一遍，重新执行时朝相反方向缩放，必须指定repeatCount，重新执行才会生效
        * 必须单独为每一个动画效果设置repeat效果，在set中设置不会生效
        * */

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setRepeatMode(Animation.REVERSE);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 1.2f,
                1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        translateAnimation.setDuration(1000);
        translateAnimation.setRepeatCount(1);
        translateAnimation.setRepeatMode(Animation.REVERSE);

        // 传入参数true，则AnimationSet中所有Animation共用一个interpolator
        AnimationSet animationSet = new AnimationSet(true);
        // 基本动画的添加顺序会对最后的显示效果有影响
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);

        mImage.startAnimation(animationSet);
    }

    /**
     * xml实现的tween动画
     * */
    private void xmlAnimation(int animId) {
        Animation animation = AnimationUtils.loadAnimation(this, animId);
        mImage.startAnimation(animation);
    }

}
