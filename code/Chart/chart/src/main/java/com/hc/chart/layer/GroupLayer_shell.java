package com.hc.chart.layer;

import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.hc.chart.widget.ChartView;
import com.hc.chart.widget.ChartView.IGroupLayer;

public class GroupLayer_shell extends ChartLayer implements IGroupLayer {

    private static final int SIDE_LEFT_V = 1;
    public static final int SIDE_MIDDLE_V = 2;
    private static final int SIDE_RIGHT_V = 4;
    private static final int SIDE_TOP_H = 8;
    private static final int SIDE_BOTTOM_H = 16;

    private ChartLayer mContentLayer = null;

    private int mShowSide = 0;
    private int mSideWidth = 0;
    private int mSideColor = 0;

    @Override
    public RectF prepareBeforeDraw(RectF rect) {

        mContentLayer.prepareBeforeDraw(rect);

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

        if (mContentLayer.isShow()) {
            getPaint().setColor(mContentLayer.getBorderColor());
            getPaint().setStrokeWidth(mContentLayer.getBorderWidth());
            getPaint().setStyle(Style.STROKE);

            if (mContentLayer.getIsShowBorder()) {
                canvas.drawRect(mContentLayer.getRectF(), getPaint());
            }

            if (mContentLayer.isShowHGrid()) {
                Path path = new Path();

                int hLineNum = mContentLayer.getHLineCount();
                float height = mContentLayer.getHeight();
                float perHeight = height / (hLineNum + 1);
                float top = mContentLayer.getTop();

                int interval = 4;

                for (int i = 0; i < hLineNum; i++) {
                    top += perHeight;

                    float x_loc = mContentLayer.getLeft();
                    while (x_loc < mContentLayer.getRight()) {
                        path.moveTo(x_loc, top);
                        path.lineTo(x_loc + interval, top);
                        x_loc += (interval * 2);
                    }

                    canvas.drawPath(path, getPaint());
                    path.reset();
                }
            }
            if (mContentLayer.isShowVGrid()) {
                Path path = new Path();
                int vLineNum = mContentLayer.getVLineCount();
                float width = mContentLayer.getWidth();
                float perWidth = width / (vLineNum + 1);
                float x = mContentLayer.getLeft();
                int interval = 4;


                for (int i = 0; i < vLineNum; i++) {
                    x += perWidth;

                    float y_loc = mContentLayer.getTop();

                    while (y_loc < mContentLayer.getBottom()) {
                        path.moveTo(x, y_loc);
                        path.lineTo(x, y_loc + interval);
                        y_loc += (interval * 2);
                    }

                    canvas.drawPath(path, getPaint());
                    path.reset();
                }
            }
            mContentLayer.doDraw(canvas);
        }
    }

    @Override
    public void rePrepareWhenDrawing(RectF rect) {
        mContentLayer.rePrepareWhenDrawing(rect);
    }

    public void layout(float left, float top, float right, float bottom) {
        super.layout(left, top, right, bottom);
        mContentLayer.layout(mContentLayer.getLeft(), top, mContentLayer.getRight(), bottom);
    }

    public void setContenLayer(ChartLayer layer) {
        mContentLayer = layer;
    }

    public void show(boolean isShow) {
        mContentLayer.show(isShow);
    }

    public boolean isShow() {
        return mContentLayer.isShow();
    }

    public boolean onActionPress(MotionEvent event) {
        return mContentLayer.onActionPress(event);
    }

    public void onActionUp(MotionEvent event) {
        mContentLayer.onActionUp(event);
    }

    public boolean onActionMove(MotionEvent event) {
        return mContentLayer.onActionMove(event);
    }

    public boolean onActionDown(MotionEvent event) {
        return mContentLayer.onActionDown(event);
    }

    public void setChartView(ChartView cv) {
        mContentLayer.setChartView(cv);
    }

    @Override
    public ChartLayer getLayerByTag(String tag) {
        if (mContentLayer instanceof IGroupLayer) {
            IGroupLayer layer = (IGroupLayer) mContentLayer;
            ChartLayer l = layer.getLayerByTag(tag);
            if (l != null) {
                return l;
            }
        }
        if (tag.equals(mContentLayer.getTag())) {
            return mContentLayer;
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
