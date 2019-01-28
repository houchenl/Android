package com.hc.chart.layer;

import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.hc.chart.widget.ChartView;
import com.hc.chart.widget.ChartView.IGroupLayer;

public class GroupLayerOverlap_count3 extends ChartLayer implements IGroupLayer {

    private ChartLayer mLeftLayer = null;
    private ChartLayer mCenterLayer = null;
    private ChartLayer mRightLayer = null;

    @Override
    public RectF prepareBeforeDraw(RectF rect) {
        mLeftLayer.prepareBeforeDraw(rect);
        mRightLayer.prepareBeforeDraw(rect);
        RectF centerRectF = new RectF(rect);
        mCenterLayer.prepareBeforeDraw(centerRectF);

        mLeft = rect.left;
        mTop = rect.top;
        mRight = rect.right;
        mBottom = rect.bottom;

        return rect;
    }

    @Override
    public void doDraw(Canvas canvas) {

        
        if (mCenterLayer.isShow()) {
            getPaint().setColor(mCenterLayer.getBorderColor());
            getPaint().setStrokeWidth(mCenterLayer.getBorderWidth());
            getPaint().setStyle(Style.STROKE);

            if (mCenterLayer.getIsShowBorder()) {
                canvas.drawRect(mCenterLayer.getRectF(), getPaint());
            }

            if (mCenterLayer.isShowHGrid()) {
                Path path = new Path();

                int hLineNum = mCenterLayer.getHLineCount();
                float height = mCenterLayer.getHeight();
                float perHeight = height / (hLineNum + 1);
                float top = mCenterLayer.getTop();

                int middleIndex = -1;
                if (mCenterLayer.getMiddleHLineIsFull()) {
                    if (hLineNum >= 3 && hLineNum % 2 == 1) {
                        middleIndex = hLineNum / 2;
                    }
                }

                int interval = 4;

                for (int i = 0; i < hLineNum; i++) {
                    top += perHeight;

                    float x_loc = mCenterLayer.getLeft();

                    if (middleIndex == i) {
                        canvas.drawLine(x_loc, top, mCenterLayer.getRight(), top, getPaint());
                    } else {
                        while (x_loc < mCenterLayer.getRight()) {
                            path.moveTo(x_loc, top);
                            path.lineTo(x_loc + interval, top);
                            x_loc += (interval * 2);
                        }

                        canvas.drawPath(path, getPaint());
                        path.reset();
                    }

                }
            }
            if (mCenterLayer.isShowVGrid()) {

                Path path = new Path();
                int vLineNum = mCenterLayer.getVLineCount();
                float width = mCenterLayer.getWidth();
                float perWidth = width / (vLineNum + 1);
                float x = mCenterLayer.getLeft();

                int interval = 4;
                for (int i = 0; i < vLineNum; i++) {
                    x += perWidth;

                    float y_loc = mCenterLayer.getTop();

                    while (y_loc < mCenterLayer.getBottom()) {
                        path.moveTo(x, y_loc);
                        path.lineTo(x, y_loc + interval);
                        y_loc += (interval * 2);
                    }

                    canvas.drawPath(path, getPaint());
                    path.reset();
                }
            }
            mCenterLayer.doDraw(canvas);
        }

        if (mLeftLayer.isShow()) {

            mLeftLayer.doDraw(canvas);
        }
        
        if (mRightLayer.isShow()) {

            mRightLayer.doDraw(canvas);
        }

    }

    @Override
    public void rePrepareWhenDrawing(RectF rect) {
        mLeftLayer.rePrepareWhenDrawing(rect);
        mRightLayer.rePrepareWhenDrawing(rect);
        mCenterLayer.rePrepareWhenDrawing(rect);
    }

    public void layout(float left, float top, float right, float bottom) {
        super.layout(left, top, right, bottom);
        mLeftLayer.layout(mLeftLayer.getLeft(), top, mLeftLayer.getRight(), bottom);
        mRightLayer.layout(mRightLayer.getLeft(), top, mRightLayer.getRight(), bottom);
        mCenterLayer.layout(mCenterLayer.getLeft(), top, mCenterLayer.getRight(), bottom);
    }

    public void setLeftLayer(ChartLayer layer) {
        mLeftLayer = layer;
    }

    public void setRightLayer(ChartLayer layer) {
        mRightLayer = layer;
    }

    public void setCenterLayer(ChartLayer layer) {
        mCenterLayer = layer;
    }

    public void show(boolean isShow) {
        mLeftLayer.show(isShow);
        mRightLayer.show(isShow);
        mCenterLayer.show(isShow);
    }

    public boolean isShow() {
        return mRightLayer.isShow() || mLeftLayer.isShow() || mCenterLayer.isShow();
    }

    public boolean onActionPress(MotionEvent event) {
        return mLeftLayer.onActionPress(event) || mRightLayer.onActionPress(event) || mCenterLayer.onActionPress(event);
    }

    public void onActionUp(MotionEvent event) {
        mLeftLayer.onActionUp(event);
        mRightLayer.onActionUp(event);
        mCenterLayer.onActionUp(event);
    }

    public boolean onActionMove(MotionEvent event) {
        return mLeftLayer.onActionMove(event) || mRightLayer.onActionMove(event) || mCenterLayer.onActionMove(event);
    }

    public boolean onActionDown(MotionEvent event) {
        return mLeftLayer.onActionDown(event) || mRightLayer.onActionDown(event) || mCenterLayer.onActionDown(event);
    }

    public void setChartView(ChartView cv) {
        mLeftLayer.setChartView(cv);
        mRightLayer.setChartView(cv);
        mCenterLayer.setChartView(cv);
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

        if (mCenterLayer instanceof IGroupLayer) {
            IGroupLayer layer = (IGroupLayer) mCenterLayer;
            ChartLayer l = layer.getLayerByTag(tag);
            if (l != null) {
                return l;
            }
        }
        if (tag.equals(mCenterLayer.getTag())) {
            return mCenterLayer;
        }
        return null;
    }
}
