package com.yulin.touch.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MyGroup extends ViewGroup {

    private static final String TAG = "houchen-MyGroup";

    public MyGroup(Context context) {
        super(context);
    }

    public MyGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSize = getDefaultSize(0, widthMeasureSpec);
        int heightSize = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, top;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            left = (getMeasuredWidth() - child.getMeasuredWidth()) / 2;
            top = (getMeasuredHeight() - child.getMeasuredHeight()) / 2;

            child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean result = super.dispatchTouchEvent(ev);
//        Log.d(TAG, "dispatchTouchEvent: return " + result);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = super.onInterceptTouchEvent(ev);
//        Log.d(TAG, "onInterceptTouchEvent: return " + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
//        boolean result = true;
        Log.d(TAG, "onTouchEvent: return " + result + ", action " + event.getAction());
        return result;
    }

}
