package com.hc.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MyGroup extends LinearLayout {

    private static final String TAG = "houchen";

    public MyGroup(Context context) {
        this(context, null);
    }

    public MyGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "MyGroup:onTouchEvent: ");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "MyGroup:onInterceptTouchEvent: ");
        return super.onInterceptTouchEvent(ev);    // 默认返回false，同return false
//        return true;    // 表示将事件进行拦截，并将拦截到的事件交由当前 View 的 onTouchEvent 进行处理；
//        return false;    // 表示将事件放行，当前 View 上的事件会被传递到子 View 上，再由子 View 的 dispatchTouchEvent 来开始这个事件的分发；
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "MyGroup:dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);    // 事件会分发给当前ViewGroup的onInterceptTouchEvent方法消费
//        return true;    // 事件会在当前View当前方法消费，不会继续传递
//        return false;    // 如果当前 View 获取的事件直接来自 Activity，则会将事件返回给 Activity 的 onTouchEvent 进行消费；
                         // 如果当前 View 获取的事件来自外层父控件，则会将事件返回给父 View 的  onTouchEvent 进行消费。
    }

}
