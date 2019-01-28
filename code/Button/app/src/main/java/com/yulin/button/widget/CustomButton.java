package com.yulin.button.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.yulin.button.R;

/**
 * Created by liu lei on 2017/5/15.
 * <p>
 * 可配置3个属性：
 * radius: 4个角的弯曲度，默认为2dp
 * normalBgColor: 正常状态下背景色，默认为#999999
 * pressBgColor: 按压状态下背景色，默认为#666666
 */

public class CustomButton extends AppCompatButton {

    private static final int DEFAULT_RADIUS = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, new DisplayMetrics());
    private static final int NORMAL_BG_COLOR = Color.parseColor("#999999");
    private static final int PRESS_BG_COLOR = Color.parseColor("#666666");

    private int mRadius;             // 弯曲度
    private int mNormalBgColor;      // 正常状态下背景色
    private int mPressBgColor;       // 按压状态下背景色

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CustomButton, 0, 0);

        try {
            mNormalBgColor = typedArray.getColor(R.styleable.CustomButton_normalBgColor, NORMAL_BG_COLOR);
            mPressBgColor = typedArray.getColor(R.styleable.CustomButton_pressBgColor, PRESS_BG_COLOR);
            mRadius = typedArray.getDimensionPixelSize(R.styleable.CustomButton_radius, DEFAULT_RADIUS);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // normal drawable
        GradientDrawable normalBgDrawable = new GradientDrawable();
        normalBgDrawable.setColor(mNormalBgColor);
        normalBgDrawable.setCornerRadius(mRadius);

        // press drawable
        GradientDrawable pressBgDrawable = new GradientDrawable();
        pressBgDrawable.setColor(mPressBgColor);
        pressBgDrawable.setCornerRadius(mRadius);

        // state list drawable
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, normalBgDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressBgDrawable);
        stateListDrawable.addState(new int[]{}, normalBgDrawable);

        if (Build.VERSION.SDK_INT >= 16) {
            setBackground(stateListDrawable);
        } else {
            setBackgroundDrawable(stateListDrawable);
        }
    }

}
