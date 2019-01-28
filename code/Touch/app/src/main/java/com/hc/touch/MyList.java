package com.hc.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class MyList extends ListView {

    private int currentY;

    private static final String TAG = "houchen";

    public MyList(Context context) {
        super(context);
    }

    public MyList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "MyList:dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "MyList:onInterceptTouchEvent: ");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "MyList:onTouchEvent: ");

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentY = (int) ev.getY();
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "MyList:onTouchEvent: currentY: " + currentY + ", moveY: " + (int) ev.getY() + ", isTop: " + isListViewReachTopEdge());
                if (currentY < (int) ev.getY()) {
                    // 手指向下滑动
                    if (isListViewReachTopEdge()) {
                        return false;
                    }
                } else {
                    if (isListViewReachBottomEdge()) {
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return super.onTouchEvent(ev);
    }

    private boolean isListViewReachTopEdge() {
        boolean result = false;
        if (getFirstVisiblePosition() == 0) {
            View topChildView = getChildAt(0);
            int top = (topChildView == null) ? 0 : topChildView.getTop();
            result = top == 0;
        }
        return result;
    }

    //ListView已到底部的判断
    private boolean isListViewReachBottomEdge() {
        boolean result = false;
        if (getLastVisiblePosition() == (getCount() - 1)) {
            final View bottomChildView = getChildAt(getLastVisiblePosition() - getFirstVisiblePosition());

            result = (getHeight() >= (bottomChildView == null ? 0 : bottomChildView.getBottom()));
        }
        return result;
    }

}
