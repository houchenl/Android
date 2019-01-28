package com.hc.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

public class MyView extends TextView {

    private static final String TAG = "houchen";

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "MyView:dispatchTouchEvent: ");
        return super.dispatchTouchEvent(event);    // 事件分发给当前View的onTouchEvent消费
//        return true;    // 事件会在当前View当前方法消费，不会继续传递
//        return false;    // 如果当前 View 获取的事件直接来自 Activity，则会将事件返回给 Activity 的 onTouchEvent 进行消费；
                         // 如果当前 View 获取的事件来自外层父控件，则会将事件返回给父 View 的  onTouchEvent 进行消费。
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "MyView:onTouchEvent: ");
        return super.onTouchEvent(event);
    }

}
