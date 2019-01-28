package com.hc.animation.property;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hc.animation.R;

public class PropertyAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImage, mImgBall, mImgBall2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animaion);

        findViewById(R.id.btn_single).setOnClickListener(this);
        findViewById(R.id.btn_set).setOnClickListener(this);
        findViewById(R.id.btn_timer).setOnClickListener(this);
        findViewById(R.id.btn_parabola).setOnClickListener(this);
        mImage = (ImageView) findViewById(R.id.img);
        mImgBall = (ImageView) findViewById(R.id.img_ball);
        mImgBall2 = (ImageView) findViewById(R.id.img_ball_2);
    }

    public void clickImg(View view) {
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        animatorAlpha.setDuration(1000);
        // 添加动画监听事件
        // 方案1
        /*animatorAlpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Toast.makeText(PropertyAnimationActivity.this, "anim end", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });*/
        // 方案2，对某一个事件监听，不需要监听所有事件
        animatorAlpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Toast.makeText(PropertyAnimationActivity.this, "anim end", Toast.LENGTH_SHORT).show();
            }
        });
        animatorAlpha.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_single:
                singleAnimator();
                break;
            case R.id.btn_set:
                animatorSet();
                break;
            case R.id.btn_timer:
                timer(view);
                break;
            case R.id.btn_parabola:
                parabolaAndDrop();
                break;
        }
    }

    /**
     * 单个动画效果
     * */
    private void singleAnimator() {
        /*
        * 第一个参数，需要操作的对象
        * 第二个参数，需要操作的属性
        * 第三个参数，是动画变化的取值范围
        *
        * 常见属性
        * translationX    偏移量
        * translationY    偏移量
        * x               最终到达的绝对值
        * y               最终到达的绝对值
        * rotation        旋转
        * alpha           透明度
        * */
//        ObjectAnimator.ofFloat(mImage, "translationX", 0f, 200f).setDuration(1000).start();
//        ObjectAnimator.ofFloat(mImage, "rotation", 0f, 360f).setDuration(1000).start();
        ObjectAnimator.ofFloat(mImage, "alpha", 1.0f, 0.5f).setDuration(1000).start();
    }

    /**
    * 组合动画效果
    * */
    private void animatorSet() {
        // 以下动画效果同时执行

        // 方案1
        /*ObjectAnimator.ofFloat(mImage, "translationX", 0f, 200f).setDuration(1000).start();
        ObjectAnimator.ofFloat(mImage, "translationY", 0f, 200f).setDuration(1000).start();
        ObjectAnimator.ofFloat(mImage, "rotation", 0f, 720f).setDuration(1000).start();
        ObjectAnimator.ofFloat(mImage, "alpha", 0f, 1.0f).setDuration(1000).start();*/

        // 方案2，谷歌在PropertyValuesHolder中对动画进行了优化，使得使用多个动画时更有效率，更节省资源
        /*PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofFloat("translationX", 0f, 200f);
        PropertyValuesHolder pvhTranslateY = PropertyValuesHolder.ofFloat("translationY", 0f, 200f);
        PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofFloat("rotation", 0f, 720f);
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1.0f);
        ObjectAnimator
                .ofPropertyValuesHolder(mImage, pvhTranslateX, pvhTranslateY, pvhRotate, pvhAlpha)
                .setDuration(1000).start();*/

        // 方案3
        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(mImage, "translationX", 0f, 200f);
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(mImage, "translationY", 0f, 200f);
        ObjectAnimator animatorRotate = ObjectAnimator.ofFloat(mImage, "rotation", 0f, 720f);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(mImage, "alpha", 0f, 1.0f);

        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(animatorTranslateX, animatorTranslateY, animatorRotate, animatorAlpha);
//        animatorSet.playSequentially(animatorTranslateX, animatorTranslateY, animatorRotate, animatorAlpha);
        animatorSet.play(animatorTranslateX).with(animatorTranslateY);
        animatorSet.play(animatorRotate).after(animatorTranslateX).with(animatorAlpha);
        animatorSet.setDuration(1000).start();
    }

    /**
     * 使用ValueAnimator实现记时器
     * */
    private void timer(View view) {
        final Button button = (Button) view;

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Integer value = (Integer) valueAnimator.getAnimatedValue();
                button.setText("" + value);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                button.setText("timer");
            }
        });
        valueAnimator.start();
    }

    /**
     * 抛物线与自由落体
     * */
    private void parabolaAndDrop() {
        // 方案1
        /*ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 900);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // 与ObjectAnimator不同的是，ValueAnimator需要自己设置属性的更新
                Integer value = (Integer) valueAnimator.getAnimatedValue();
                mImgBall.setTranslationX(value);
                mImgBall.setTranslationY(value * value / 1000);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mImgBall.setTranslationX(0f);
                mImgBall.setTranslationY(0f);
            }
        });
        valueAnimator.start();*/

        // 方案2
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF o, PointF t1) {
                PointF point = new PointF(0f, 0f);
                point.x = 300 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF point = (PointF) valueAnimator.getAnimatedValue();
                mImgBall.setTranslationX(point.x);
                mImgBall.setTranslationY(point.y);
                mImgBall2.setTranslationY(point.y);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mImgBall.setTranslationX(0f);
                mImgBall.setTranslationY(0f);
                mImgBall2.setTranslationY(0f);
            }
        });
        valueAnimator.start();
    }

}
