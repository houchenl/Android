package com.hc.chart.layer;

import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class LineLayer extends ChartLayer {

    private List<Float> mLstValues = new ArrayList<Float>();

    private int mMaxCount = 0;

    private float mSpace = 0;

    private float mMinValue = 0;
    private float mMaxValue = 0;

    private float mPosPerValue = 0;

    private int mCurrPos = -1;

    private int mStrokeWidth = 2;

    private int mStartPos = 0;

    private float mFloorValue = Integer.MIN_VALUE;
    private boolean mIsIncludeFloor = true;

    private int mHasDataState = 0;

    private boolean mIsShowShadow = false;
    private int mShadowColor = 0x214690ef;


    @Override
    public RectF prepareBeforeDraw(RectF rect) {
        getPaint().setColor(mColor);
        getPaint().setStrokeWidth(mStrokeWidth);
        getPaint().setAntiAlias(true);
        mLeft = rect.left;
        mRight = rect.right;
        mTop = rect.top;
        mBottom = rect.bottom;
        return new RectF(mLeft, mTop, mRight, mBottom);
    }

    public float[] calMinAndMaxValue() {
        // int size = mLstValues.size();
        int size = mStartPos + mMaxCount > mLstValues.size() ? mLstValues.size() : mStartPos + mMaxCount;
        if (mIsIncludeFloor) {
            boolean isFirst = true;
            for (int i = mStartPos; i < size; i++) {
                float val = mLstValues.get(i);
                if (val < mFloorValue) {
                    continue;
                }
                if (isFirst) {
                    isFirst = false;
                    mMinValue = val;
                    mMaxValue = val;
                } else {
                    if (mMinValue > val) {
                        mMinValue = val;
                    }
                    if (mMaxValue < val) {
                        mMaxValue = val;
                    }
                }
            }
            if (isFirst) {
                return null;
            } else {
                return new float[] {mMinValue, mMaxValue};
            }
        } else {
            boolean isFirst = true;
            for (int i = mStartPos; i < size; i++) {
                float val = mLstValues.get(i);
                if (val <= mFloorValue) {
                    continue;
                }
                if (isFirst) {
                    isFirst = false;
                    mMinValue = val;
                    mMaxValue = val;
                } else {
                    if (mMinValue > val) {
                        mMinValue = val;
                    }
                    if (mMaxValue < val) {
                        mMaxValue = val;
                    }
                }
            }
            if (isFirst) {
                return null;
            } else {
                return new float[] {mMinValue, mMaxValue};
            }
        }
    }

    @Override
    public void doDraw(Canvas canvas) {
        int size = mLstValues.size();
        if (mIsIncludeFloor) {
            int count = 0;

            Path shadowPath = null;
            if (mIsShowShadow) {
                shadowPath = new Path();
                shadowPath.moveTo(pos2X(0), mBottom - mPaddingBottom);
            }

            float lastX = -9999;
            float lastY = -9999;

            for (int i = mStartPos; i < size; i++) {
                int j = 1;
                if (i - mStartPos >= mMaxCount - 1) {
                    break;
                }
                if (mDrawingListener != null) {
                    mDrawingListener.onDrawing(getPaint(), i);
                }
                float x = pos2X(count);
                if (i >= 0 && i < size - 1) {
                    float val1 = mLstValues.get(i);
                    Float val2 = Float.NaN;
                    for (; i + j < mLstValues.size(); j++) {
                        val2 = mLstValues.get(i + j);
                        if (!val2.equals(Float.NaN)) {
                            break;
                        }

                    }

                    if (!val2.equals(Float.NaN)) {
                        i += (j - 1);
                        if (val1 >= mFloorValue && val2.floatValue() >= mFloorValue) {
                            getPaint().setStrokeWidth(mStrokeWidth);
                            getPaint().setColor(mColor);

                            lastX = x + mSpace * j;
                            lastY = value2Y(val2.floatValue());

                            canvas.drawLine(x, value2Y(val1), lastX, lastY, getPaint());

                            if (shadowPath != null) {
                                shadowPath.lineTo(x, value2Y(val1));
                            }
                        }
                    }
                }
                count += j;
            }

            if (mIsShowShadow && shadowPath != null) {
                if (lastX != -9999 && lastY != -9999) {
                    shadowPath.lineTo(lastX, lastY);
                    shadowPath.lineTo(lastX, mBottom - mPaddingBottom);
                }

                if (!shadowPath.isEmpty()) {
                    getPaint().setColor(mShadowColor);
                    getPaint().setStrokeWidth(1);
                    getPaint().setStyle(Style.FILL);
                    shadowPath.close();
                    canvas.drawPath(shadowPath, getPaint());
                }
            }
        } else {
            int count = 0;

            Path shadowPath = null;
            if (mIsShowShadow) {
                shadowPath = new Path();
                shadowPath.moveTo(pos2X(0), mBottom - mPaddingBottom);
            }


            float lastX = -9999;
            float lastY = -9999;

            for (int i = mStartPos; i < size; i++) {
                int j = 1;
                if (i - mStartPos >= mMaxCount - 1) {
                    break;
                }
                if (mDrawingListener != null) {
                    mDrawingListener.onDrawing(getPaint(), i);
                }
                float x = pos2X(count);
                if (i >= 0 && i < size - 1) {
                    float val1 = mLstValues.get(i);
                    Float val2 = Float.NaN;
                    for (; i + j < mLstValues.size(); j++) {
                        val2 = mLstValues.get(i + j);
                        if (!val2.equals(Float.NaN)) {
                            break;
                        }
                    }

                    if (!val2.equals(Float.NaN)) {
                        i += (j - 1);
                        if (val1 > mFloorValue && val2.floatValue() > mFloorValue) {
                            getPaint().setStrokeWidth(mStrokeWidth);
                            lastX = x + mSpace * j;
                            lastY = value2Y(val2.floatValue());

                            canvas.drawLine(x, value2Y(val1), lastX, lastY, getPaint());
                            if (shadowPath != null) {
                                shadowPath.lineTo(x, value2Y(val1));
                            }
                        }
                    }
                }

                count += j;
            }
            if (mIsShowShadow && shadowPath != null) {
                if (lastX != -9999 && lastY != -9999) {
                    shadowPath.lineTo(lastX, lastY);
                    shadowPath.lineTo(lastX, mBottom - mPaddingBottom);
                }

                if (!shadowPath.isEmpty()) {
                    getPaint().setColor(mShadowColor);
                    getPaint().setStrokeWidth(1);
                    getPaint().setStyle(Style.FILL);
                    shadowPath.close();
                    canvas.drawPath(shadowPath, getPaint());
                }
            }
        }
        // if(mCurrPos > -1)
        // {
        // float x = pos2X(mCurrPos);
        // getPaint().setStrokeWidth(mCheckWidth);
        // getPaint().setColor(mCheckColor);
        // canvas.drawLine(x, mTop, x, mBottom, getPaint());
        // }
    }


    public void setShowShadow(boolean b) {
        mIsShowShadow = b;
    }

    public void setShadowColor(int color) {
        mShadowColor = color;
    }

    public void setStrokeWidth(int width) {
        mStrokeWidth = width;
    }

    public void setFloorValue(float val, boolean isInclude) {
        mFloorValue = val;
        mIsIncludeFloor = isInclude;
    }

    private int x2Pos(float x) {
        return (int) ((x - mLeft - mPaddingLeft) / mSpace);
    }

    private float pos2X(int pos) {
        return mLeft + mPaddingLeft + mSpace * pos;
    }

    private float value2Y(float val) {
        return mBottom - mPosPerValue * (val - mMinValue) - mPaddingBottom;
    }

    public PointF getPointByPos(int pos) {
        PointF pointf = new PointF();
        int count = pos - mStartPos < 0 ? 0 : pos - mStartPos;
        pointf.x = pos2X(count);
        float val = getValue(pos);
        pointf.y = value2Y(val);
        return pointf;
    }

    @Override
    public void rePrepareWhenDrawing(RectF rect) {
        float totalWidth = mRight - mLeft - mPaddingLeft - mPaddingRight;
        mSpace = totalWidth / (mMaxCount - 1);

        float totalHeight = mBottom - mTop - mPaddingTop - mPaddingBottom;
        mPosPerValue = totalHeight / (mMaxValue - mMinValue);
    }

    public void setValue(int index, float val) {
        if (index >= 0 && index <= mLstValues.size() - 1) {
            mLstValues.set(index, val);
        } else if (index == mLstValues.size()) {
            addValue(val);
        }
    }

    public void addValue(float val) {
        mLstValues.add(val);
    }

    public float getValue(int pos) {
        return mLstValues.get(pos);
    }

    @Override
    public void moveStartPos(int offset) {
        int newPos = mStartPos + offset;
        setStartPos(newPos);
    }

    public int getStartPos() {
        return mStartPos;
    }

    public void setStartPos(int pos) {
        if (getValueCount() <= 0) {
            mStartPos = 0;
            return;
        }

        if (pos < 0) {
            mStartPos = 0;
        } else if (pos > getValueCount() - mMaxCount) {
            mStartPos = getValueCount() - mMaxCount;
        } else {
            mStartPos = pos;
        }
    }
    @Override
    public void setMaxCount(int count) {
        setMaxCount(count, 1);
    }

    public void setMaxCount(int count, int direction) {
        if (getValueCount() <= 0) {
            mMaxCount = count;
            mStartPos = 0;
        } else {
            if (mHasDataState == 0) {
                mHasDataState = 1;

                mStartPos = 0;
                if (direction == 1 && getValueCount() > count) {
                    mStartPos = getValueCount() - count;
                }
                mMaxCount = count;
            } else {
                if (getValueCount() > count) {
                    if (count < mMaxCount){
                        mStartPos += (mMaxCount - count);
                        if (mStartPos > getValueCount() - count) {
                            mStartPos = getValueCount() - count;
                        }
                    } else if (count > mMaxCount){
                        mStartPos -= (count - mMaxCount);
                        if (mStartPos < 0) {
                            mStartPos = 0;
                        }
                    }
                } else {
                    mStartPos = 0;
                }
                mMaxCount = count;
            }
        }
    }

    public int getValueCount() {
        return mLstValues.size();
    }

    public float getLastValue() {
        if (getValueCount() > 0) {
            return getValue(getValueCount() - 1);
        }
        return 0;
    }

    public float getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(float val) {
        mMaxValue = val;
    }

    public float getMinValue() {
        return mMinValue;
    }

    public void setMinValue(float val) {
        mMinValue = val;
    }

    @Override
    public void resetData() {
        clear();
        mCurrPos = -1;
        mMaxCount = 0;
        mHasDataState = 0;
        mStartPos = 0;
    }

    public void clear() {
        mLstValues.clear();
        mMinValue = 0;
        mMaxValue = 0;
    }

    @Override
    public boolean onActionPress(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (x >= mLeft && x <= mRight && y >= mTop && y <= mBottom) {
            if (mActionListener != null && mLstValues.size() > 0) {
                mCurrPos = x2Pos(event.getX());
                if (mCurrPos < 0) {
                    mCurrPos = 0;
                }

                int realPos = mStartPos + mCurrPos;

                if (realPos > mLstValues.size() - 1) {
                    realPos = mLstValues.size() - 1 >= 0 ? mLstValues.size() - 1 : 0;
                } else if (realPos < 0) {
                    realPos = 0;
                }

                if (mActionListener.onActionDown(realPos)) {
                    repaint();
                }

                return true;
            }
        }
        return false;
    }

    @Override
    public void onActionUp(MotionEvent event) {
        if (mActionListener != null && mLstValues.size() > 0) {
            mCurrPos = -1;
            if (mActionListener.onActionUp()) {
                repaint();
            }

        }
    }

    @Override
    public boolean onActionMove(MotionEvent event) {
        // if (event.getAction() == MotionEvent.ACTION_MOVE && mIsPressed) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (mActionListener != null && mLstValues.size() > 0) {
                mCurrPos = x2Pos(event.getX());
                if (mCurrPos < 0) {
                    mCurrPos = 0;
                }

                int realPos = mStartPos + mCurrPos;

                if (realPos > mLstValues.size() - 1) {
                    realPos = mLstValues.size() - 1 >= 0 ? mLstValues.size() - 1 : 0;
                } else if (realPos < 0) {
                    realPos = 0;
                }

                if (mActionListener.onActionMove(realPos)) {
                    repaint();
                }

                return true;
            }
        }
        return false;
    }
}
