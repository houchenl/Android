package com.hc.customview.fixed;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

public class FixedItemView extends View {

    private Paint mPaint;
    private String mText = "Hello";
    private int windowWidth = 0;
    private int paramWidth = 0;

    public FixedItemView(Context context) {
        this(context, null);
    }

    public FixedItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FixedItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(30);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowWidth = wm.getDefaultDisplay().getWidth();
        paramWidth = windowWidth / 4;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(paramWidth * 10, 200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.RED);
        Rect rect = new Rect(0, 0, paramWidth * 10, 200);
        canvas.drawRect(rect, mPaint);

        int width = getMeasuredWidth();
        int startPos = 0;
        int y = 100;

        mPaint.setColor(Color.BLUE);
        while (startPos < width) {
            canvas.drawText(mText + startPos, startPos, y, mPaint);
            startPos += paramWidth;
        }


    }

}
