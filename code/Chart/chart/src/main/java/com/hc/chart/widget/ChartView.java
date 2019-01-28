package com.hc.chart.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.hc.chart.layer.ChartLayer;

import java.util.ArrayList;
import java.util.List;

public class ChartView extends View implements OnTouchListener {

    private List<ChartLayer> mLstLayers = new ArrayList<>();
    private RectF mChartAreaRectF = null;

    private Paint mPaint = new Paint();
    private Canvas mCanvas = null;
    private GestureDetector mGestureDetector = null;

    private boolean mHasLayouted = false;

    private static final int ACTION_NONE = 0;
    private static final int ACTION_ZOOM = 1;
    private int mActionType = ACTION_NONE;
    private float mLastDistance = 1f;
    private static final float MIN_ZOOM_DISTANCE = 10f;

    private float mScale = 1;

    public ChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(new MyOnGestureListener());
        setOnTouchListener(this);
        setFocusable(true);
        setLongClickable(true);
        setClickable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        if (mChartAreaRectF == null) {
            forceAdjustLayers();
        }
        rePrepareWhenDrawing(mChartAreaRectF);
        doDraw(canvas);
    }

    private void forceAdjustLayers() {
        float left = getPaddingLeft() + 1;
        float top = getPaddingTop() + 1;
        float right = getMeasuredWidth() - getPaddingRight() - 2;
        float bottom = getMeasuredHeight() - getPaddingBottom() - 2;
        mChartAreaRectF = new RectF(left, top, right, bottom);
        prepareBeforeDraw(mCanvas, mChartAreaRectF);
    }

    private int getAvailHeight() {
        return getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }

    public int getAvailWidth() {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    private void rePrepareWhenDrawing(RectF rectF) {
        for (int i = 0; i < mLstLayers.size(); i++) {
            mLstLayers.get(i).rePrepareWhenDrawing(rectF);
        }
    }

    private void prepareBeforeDraw(Canvas canvas, RectF rectF) {
        float usedHeight = 0;
        for (int i = 0; i < mLstLayers.size(); i++) {
            ChartLayer layer = mLstLayers.get(i);
            layer.prepareBeforeDraw(rectF);
            if (layer.getHeightPercent() == 0) {
                usedHeight += layer.getHeight();
            }
        }
        float leftHeight = getAvailHeight() - usedHeight;

        float top = getPaddingTop();
        for (int i = 0; i < mLstLayers.size(); i++) {
            ChartLayer layer = mLstLayers.get(i);
            float height;
            if (layer.getHeightPercent() == 0) {
                height = layer.getHeight();
                layer.layout(layer.getLeft(), top, layer.getRight(), top + height);
                top += height;
            } else {
                height = leftHeight * layer.getHeightPercent();
                layer.layout(layer.getLeft(), top, layer.getRight(), top + height);
                top = layer.getBottom();
            }
        }
    }

    private void doDraw(Canvas canvas) {
        for (int i = 0; i < mLstLayers.size(); i++) {
            ChartLayer layer = mLstLayers.get(i);
            if (layer.isShow()) {
                mPaint.setColor(layer.getBorderColor());
                mPaint.setStrokeWidth(layer.getBorderWidth());
                mPaint.setStyle(Style.STROKE);
                if (layer.getIsShowBorder()) {
                    canvas.drawRect(layer.getRectF(), mPaint);
                }

                layer.doDraw(canvas);
            }
        }
    }

    public void addLayer(ChartLayer layer) {
        layer.setChartView(this);
        mLstLayers.add(layer);
    }

    public void addLayer(int index, ChartLayer layer) {
        layer.setChartView(this);
        mLstLayers.add(index, layer);
    }

    public void setLayer(int index, ChartLayer layer) {
        layer.setChartView(this);
        mLstLayers.set(index, layer);
    }

    public void removeLayer(int index) {
        mLstLayers.remove(index);
    }

    public void removeAllLayers() {
        mLstLayers.clear();
    }

    public void removeLayer(ChartLayer layer) {
        mLstLayers.remove(layer);
    }

    public void clearLayers() {
        mLstLayers.clear();
    }

    public int getLayerCount() {
        return mLstLayers.size();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mHasLayouted = true;
        // if(mFinishLayoutListener != null)
        // {
        // mFinishLayoutListener.onFinishLayout();
        // }
        forceAdjustLayers();
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        forceAdjustLayers();
        invalidate();
    }

    public interface OnFinishLayoutListener {
        void onFinishLayout();
    }

    // private OnFinishLayoutListener mFinishLayoutListener = null;
    // public void setOnFinishLayoutListener(OnFinishLayoutListener listener)
    // {
    // mFinishLayoutListener = listener;
    // }

    public boolean hasLayouted() {
        return mHasLayouted;
    }

    private class MyOnGestureListener extends SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return onActionDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            // if(isInZooming())
            // {
            // return;
            // }
            // boolean result = onActionPress(e);
            // if(getParent() != null)
            // {
            // if(result)
            // {
            // getParent().requestDisallowInterceptTouchEvent(true);
            // }
            // else
            // {
            // getParent().requestDisallowInterceptTouchEvent(false);
            // }
            // }
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            if (isInZooming()) {
                return;
            }
            boolean result = onActionPress(e);
            if (getParent() != null) {
                if (result) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
            }
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            onActionUp(e);
            // getParent().requestDisallowInterceptTouchEvent(false);
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            onActionDoubleTap(e);
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            onActionSingleTap(e);
            return super.onSingleTapConfirmed(e);
        }

    }

    protected boolean onActionPress(MotionEvent event) {
        for (int i = 0; i < mLstLayers.size(); i++) {
            if (mLstLayers.get(i).onActionPress(event)) {
                return true;
            }
        }
        return false;
    }

    protected void onActionUp(MotionEvent event) {
        for (int i = 0; i < mLstLayers.size(); i++) {
            mLstLayers.get(i).onActionUp(event);
        }
    }

    protected boolean onActionMove(MotionEvent event) {
        for (int i = 0; i < mLstLayers.size(); i++) {
            if (mLstLayers.get(i).onActionMove(event)) {
                return true;
            }
        }
        return false;
    }

    protected boolean onActionDown(MotionEvent event) {
        for (int i = 0; i < mLstLayers.size(); i++) {
            if (mLstLayers.get(i).onActionDown(event)) {
                return true;
            }
        }
        return false;
    }

    private boolean onActionDoubleTap(MotionEvent event) {
        if (mDoubleTapListener != null) {
            mDoubleTapListener.onDoubleTap();
        }

        for (int i = 0; i < mLstLayers.size(); i++) {
            if (mLstLayers.get(i).onActionDoubleTap(event)) {
                return true;
            }
        }
        return false;
    }

    private boolean onActionSingleTap(MotionEvent event) {
        if (mSingleTapListener != null) {
            mSingleTapListener.onSingleTapConfirm();
        }

        for (int i = 0; i < mLstLayers.size(); i++) {
            if (mLstLayers.get(i).onActionSingleTap(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        onActionMove(event);
        doMultiPointsZoom(event);
        return mGestureDetector.onTouchEvent(event);
    }

    private void doMultiPointsZoom(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // Log.v("ZYL", "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                // Log.v("ZYL", "ACTION_POINTER_DOWN");
                if (mZoomActionListener != null) {
                    checkMultiTouch(event);
                    if (mActionType == ACTION_ZOOM) {
                        // getParent().requestDisallowInterceptTouchEvent(true);
                        mZoomActionListener.onStartZoom();
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                // Log.v("ZYL", "ACTION_CANCEL");
                // break;
            case MotionEvent.ACTION_UP:
                onActionUp(event);
                // Log.v("ZYL", "ACTION_UP");
            case MotionEvent.ACTION_POINTER_UP:
                // Log.v("ZYL", "ACTION_POINTER_UP");
                if (mZoomActionListener != null) {
                    if (mActionType == ACTION_ZOOM) {
                        mActionType = ACTION_NONE;
                        // getParent().requestDisallowInterceptTouchEvent(false);
                        mZoomActionListener.onFinishZoom(mScale);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // Log.v("ZYL", "ACTION_MOVE");
                if (mZoomActionListener != null) {
                    if (mActionType == ACTION_ZOOM) {
                        float newDist = distance(event);
                        if (newDist > MIN_ZOOM_DISTANCE) {
                            mScale = newDist / mLastDistance;
                            mZoomActionListener.onZooming(mScale);
                            invalidate();
                        }
                    }
                }
                break;
        }
    }

    public ChartLayer getLayerByTag(String tag) {
        for (int i = 0; i < mLstLayers.size(); i++) {
            ChartLayer layer = mLstLayers.get(i);
            if (layer instanceof IGroupLayer) {
                ChartLayer l = ((IGroupLayer) layer).getLayerByTag(tag);
                if (l != null) {
                    return l;
                }
            }
            if (tag.equals(layer.getTag())) {
                return layer;
            }
        }
        return null;
    }

    private boolean isInZooming() {
        return mActionType == ACTION_ZOOM;
    }

    private void checkMultiTouch(MotionEvent event) {
        mLastDistance = distance(event);
        if (mLastDistance > MIN_ZOOM_DISTANCE && checkMultiTouchRange(event.getY(0), event.getY(1))) {
            mActionType = ACTION_ZOOM;
        }
    }

    private boolean checkMultiTouchRange(float py1, float py2) {
        int top = 0;
        int bottom = getMeasuredHeight();

        return py1 >= top && py1 <= bottom && py2 >= top && py2 <= bottom;
    }

    private float distance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private OnZoomActionListener mZoomActionListener = null;

    interface OnZoomActionListener {
        void onStartZoom();

        void onFinishZoom(float rate);

        void onZooming(float rate);
    }

    public void setOnZoomActionListener(OnZoomActionListener listener) {
        mZoomActionListener = listener;
    }

    interface OnDoubleTapListener {
        void onDoubleTap();
    }

    private OnDoubleTapListener mDoubleTapListener = null;

    public void setOnDoubleTapListener(OnDoubleTapListener listener) {
        mDoubleTapListener = listener;
    }

    interface OnSingleTapListener {
        void onSingleTapConfirm();
    }

    private OnSingleTapListener mSingleTapListener = null;

    public void setOnSingleTapListener(OnSingleTapListener listener) {
        mSingleTapListener = listener;
    }

    public interface IGroupLayer {
        ChartLayer getLayerByTag(String tag);
    }

}
