package com.hc.customview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hc.customview.R;

/**
 * Created by liu_lei on 2017/10/16.
 * 百分比圆环
 */

public class RingPercentView extends View {

    private static final float DEFAULT_START_ANGLE = 90;
    private static final float DEFAULT_SWEEP_ANGLE = 300;
    private static final int DEFAULT_BG_RING_WIDTH = 8;
    private static final int DEFAULT_BG_RING_COLOR = Color.parseColor("#e2e5ec");
    private static final int DEFAULT_PERCENT_RING_WIDTH = 25;
    private static final int DEFAULT_PERCENT_RING_START_COLOR = Color.parseColor("#fb9e09");
    private static final int DEFAULT_PERCENT_RING_END_COLOR = Color.parseColor("#ff6b06");
    private static final int DEFAULT_ANIMATION_DURATION = 2000;
    private static final boolean DEFAULT_IS_SHOW_ANIM = true;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.TRANSPARENT;

    /**
     * 绘制圆环起始角度，默认从正下方开始，沿顺时针方向绘制
     * */
    private float mStartAngle;

    /**
     * 背景圆环宽度
     * */
    private int mBgRingWidth;

    /**
     * 百分比圆环起始颜色
     * */
    private int mPercentRingStartColor;

    /**
     * 百分比圆环终止颜色
     * */
    private int mPercentRingEndColor;

    /**
     * 百分比圆一次宽度
     * */
    private int mPercentRingWidth;

    // 存储圆心位置
    private float mCx, mCy;

    // 圆环区域
    private RectF mRectArc;

    // 默认底层圆环
    private Paint mBgRingPaint;

    // 百分比圆环
    private Paint mPercentPaint;

    // 圆环起终点圆形
    private Paint mCirclePaint;

    // 背景颜色画笔
    private Paint mBackgroundPaint;

    // 当前圆环角度（动画执行之后）
    private float mCurrentSweepAngle = 0f;

    // 背景颜色
    private int mBackgroundColor;

    public RingPercentView(Context context) {
        this(context, null);
    }

    public RingPercentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingPercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RingPercentView, 0, defStyleAttr);

        mStartAngle = a.getFloat(R.styleable.RingPercentView_startAngle, DEFAULT_START_ANGLE);
        float sweepAngle = a.getFloat(R.styleable.RingPercentView_sweepAngle, DEFAULT_SWEEP_ANGLE);    // 绘制圆环角度，一圈共360度

        mBgRingWidth = a.getDimensionPixelSize(R.styleable.RingPercentView_bgRingWidth, DEFAULT_BG_RING_WIDTH);
        int bgRingColor = a.getColor(R.styleable.RingPercentView_bgRingColor, DEFAULT_BG_RING_COLOR);    // 背景圆环颜色

        mPercentRingWidth = a.getDimensionPixelSize(R.styleable.RingPercentView_percentRingWidth, DEFAULT_PERCENT_RING_WIDTH);
        mPercentRingStartColor = a.getColor(R.styleable.RingPercentView_startColor, DEFAULT_PERCENT_RING_START_COLOR);
        mPercentRingEndColor = a.getColor(R.styleable.RingPercentView_endColor, DEFAULT_PERCENT_RING_END_COLOR);

        int animationDuration = a.getInteger(R.styleable.RingPercentView_duration, DEFAULT_ANIMATION_DURATION);
        boolean isShowAnim = a.getBoolean(R.styleable.RingPercentView_showAnim, DEFAULT_IS_SHOW_ANIM);

        mBackgroundColor = a.getColor(R.styleable.RingPercentView_backgroundColor, DEFAULT_BACKGROUND_COLOR);

        a.recycle();

        mBgRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgRingPaint.setColor(bgRingColor);
        mBgRingPaint.setStrokeWidth(mBgRingWidth);
        mBgRingPaint.setStyle(Paint.Style.STROKE);

        mPercentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPercentPaint.setStrokeWidth(mPercentRingWidth);
        mPercentPaint.setStyle(Paint.Style.STROKE);
        mPercentPaint.setStrokeCap(Paint.Cap.ROUND);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(mPercentRingStartColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(mBackgroundColor);
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        if (isShowAnim) {
            /*
            * 为圆环设置动画
            * 圆环角度从0到指定角度旋转
            * */
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, sweepAngle);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mCurrentSweepAngle = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.setDuration(animationDuration);
            valueAnimator.start();
        } else {
            mCurrentSweepAngle = sweepAngle;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.resolveSize(getDesiredWidth(), widthMeasureSpec);
        int height = View.resolveSize(getDesiredHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);

        updateCenterValue(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制背景颜色
        canvas.drawArc(mRectArc, 0, 360, true, mBackgroundPaint);
//        canvas.drawCircle(mCx, mCy, 100, mBackgroundPaint);

        // 绘制默认圆环
        canvas.drawArc(mRectArc, 0, 360, false, mBgRingPaint);

        // 绘制百分比圆环
        drawProgress(canvas);

        // 绘制百分比圆环起始位置，一个圆形
        // 逆时针旋转90度，使坐标系回到初始位置
        float degree = 0 - mStartAngle;
        canvas.rotate(degree, mCx, mCy);
        float cx = (mRectArc.left + mRectArc.right) / 2;
        float cy = mRectArc.bottom;
        canvas.drawCircle(cx, cy, mPercentRingWidth / 2, mCirclePaint);
    }

    private void drawProgress(Canvas canvas) {
        mPercentPaint.setShader(new SweepGradient(mCx, mCy, mPercentRingStartColor, mPercentRingEndColor));

        // 顺时针旋转90，让圆环从正下方开始，按顺时针方向增加
        canvas.rotate(mStartAngle, mCx, mCy);
        canvas.drawArc(mRectArc, 0, mCurrentSweepAngle, false, mPercentPaint);
    }

    private int getDesiredWidth() {
        return getPaddingLeft() + getPaddingRight();
    }

    private int getDesiredHeight() {
        return getPaddingTop() + getPaddingBottom();
    }

    // 获取圆环绘制区域RECT
    private void updateCenterValue(int width, int height) {
        float radius = getRadius(width, height);
        // 圆环宽度，取默认圆环和进度圆环的大者
        float strokeWidth = Math.max(mBgRingWidth, mPercentRingWidth);

        // 确定圆一次绘制区域
        float left = mCx - radius + strokeWidth / 2;
        float top = mCy - radius + strokeWidth / 2;
        float right = mCx + radius - strokeWidth / 2;
        float bottom = mCy + radius - strokeWidth / 2;
        mRectArc = new RectF(left, top, right, bottom);
    }

    /**
     * 获取半径及圆心坐标
     * */
    private float getRadius(int width, int height) {
        float xRadius = (width - getPaddingLeft() - getPaddingRight()) / 2;
        float yRadius = (height - getPaddingTop() - getPaddingBottom()) / 2;

        // 确定圆心位置
        mCx = getPaddingLeft() + xRadius;
        mCy = getPaddingTop() + yRadius;

        // 取小者为半径
        return Math.min(xRadius, yRadius);
    }

}
