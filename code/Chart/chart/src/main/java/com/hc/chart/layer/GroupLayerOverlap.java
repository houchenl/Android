package com.hc.chart.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.hc.chart.widget.ChartView;
import com.hc.chart.widget.ChartView.IGroupLayer;

public class GroupLayerOverlap extends ChartLayer implements IGroupLayer {

    private static final int SIDE_LEFT_V = 1;
    private static final int SIDE_MIDDLE_V = 2;
    private static final int SIDE_RIGHT_V = 4;
    private static final int SIDE_TOP_H = 8;
    private static final int SIDE_BOTTOM_H = 16;

    private ChartLayer mLeftLayer = null;
    private ChartLayer mRightLayer = null;

    private int mShowSide = 0;
    private int mSideWidth = 0;
    private int mSideColor = 0;

    private Bitmap mBitmapAvgLineIdentfy = null;
    private boolean mIsAvgLineIdentifyShow = false;

    @Override
    public RectF prepareBeforeDraw(RectF rect) {

        mLeftLayer.prepareBeforeDraw(rect);
        mRightLayer.prepareBeforeDraw(rect);

        mLeft = rect.left;
        mTop = rect.top;
        mRight = rect.right;
        mBottom = rect.bottom;
        return rect;
    }

    @Override
    public void doDraw(Canvas canvas) {
        if ((mShowSide & SIDE_TOP_H) == SIDE_TOP_H) {
            getPaint().setColor(mSideColor);
            getPaint().setStrokeWidth(mSideWidth);
            getPaint().setStyle(Style.STROKE);

            canvas.drawLine(mLeft, mTop, mRight, mTop, getPaint());
        }

        if ((mShowSide & SIDE_BOTTOM_H) == SIDE_BOTTOM_H) {
            getPaint().setColor(mSideColor);
            getPaint().setStrokeWidth(mSideWidth);
            getPaint().setStyle(Style.STROKE);

            canvas.drawLine(mLeft, mBottom, mRight, mBottom, getPaint());
        }

        if ((mShowSide & SIDE_LEFT_V) == SIDE_LEFT_V) {
            getPaint().setColor(mSideColor);
            getPaint().setStrokeWidth(mSideWidth);
            getPaint().setStyle(Style.STROKE);

            canvas.drawLine(mLeft, mTop, mLeft, mBottom, getPaint());
        }

        if ((mShowSide & SIDE_RIGHT_V) == SIDE_RIGHT_V) {
            getPaint().setColor(mSideColor);
            getPaint().setStrokeWidth(mSideWidth);
            getPaint().setStyle(Style.STROKE);

            canvas.drawLine(mRight, mTop, mRight, mBottom, getPaint());
        }


        if (mIsAvgLineIdentifyShow && mBitmapAvgLineIdentfy != null) {
            float tStartX = mLeft;
            
            canvas.drawBitmap(mBitmapAvgLineIdentfy, tStartX, mTop + 3, getPaint());
        }


        if (mRightLayer.isShow()) {
            getPaint().setColor(mRightLayer.getBorderColor());
            getPaint().setStrokeWidth(mRightLayer.getBorderWidth());
            getPaint().setStyle(Style.STROKE);

            if (mRightLayer.getIsShowBorder()) {
                canvas.drawRect(mRightLayer.getRectF(), getPaint());
            }

            if (mRightLayer.isShowHPaddingLine()) {
                if (mRightLayer.mPaddingTop > 0) {
                    Path path = new Path();
                    float top = mRightLayer.getTop() + mRightLayer.mPaddingTop;
                    int interval = 4;

                    float x_loc = mRightLayer.getLeft();
                    while (x_loc < mRightLayer.getRight()) {
                        path.moveTo(x_loc, top);
                        path.lineTo(x_loc + interval, top);
                        x_loc += (interval * 2);
                    }

                    canvas.drawPath(path, getPaint());
                    path.reset();
                }

                if (mRightLayer.mPaddingBottom > 0) {
                    Path path = new Path();
                    float top = mRightLayer.getBottom() - mRightLayer.mPaddingBottom;
                    int interval = 4;

                    float x_loc = mRightLayer.getLeft();
                    while (x_loc < mRightLayer.getRight()) {
                        path.moveTo(x_loc, top);
                        path.lineTo(x_loc + interval, top);
                        x_loc += (interval * 2);
                    }

                    canvas.drawPath(path, getPaint());
                    path.reset();
                }
            }

            if (mRightLayer.isShowHGrid()) {
                Path path = new Path();

                int hLineNum = mRightLayer.getHLineCount();
                float height = mRightLayer.getHeight() - mRightLayer.mPaddingBottom - mRightLayer.mPaddingTop;
                float perHeight = height / (hLineNum + 1);
                float top = mRightLayer.getTop() + mRightLayer.mPaddingTop;

                int interval = 4;

                for (int i = 0; i < hLineNum; i++) {
                    top += perHeight;

                    float x_loc = mRightLayer.getLeft();
                    while (x_loc < mRightLayer.getRight()) {
                        path.moveTo(x_loc, top);
                        path.lineTo(x_loc + interval, top);
                        x_loc += (interval * 2);
                    }

                    canvas.drawPath(path, getPaint());
                    path.reset();
                }
            }

            if (mRightLayer.isShowVGrid()) {
                Path path = new Path();
                int vLineNum = mRightLayer.getVLineCount();
                float width = mRightLayer.getWidth();
                float perWidth = width / (vLineNum + 1);
                float x = mRightLayer.getLeft();
                int interval = 4;



                for (int i = 0; i < vLineNum; i++) {
                    x += perWidth;

                    float y_loc = mRightLayer.getTop();

                    while (y_loc < mRightLayer.getBottom()) {
                        path.moveTo(x, y_loc);
                        path.lineTo(x, y_loc + interval);
                        y_loc += (interval * 2);
                    }

                    canvas.drawPath(path, getPaint());
                    path.reset();
                }
            }
            mRightLayer.doDraw(canvas);
        }
        
        if (mLeftLayer.isShow()) {
            getPaint().setColor(mLeftLayer.getBorderColor());
            getPaint().setStrokeWidth(mLeftLayer.getBorderWidth());
            getPaint().setStyle(Style.STROKE);

            if (mLeftLayer.getIsShowBorder()) {
                canvas.drawRect(mLeftLayer.getRectF(), getPaint());
            }

            if ((mShowSide & SIDE_MIDDLE_V) == SIDE_MIDDLE_V) {
                getPaint().setColor(mSideColor);
                getPaint().setStrokeWidth(mSideWidth);
                getPaint().setStyle(Style.STROKE);

                canvas.drawLine(mLeftLayer.mRight, mLeftLayer.mTop, mLeftLayer.mRight, mLeftLayer.mBottom, getPaint());
            }

            if (mLeftLayer.isShowHGrid()) {
                Path path = new Path();

                int hLineNum = mLeftLayer.getHLineCount();
                float height = mLeftLayer.getHeight();
                float perHeight = height / (hLineNum + 1);
                float top = mLeftLayer.getTop();

                int interval = 5;

                for (int i = 0; i < hLineNum; i++) {
                    top += perHeight;

                    float x_loc = mLeftLayer.getLeft();
                    while (x_loc < mLeftLayer.getRight()) {
                        path.moveTo(x_loc, top);
                        path.lineTo(x_loc + interval, top);
                        x_loc += (interval * 2);
                    }

                    canvas.drawPath(path, getPaint());
                    path.reset();
                }
            }
            if (mLeftLayer.isShowVGrid()) {
                int vLineNum = mLeftLayer.getVLineCount();
                float width = mLeftLayer.getWidth();
                float perWidth = width / (vLineNum + 1);
                float x = mLeftLayer.getLeft();
                for (int i = 0; i < vLineNum; i++) {
                    x += perWidth;
                    canvas.drawLine(x, mLeftLayer.getTop(), x, mLeftLayer.getBottom(), getPaint());
                }
            }
            mLeftLayer.doDraw(canvas);
        }
    }

