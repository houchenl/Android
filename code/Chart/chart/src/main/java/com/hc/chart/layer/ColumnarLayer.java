package com.hc.chart.layer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 *
 */
public class ColumnarLayer extends ChartLayer {

    private List<ColumnarAtom> mLstValues = new ArrayList<ColumnarAtom>();
    private int mMaxCount = 0;
    private float mColumnarWidth = 0;
    private float mSpace = 0;

    private float mPosPerValue = 0;

    private float mMinValue = 0;
    private float mMaxValue = 0;

    private int mCurrPos = -1;
    private float mStrokeWidth = 1;
    private float mLineWidth = 1;

    private int mLineColor = Color.BLACK;

    private int mStartPos = 0;

    private boolean mIsLine = false;

    private int mHasDataState = 0;

    DrawInfoWriteOutCallBack mWriteOutCallback = null;
    private boolean mNeedPreCalcCoulumnar = false;

    public void setWriteOutCallback(DrawInfoWriteOutCallBack writeOutCallback) {
        this.mWriteOutCallback = writeOutCallback;
    }

    @Override
    public RectF prepareBeforeDraw(RectF rect) {
        mLeft = rect.left + mPaddingLeft;
        mRight = rect.right - mPaddingRight;
        mTop = rect.top + mPaddingTop;
        mBottom = rect.bottom - mPaddingBottom;

        getPaint().setColor(mColor);
        getPaint().setStrokeWidth(mStrokeWidth);
        getPaint().setAntiAlias(true);
        getPaint().setStyle(Style.FILL);

        return new RectF(mLeft, mTop, mRight, mBottom);
    }

    public float[] calMinAndMaxValue() {
        // int size = mLstValues.size();
        int size = mStartPos + mMaxCount > mLstValues.size() ? mLstValues.size() : mStartPos + mMaxCount;
        if (isLine()) {
            for (int i = mStartPos; i < size; i++) {
                ColumnarAtom value = mLstValues.get(i);
                if (i == mStartPos) {
                    mMaxValue = value.mClose;
                    mMinValue = value.mClose;
                } else {
                    if (mMaxValue < value.mClose) {
                        mMaxValue = value.mClose;
                    }
                    if (mMinValue > value.mClose) {
                        mMinValue = value.mClose;
                    }
                }
            }
        } else {
            for (int i = mStartPos; i < size; i++) {
                ColumnarAtom value = mLstValues.get(i);
                if (i == mStartPos) {
                    mMaxValue = value.mHigh;
                    if (mMaxValue < value.mClose) {
                        mMaxValue = value.mClose;
                    }
                    if (mMaxValue < value.mOpen) {
                        mMaxValue = value.mOpen;
                    }
                    if (mMaxValue < value.mLow) {
                        mMaxValue = value.mLow;
                    }

                    mMinValue = value.mLow;
                    if (mMinValue > value.mClose) {
                        mMinValue = value.mClose;
                    }
                    if (mMinValue > value.mOpen) {
                        mMinValue = value.mOpen;
                    }
                    if (mMinValue > value.mHigh) {
                        mMinValue = value.mHigh;
                    }
                } else {
                    if (mMaxValue < value.mHigh) {
                        mMaxValue = value.mHigh;
                    }
                    if (mMaxValue < value.mClose) {
                        mMaxValue = value.mClose;
                    }
                    if (mMaxValue < value.mOpen) {
                        mMaxValue = value.mOpen;
                    }
                    if (mMaxValue < value.mLow) {
                        mMaxValue = value.mLow;
                    }

                    if (mMinValue > value.mLow) {
                        mMinValue = value.mLow;
                    }
                    if (mMinValue > value.mClose) {
                        mMinValue = value.mClose;
                    }
                    if (mMinValue > value.mOpen) {
                        mMinValue = value.mOpen;
                    }
                    if (mMinValue > value.mHigh) {
                        mMinValue = value.mHigh;
                    }
                }
            }
        }
        if (mLstValues.size() == 0) {
            return null;
        } else {
            return new float[] {mMinValue, mMaxValue};
        }
    }

    protected float value2Y(float val) {
        return mBottom - mPosPerValue * (val - mMinValue) - mPaddingBottom;
    }

