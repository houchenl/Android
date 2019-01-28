package com.yulin.touch.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

    private static final String TAG = "houchen-MyView";

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.resolveSize(0, widthMeasureSpec);
        int height = View.resolveSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean result = super.dispatchTouchEvent(event);
//        Log.d(TAG, "dispatchTouchEvent: return " + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 1. onTouchEvent方法，只有在没有为view设置onTouchListener方法，或设置的onTouch方法返回false时才会被调用。
        // 2. super.onTouchEvent(event)会检查，如果设置了onClick事件就调用onClick方法，如果不在onTouchEvent中
        // 调用super.onTouchEvent(event)方法，即使为view设置了onclick事件，也不会调用onClick方法。
        // 3. 返回true表示处理事件，返回false表示不处理事件，如果返回false，当前view就不会再接受到同一系列的后续事件。
        boolean result = super.onTouchEvent(event);
//        boolean result = true;

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                result = false;
                break;
            case MotionEvent.ACTION_MOVE:
                result = true;
                break;
            case MotionEvent.ACTION_UP:
                result = true;
                break;
        }

        Log.d(TAG, "onTouchEvent: return " + result + ", action " + event.getAction());
        return result;
    }

}