    @Override
    public void rePrepareWhenDrawing(RectF rect) {
        mLeftLayer.rePrepareWhenDrawing(rect);
        mRightLayer.rePrepareWhenDrawing(rect);
    }

    public void layout(float left, float top, float right, float bottom) {
        super.layout(left, top, right, bottom);
        mLeftLayer.layout(mLeftLayer.getLeft(), top, mLeftLayer.getRight(), bottom);
        mRightLayer.layout(mRightLayer.getLeft(), top, mRightLayer.getRight(), bottom);
    }

    public void setAvgLineBitmap(Bitmap bitmap) {
        mBitmapAvgLineIdentfy = bitmap;
    }

    public void switchAvgLineIdentifyOn(boolean isOn) {
        mIsAvgLineIdentifyShow = isOn;
    }

    public void setLeftLayer(ChartLayer layer) {
        mLeftLayer = layer;
    }

    public void setRightLayer(ChartLayer layer) {
        mRightLayer = layer;
    }

    public void show(boolean isShow) {
        mLeftLayer.show(isShow);
        mRightLayer.show(isShow);
    }

    public boolean isShow() {
        return mRightLayer.isShow() || mLeftLayer.isShow();
    }

    public boolean onActionPress(MotionEvent event) {
        return mLeftLayer.onActionPress(event) || mRightLayer.onActionPress(event);
    }

    public void onActionUp(MotionEvent event) {
        mLeftLayer.onActionUp(event);
        mRightLayer.onActionUp(event);
    }

    public boolean onActionMove(MotionEvent event) {
        return mLeftLayer.onActionMove(event) || mRightLayer.onActionMove(event);
    }

    public boolean onActionDown(MotionEvent event) {
        return mLeftLayer.onActionDown(event) || mRightLayer.onActionDown(event);
    }

    public void setChartView(ChartView cv) {
        mLeftLayer.setChartView(cv);
        mRightLayer.setChartView(cv);
    }

    @Override
    public ChartLayer getLayerByTag(String tag) {
        if (mLeftLayer instanceof IGroupLayer) {
            IGroupLayer layer = (IGroupLayer) mLeftLayer;
            ChartLayer l = layer.getLayerByTag(tag);
            if (l != null) {
                return l;
            }
        }
        if (tag.equals(mLeftLayer.getTag())) {
            return mLeftLayer;
        }
        if (mRightLayer instanceof IGroupLayer) {
            IGroupLayer layer = (IGroupLayer) mRightLayer;
            ChartLayer l = layer.getLayerByTag(tag);
            if (l != null) {
                return l;
            }
        }
        if (tag.equals(mRightLayer.getTag())) {
            return mRightLayer;
        }
        return null;
    }

    public void setSideColor(int color) {
        mSideColor = color;
    }

    public void setSideWidth(int width) {
        mSideWidth = width;
    }

    public void setShowSide(int side) {
        mShowSide = side;
    }
}
