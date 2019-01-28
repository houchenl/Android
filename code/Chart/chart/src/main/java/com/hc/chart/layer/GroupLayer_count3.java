package com.hc.chart.layer;

import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.hc.chart.widget.ChartView;
import com.hc.chart.widget.ChartView.IGroupLayer;

public class GroupLayer_count3 extends ChartLayer implements IGroupLayer {

    private ChartLayer mLeftLayer = null;
    private ChartLayer mCenterLayer = null;
    private ChartLayer mRightLayer = null;

    @Override
    public RectF prepareBeforeDraw(RectF rect) {

        RectF leftRectF = mLeftLayer.prepareBeforeDraw(rect);
        RectF rightRectF = mRightLayer.prepareBeforeDraw(rect);
        RectF centerRectF = new RectF(leftRectF.right, rect.top, rightRectF.left, rect.bottom);
        mCenterLayer.prepareBeforeDraw(centerRectF);

        mLeft = leftRectF.left;
        mTop = rect.top;
        mRight = rect.right;
        mBottom = rect.bottom;

        return rect;
    }

    @Override
    public void doDraw(Canvas canvas) {
        if (mLeftLayer.isShow()) {
            getPaint().setColor(mLeftLayer.getBorderColor());
            getPaint().setStrokeWidth(mLeftLayer.getBorderWidth());
            getPaint().setStyle(Style.STROKE);
            if (mLeftLayer.getIsShowBorder()) {
                canvas.drawRect(mLeftLayer.getRectF(), getPaint());
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

                int interval = 4;

                for (int i = 0; i < hLineNum; i++) {
                    top += perHeight;

                    float x_loc = mCenterLayer.getLeft();
                    while (x_loc < mCenterLayer.getRight()) {
                        path.moveTo(x_loc, top);
                        path.lineTo(x_loc + interval, top);
                        x_loc += (interval * 2);
                    }

                    canvas.drawPath(path, getPaint());
                    path.reset();
                }
            }
            if (mCenterLayer.isShowVGrid()) {
                int vLineNum = mCenterLayer.getVLineCount();
                float width = mCenterLayer.getWidth();
                float perWidth = width / (vLineNum + 1);
                float x = mCenterLayer.getLeft();
                for (int i = 0; i < vLineNum; i++) {
                    x += perWidth;
                    canvas.drawLine(x, mCenterLayer.getTop(), x, mCenterLayer.getBottom(), getPaint());
                }
            }
            mCenterLayer.doDraw(canvas);
        }

        if (mRightLayer.isShow()) {
            getPaint().setColor(mRightLayer.getBorderColor());
            getPaint().setStrokeWidth(mRightLayer.getBorderWidth());
            getPaint().setStyle(Style.STROKE);

            if (mRightLayer.getIsShowBorder()) {
                canvas.drawRect(mRightLayer.getRectF(), getPaint());
            }

            if (mRightLayer.isShowHGrid()) {
                Path path = new Path();

                int hLineNum = mRightLayer.getHLineCount();
                float height = mRightLayer.getHeight();
                float perHeight = height / (hLineNum + 1);
                float top = mRightLayer.getTop();

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
                int vLineNum = mRightLayer.getVLineCount();
                float width = mRightLayer.getWidth();
                float perWidth = width / (vLineNum + 1);
                float x = mRightLayer.getLeft();
                for (int i = 0; i < vLineNum; i++) {
                    x += perWidth;
                    canvas.drawLine(x, mRightLayer.getTop(), x, mRightLayer.getBottom(), getPaint());
                }
            }
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
