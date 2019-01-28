package com.yulin.touch.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

public class SkipChildGroup extends FrameLayout {

    private static final String TAG = "houchen-SkipChildGroup";

    public SkipChildGroup(Context context) {
        super(context);
    }

    public SkipChildGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SkipChildGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        super.onDraw(canvas);
    }
    
}
