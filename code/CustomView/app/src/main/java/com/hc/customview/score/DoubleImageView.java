package com.hc.customview.score;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hc.customview.R;

/**
 * 显示两张重叠显示的图片logo，并且有一个文本上下居中显示在它们右边。
 * 可以用来显示体育比赛的比分。
 * */
public class DoubleImageView extends View {

    private static final String TAG = "DoubleImageView";

    /* Image Contents */
    private Drawable mLeftDrawable, mRightDrawable;

    /* Text Contents */
    private CharSequence mText;
    private StaticLayout mTextLayout;

    /* Text Drawing */
    private TextPaint mTextPaint;
    private Point mTextOrigin;
    private int mSpacing;

    public DoubleImageView(Context context) {
        this(context, null);
    }

    public DoubleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     * */
    private void init(Context context, AttributeSet attrs, int style) {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextOrigin = new Point(0, 0);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DoubleImageView, 0, style);

        Drawable d = a.getDrawable(R.styleable.DoubleImageView_android_drawableLeft);
        if (d != null)
            setLeftDrawable(d);

        d = a.getDrawable(R.styleable.DoubleImageView_android_drawableRight);
        if (d != null)
            setRightDrawable(d);

        int spacing = a.getDimensionPixelSize(R.styleable.DoubleImageView_android_spacing, 0);
        setSpacing(spacing);

        int color = a.getColor(R.styleable.DoubleImageView_android_textColor, 0);
        mTextPaint.setColor(color);

        int rawSize = a.getDimensionPixelSize(R.styleable.DoubleImageView_android_textSize, 0);
        mTextPaint.setTextSize(rawSize);

        CharSequence text = a.getText(R.styleable.DoubleImageView_android_text);
        setText(text);

        a.recycle();
    }

    public void setLeftDrawableResource(int resId) {
        Drawable d = getResources().getDrawable(resId);
        setLeftDrawable(d);
    }

    public void setLeftDrawable(Drawable left) {
        mLeftDrawable = left;
        updateContentBounds();
        invalidate();
    }

    public void setRightDrawableResource(int resId) {
        Drawable d = getResources().getDrawable(resId);
        setRightDrawable(d);
    }

    public void setRightDrawable(Drawable right) {
        mRightDrawable = right;
        updateContentBounds();
        invalidate();
    }

    public void setSpacing(int spacing) {
        mSpacing = spacing;
        updateContentBounds();
        invalidate();
    }

    public void setText(int resId) {
        CharSequence text = getResources().getText(resId);
        setText(text);
    }

    public void setText(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            mText = text;
            updateContentBounds();
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: ");

        // Get the width measurement
        int widthSize = View.resolveSize(getDesiredWidth(), widthMeasureSpec);

        // Get the height measurement
        int heightSize = View.resolveSize(getDesiredHeight(), heightMeasureSpec);

        // Must call this to store the measurements
        setMeasuredDimension(widthSize, heightSize);
    }

    private int getDesiredWidth() {
        int leftWidth;
        if (mLeftDrawable == null) {
            leftWidth = 0;
        } else {
            leftWidth = mLeftDrawable.getIntrinsicWidth();
        }

        int rightWidth;
        if (mRightDrawable == null) {
            rightWidth = 0;
        } else {
            rightWidth = mRightDrawable.getIntrinsicWidth();
        }

        int textWidth;
        if (mTextLayout == null) {
            textWidth = 0;
        } else {
            textWidth = mTextLayout.getWidth();
        }

        return (int)(leftWidth * 0.67f) + (int)(rightWidth * 0.67f) + mSpacing + textWidth;
    }

    private int getDesiredHeight() {
        int leftHeight;
        if (mLeftDrawable == null) {
            leftHeight = 0;
        } else {
            leftHeight = mLeftDrawable.getIntrinsicHeight();
        }

        int rightHeight;
        if (mRightDrawable == null) {
            rightHeight = 0;
        } else {
            rightHeight = mRightDrawable.getIntrinsicHeight();
        }

        return (int)(leftHeight * 0.67f) + (int)(rightHeight * 0.67f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        /*
        * Android doesn't know the real size at start, it needs to calculate it.
        * Once it's done, onSizeChanged() will notify you with the real size.
        * onSizeChanged() is called once the size as been calculated.
        * so you can set bounds when onSizeChanged() is called.
        * */
        Log.d(TAG, "onSizeChanged: ");

        if (w != oldw || h != oldh)
            updateContentBounds();
    }

    private void updateContentBounds() {
        Log.d(TAG, "updateContentBounds: width is " + getWidth() + ", height is " + getHeight());

        if (mText == null) {
            mText = "";
        }
        float textWidth = mTextPaint.measureText(mText, 0, mText.length());
        mTextLayout = new StaticLayout(mText, mTextPaint, (int)textWidth,
                Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);

        // getWidth() and getHeight() return 0 before onMeasure() is called.
        int left = (getWidth() - getDesiredWidth()) / 2;
        int top = (getHeight() - getDesiredHeight()) / 2;

        if (mLeftDrawable != null) {
            mLeftDrawable.setBounds(left, top, left + mLeftDrawable.getIntrinsicWidth(),
                    top + mLeftDrawable.getIntrinsicHeight());

            left += mLeftDrawable.getIntrinsicWidth() * 0.33f;
            top += mLeftDrawable.getIntrinsicHeight() * 0.33f;
        }

        if (mRightDrawable != null) {
            mRightDrawable.setBounds(left, top, left + mRightDrawable.getIntrinsicWidth(),
                    top + mRightDrawable.getIntrinsicHeight());

            left = mRightDrawable.getBounds().right + mSpacing;
        }

        if (mTextLayout != null) {
            top = (getHeight() - mTextLayout.getHeight()) / 2;
            mTextOrigin.set(left, top);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        
        if (mLeftDrawable != null) {
            mLeftDrawable.draw(canvas);
        }

        if (mTextLayout != null) {
            canvas.save();
            canvas.translate(mTextOrigin.x, mTextOrigin.y);

            mTextLayout.draw(canvas);

            canvas.restore();
        }

        if (mRightDrawable != null) {
            mRightDrawable.draw(canvas);
        }
    }

}
