package com.hc.chart.layer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.hc.chart.widget.ChartView;

public abstract class ChartLayer {
    private Paint mPaint = new Paint();
    protected int mColor = Color.BLACK;
    protected float mLeft = 0;
    protected float mRight = 0;
    protected float mTop = 0;
    protected float mBottom = 0;

    protected float mPaddingLeft = 0;
    protected float mPaddingTop = 0;
    protected float mPaddingRight = 0;
    protected float mPaddingBottom = 0;

    protected int mBorderColor = Color.GRAY;
    protected int mBorderWidth = 1;
    protected boolean mIsShowBorder = false;

    protected float mHeightPercent = 0;

    private ChartView mChartView = null;

    private boolean mIsShowHGrid = false;
    private boolean mIsShowVGrid = false;
    private int mHLineNum = 0;
    private int mVLineNum = 0;
    private boolean mMiddleHLineIsFull = false;

    private boolean mIsShwoHPaddingLine = false;

    private boolean mIgnoreParentPadding = false;

    private boolean mIsShow = true;
    private String mTag = null;

    public Paint getPaint() {
        return mPaint;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public void showHGrid(int hGridNum) {
        mHLineNum = hGridNum;
        mIsShowHGrid = hGridNum > 0;
    }

    public void showVGrid(int vGridNum) {
        mVLineNum = vGridNum;
        mIsShowVGrid = vGridNum > 0;
    }

    public int getHLineCount() {
        return mHLineNum;
    }

    public int getVLineCount() {
        return mVLineNum;
    }

    public boolean isShowHGrid() {
        return mIsShowHGrid;
    }

    public boolean isShowVGrid() {
        return mIsShowVGrid;
    }

    public void setMiddleLineIsFull(boolean bFull) {
        mMiddleHLineIsFull = bFull;
    }

    public boolean getMiddleHLineIsFull() {
        return mMiddleHLineIsFull;
    }

    public void setShowHPaddingLine(boolean bShow) {
        mIsShwoHPaddingLine = bShow;
    }

    public boolean isShowHPaddingLine() {
        return mIsShwoHPaddingLine;
    }

    public void setIgnoreParentPadding(boolean b) {
        mIgnoreParentPadding = b;
    }

    public boolean getIgnoreParentPadding() {
        return mIgnoreParentPadding;
    }

    public abstract RectF prepareBeforeDraw(RectF rect);

    public RectF prepareBeforeDrawFixed(RectF rect) {
        return null;
    }

    public float[] calMinAndMaxValue() {
        return null;
    }

    public abstract void doDraw(Canvas canvas);

    public abstract void rePrepareWhenDrawing(RectF rect);

    public void layout(float left, float top, float right, float bottom) {
        mLeft = left;
        mRight = right;
        mTop = top;
        mBottom = bottom;
    }

    public void setPaddings(float left, float top, float right, float bottom) {
        mPaddingLeft = left;
        mPaddingTop = top;
        mPaddingRight = right;
        mPaddingBottom = bottom;
    }

    public void setShowBorder(boolean b) {
        mIsShowBorder = b;
    }

    public boolean getIsShowBorder() {
        return mIsShowBorder;
    }

    public void setBorderColor(int color) {
        mBorderColor = color;
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderWidth(int width) {
        mBorderWidth = width;
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    public float getLeft() {
        return mLeft;
    }

    public float getTop() {
        return mTop;
    }

    public float getRight() {
        return mRight;
    }

    public float getBottom() {
        return mBottom;
    }

    public float getWidth() {
        return mRight - mLeft;
    }

    public float getHeight() {
        return mBottom - mTop;
    }

    public RectF getRectF() {
        return new RectF(mLeft, mTop, mRight, mBottom);
    }

    public void setHeightPercent(float percent) {
        mHeightPercent = percent;
    }

    public float getHeightPercent() {
        return mHeightPercent;
    }

    public void resetData() {

    }

    public void moveStartPos(int offset) {

    }

    public void setMaxCount(int count) {

    }


    public boolean onActionPress(MotionEvent event) {
        return false;
    }

    public void onActionUp(MotionEvent event) {

    }

    public boolean onActionMove(MotionEvent event) {
        return false;
    }

    public boolean onActionDown(MotionEvent event) {
        return false;
    }

    public boolean onActionDoubleTap(MotionEvent event) {
        return false;
    }

    public boolean onActionSingleTap(MotionEvent event) {
        return false;
    }

    public void setChartView(ChartView view) {
        mChartView = view;
    }

    public void repaint() {
        if (mChartView != null) {
            mChartView.invalidate();
        }
    }

    public void show(boolean isShow) {
        mIsShow = isShow;
    }

    public boolean isShow() {
        return mIsShow;
    }

    protected OnActionListener mActionListener = null;

    public static interface OnActionListener {
        public boolean onActionMove(int pos);

        public boolean onActionUp();

        public boolean onActionDown(int pos);
    }

    public void setOnActionListener(OnActionListener listener) {
        mActionListener = listener;
    }

    protected OnDrawingListener mDrawingListener = null;

    public static interface OnDrawingListener {
        public void onDrawing(Paint paint, int pos);
    }

    public void setOnDrawingListener(OnDrawingListener listener) {
        mDrawingListener = listener;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public String getTag() {
        return mTag;
    }

    public void setMaxValue(float val) {
        
    }

    public void setMinValue(float val) {
        
    }

    public void setStartPos(int pos) {
        
    }

}
