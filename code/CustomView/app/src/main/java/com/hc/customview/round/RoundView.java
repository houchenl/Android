package com.hc.customview.round;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hc.customview.R;

/**
 * Created by liu_lei on 2016/11/4.
 * 实心圆形
 */

public class RoundView extends View {

    private int mRadius;
    private int mCenterX, mCenterY;
    private int mCheckedColor, mUnCheckedColor;

    private Paint mPaint;

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundView, 0, defStyleAttr);
        try {
            int density = (int) context.getResources().getDisplayMetrics().density;
            mRadius = a.getDimensionPixelSize(R.styleable.RoundView_radius, density * 100);
            mCheckedColor = a.getColor(R.styleable.RoundView_checkedColor, Color.WHITE);
            mUnCheckedColor = a.getColor(R.styleable.RoundView_uncheckedColor, Color.WHITE);
        } finally {
            a.recycle();
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mUnCheckedColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.resolveSize(getDesiredWidth(), widthMeasureSpec);
        int height = View.resolveSize(getDesiredHeight(), heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
        updateCenterValue(size / 2, size / 2);
        mRadius = size / 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        updateCenterValue(w / 2, h / 2);
        mRadius = w / 2;
    }

    private int getDesiredWidth() {
        return getPaddingLeft() + 2 * mRadius + getPaddingRight();
    }

    private int getDesiredHeight() {
        return getPaddingTop() + 2 * mRadius + getPaddingBottom();
    }

    private void updateCenterValue(int x, int y) {
        mCenterX = x;
        mCenterY = y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    private boolean isInsideCircle(float x, float y) {
        return Math.pow(x - mCenterX, 2) + Math.pow(y - mCenterY, 2) <= Math.pow(mRadius, 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 这里得到的xy坐标值相对的坐标原点在本view的左上角
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                if (isInsideCircle(x, y))
                    setColor(mCheckedColor);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isInsideCircle(x, y))
                    setColor(mUnCheckedColor);
                break;
            case MotionEvent.ACTION_UP:
                setColor(mUnCheckedColor);
                break;
        }

        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

}