    @Override
    public void doDraw(Canvas canvas) {
        int count = 0;

        if (mWriteOutCallback != null) {
            mWriteOutCallback.clear();
        }

        for (int i = mStartPos; i < mLstValues.size(); i++) {
            if (i - mStartPos >= mMaxCount) {
                break;
            }

            if (mIsLine) {
                getPaint().setStrokeWidth(mLineWidth);
                getPaint().setColor(mLineColor);
                if (count > 0 && i > 0) {
                    ColumnarAtom a1 = mLstValues.get(i - 1);
                    ColumnarAtom a2 = mLstValues.get(i);
                    canvas.drawLine(pos2X(count - 1), value2Y(a1.mClose), pos2X(count), value2Y(a2.mClose), getPaint());
                }
            } else {
                getPaint().setStrokeWidth(mStrokeWidth);
                if (mDrawingListener != null) {
                    mDrawingListener.onDrawing(getPaint(), i);
                }
                drawOneColumnar(i, pos2X(count), canvas, mLstValues.get(i));
            }
            count++;
        }
    }

    public void setStrokeWidth(int width) {
        mStrokeWidth = width;
    }

    public void setCheckWidth(float width) {
    }

    public void setLineColor(int color) {
        mLineColor = color;
    }

    public void setLineWidth(float width) {
        mLineWidth = width;
    }

    protected float pos2X(int index) {
        return mLeft + mColumnarWidth / 2 + mPaddingLeft + (mColumnarWidth + mSpace) * index;
    }

    protected int x2Pos(float x) {
        return (int) ((x - mLeft - mColumnarWidth / 2 - mPaddingLeft) / (mColumnarWidth + mSpace));
    }

    @Override
    public void rePrepareWhenDrawing(RectF rect) {
        float totalWidth = mRight - mLeft - mColumnarWidth * mMaxCount - mPaddingRight - mPaddingLeft;
        mSpace = totalWidth / (mMaxCount - 1);

        float totalHeight = mBottom - mTop - mPaddingTop - mPaddingBottom;
        mPosPerValue = totalHeight / (mMaxValue - mMinValue);

        if (mNeedPreCalcCoulumnar) {
            preCalcColumnar();
        }

    }

    protected void preCalcColumnar() {
        if (mWriteOutCallback != null) {
            mWriteOutCallback.clear();

            int count = 0;

            for (int i = mStartPos; i < mLstValues.size(); i++) {
                if (i - mStartPos >= mMaxCount) {
                    break;
                }

                if (!mIsLine) {
                    getPaint().setStrokeWidth(mStrokeWidth);
                    if (mDrawingListener != null) {
                        mDrawingListener.onDrawing(getPaint(), i);
                    }

                    mWriteOutCallback.out(pos2X(count), value2Y(mLstValues.get(i).mHigh), value2Y(mLstValues.get(i).mLow), (Integer) mLstValues.get(i).mTag, mColumnarWidth);
                }
                count++;
            }
        }

    }

    protected void drawOneColumnar(int pos, float centerX, Canvas canvas, ColumnarAtom value) {
        float left = centerX - mColumnarWidth / 2;
        float right = centerX + mColumnarWidth / 2;
        if (value.mOpen > value.mClose) {
            if (value.mHigh != value.mOpen) {
                canvas.drawLine(centerX, value2Y(value.mHigh), centerX, value2Y(value.mOpen), getPaint());
            }
            if (value.mClose != value.mLow) {
                canvas.drawLine(centerX, value2Y(value.mClose), centerX, value2Y(value.mLow), getPaint());
            }
            float openY = value2Y(value.mOpen);
            float closeY = value2Y(value.mClose);
            canvas.drawRect(new RectF(left, openY, right, closeY), getPaint());
            if (closeY - openY <= mStrokeWidth) {
                canvas.drawLine(left, closeY, right, closeY, getPaint());
            }
        } else if (value.mOpen < value.mClose) {
            if (value.mHigh != value.mClose) {
                canvas.drawLine(centerX, value2Y(value.mHigh), centerX, value2Y(value.mClose), getPaint());
            }
            if (value.mOpen != value.mLow) {
                canvas.drawLine(centerX, value2Y(value.mOpen), centerX, value2Y(value.mLow), getPaint());
            }
            canvas.drawRect(new RectF(left, value2Y(value.mClose), right, value2Y(value.mOpen)), getPaint());
            float openY = value2Y(value.mOpen);
            float closeY = value2Y(value.mClose);
            if (openY - closeY <= mStrokeWidth) {
                canvas.drawLine(left, closeY, right, closeY, getPaint());
            }
        } else {

            if (value.mHigh != value.mLow) {
                canvas.drawLine(centerX, value2Y(value.mHigh), centerX, value2Y(value.mLow), getPaint());
            }
            canvas.drawLine(left, value2Y(value.mClose), right, value2Y(value.mClose), getPaint());
        }

        if (mWriteOutCallback != null) {
            mWriteOutCallback.out(centerX, value2Y(value.mHigh), value2Y(value.mLow), (Integer) value.mTag, mColumnarWidth);
        }
    }

