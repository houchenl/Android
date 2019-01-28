package com.hc.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liulei0905 on 2016/12/5.
 */

public class FontView extends View {

    private Paint mPaint;

    public FontView(Context context) {
        this(context, null);
    }

    public FontView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(24);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint.FontMetrics fm = mPaint.getFontMetrics();
        float top = fm.top;
        float bottom = fm.bottom;
        System.out.println("top: " + top + ", bottom: " + bottom);
    }
}
