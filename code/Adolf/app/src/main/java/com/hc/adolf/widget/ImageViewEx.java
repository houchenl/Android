package com.hc.adolf.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hc.adolf.util.FontUtil;

/**
 * Created by liulei0905 on 2016/8/4.
 *
 */
public class ImageViewEx extends ImageView {

    private boolean mIsRed;
    private float mRadius;
    private int mRedCircleY = -1;
    private Paint mPaint = new Paint();

    public ImageViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mIsRed) {
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(0xFFF5380A);

            int width = getWidth();

            if (mRedCircleY < 0) {
                canvas.drawCircle(width - mRadius, mRadius * 2, mRadius, mPaint);
            } else {
                canvas.drawCircle(width - mRadius, mRedCircleY + mRadius, mRadius, mPaint);
            }
        }
    }

    public void setRedCircleY(int redCircleY) {
        mRedCircleY = redCircleY;
        invalidate();
    }

    public void setRedDoat(boolean isRed) {
        mIsRed = isRed;

        if (isRed) {
            mPaint.setTextSize(FontUtil.dip2px(getContext(), 15));
            float textHeight = mPaint.descent() - mPaint.ascent();
            mRadius = textHeight / 6;
        }

        invalidate();
    }

}