    public void setValue(int index, ColumnarAtom value) {
        if (index >= 0 && index <= mLstValues.size() - 1) {
            mLstValues.set(index, value);
        } else if (index == mLstValues.size()) {
            addValue(value);
        }
    }

    public void addValue(ColumnarAtom value) {
        mLstValues.add(value);
    }

    public ColumnarAtom getValue(int pos) {
        return mLstValues.get(pos);
    }

    public int getValueCount() {
        return mLstValues.size();
    }

    public int getDisplayValueCount() {
        int count = getValueCount() - mStartPos < mMaxCount ? getValueCount() - mStartPos : mMaxCount;
        return count > 0 ? count : 0;
    }

    public ColumnarAtom getLastValue() {
        if (getValueCount() > 0) {
            return getValue(getValueCount() - 1);
        }
        return null;
    }

    public ColumnarAtom getDisplayLastValue(int offset) {
        if (offset > 0) {
            return null;
        }

        if (getDisplayValueCount() > 0) {
            return getValue(mStartPos + getDisplayValueCount() - 1 + offset);
        }
        return null;
    }

    public ColumnarAtom getDisplayLastValue() {
        return getDisplayLastValue(0);
    }

    public ColumnarAtom getFirstValue() {
        if (getValueCount() > 0) {
            return getValue(0);
        }
        return null;
    }

    public ColumnarAtom getDisplayFirstValue() {
        if (getValueCount() > mStartPos) {
            return getValue(mStartPos);
        }
        return null;
    }

    /**
     * 
     * @param pos
     */
    public PointF getPointByPos(int pos) {
        PointF pointf = new PointF();
        int count = pos - mStartPos < 0 ? 0 : pos - mStartPos;
        pointf.x = pos2X(count);
        pointf.y = 0;
        return pointf;
    }

    @Override
    public void moveStartPos(int offset) {
        int newPos = mStartPos + offset;
        setStartPos(newPos);
    }

    public int getStartPos() {
        return mStartPos;
    }

    
    @Override
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

    @Override
    public void setMaxCount(int count) {
        setMaxCount(count, 1);
    }

    public float getMaxValue() {
        return mMaxValue;
    }

    public float getMinValue() {
        return mMinValue;
    }

    @Override
    public void resetData() {
        clear();
        mCurrPos = -1;
        mIsLine = false;
        mMaxCount = 0;
        mHasDataState = 0;
        mStartPos = 0;
    }

    public void clear() {
        mLstValues.clear();
        mMinValue = 0;
        mMaxValue = 0;
    }

    public void setColumnarWidth(float width) {
        mColumnarWidth = width;
    }

    public static class ColumnarAtom {
        public float mOpen = 0;
        public float mHigh = 0;
        public float mClose = 0;
        public float mLow = 0;
        public Object mTag = null;

        public ColumnarAtom() {

        }

        public ColumnarAtom(float open, float high, float close, float low) {
            mOpen = open;
            mHigh = high;
            mClose = close;
            mLow = low;
        }

        public ColumnarAtom(float close) {
            mClose = close;
        }
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
            }
            return true;
        }
        return false;
    }

    public void setNeedPreCalc(boolean b) {
        mNeedPreCalcCoulumnar = b;
    }

    @Override
    public void setMaxValue(float val) {
        mMaxValue = val;
    }

    @Override
    public void setMinValue(float val) {
        mMinValue = val;
    }

    public void change2Line(boolean flag) {
        mIsLine = flag;
    }

    public boolean isLine() {
        return mIsLine;
    }

    public float getColumnarWidth() {
        return mColumnarWidth;
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
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onActionDoubleTap(MotionEvent event) {
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
                // onActionDoubleDown
                // mActionListener.onActionDown(realPos);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onActionDown(MotionEvent event) {
        return true;
    }

    public interface DrawInfoWriteOutCallBack {
        public void out(float centerX, float topY, float bottomY, int time, float columWidth);

        public void clear();
    }
}
