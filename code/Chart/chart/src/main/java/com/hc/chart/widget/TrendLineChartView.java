package com.hc.chart.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hc.chart.util.FixPair;

import java.util.ArrayList;
import java.util.List;

public class TrendLineChartView extends View {
	private int mMaxValue = 0;
	private int mMinValue = 0;
	
	private RectF mLineChartRectF = null;

//	private List<Integer> mLstYAxis = new ArrayList<>();
	
	private List<FixPair<Integer, Integer>> mLstData = new ArrayList<>();

	public int getMaxValue() {
		return mMaxValue;
	}

	public int getMinValue() {
		return mMinValue;
	}

	public TrendLineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public TrendLineChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TrendLineChartView(Context context) {
		super(context);
		init();
	}

	private void init() {
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		mCanvas = canvas;
		if (mLineChartRectF == null) {
			forceAdjustLayers();
		}

//		doDraw(canvas);
	}

	private void forceAdjustLayers() {
		float left = getPaddingLeft() + 1;
		float top = getPaddingTop() + 1;
		float right = getMeasuredWidth() - getPaddingRight() - 2;
		float bottom = getMeasuredHeight() - getPaddingBottom() - 2;
		mLineChartRectF = new RectF(left, top, right, bottom);

	}
	
	private void regulateAll(int dMin, int dMax) {

		if (dMin > dMax) {
			return;
		}

		int t_dmin;
		if (dMin < 0) {
			double td = Math.abs(dMin) / 1000f;
			td = Math.ceil(td) * 1000;
			t_dmin = -(int) td;
		} else {
			double td = dMin / 1000f;
			td = Math.floor(td) * 1000;
			t_dmin = (int) td;
		}

		int t_dmax;
		if (dMax < 0) {
			double td = Math.abs(dMax) / 1000f;
			td = Math.floor(td) * 1000;
			t_dmax = -(int) td;
		} else {
			double td = dMax / 1000f;
			td = Math.ceil(td) * 1000;
			t_dmax = (int) td;
		}

		int t_dmiddle = (t_dmax + t_dmin) / 2;

//		mLstYAxis.clear();
//		mLstYAxis.add(t_dmin);
//		mLstYAxis.add(t_dmiddle);
//		mLstYAxis.add(t_dmax);
	}

	public void addPointPair(FixPair<Integer, Integer> point) {
		mLstData.add(point);
	}

	private void calcMaxAndMinValue() {
		if (mLstData.size() >= 1) {
			mMaxValue = Math.max(mLstData.get(0).getFirst(), mLstData.get(0).getSecond());
			mMinValue = Math.min(mLstData.get(0).getFirst(), mLstData.get(0).getSecond());

			for (int i = 1; i < mLstData.size(); i++) {
				int tMax = Math.max(mLstData.get(i).getFirst(), mLstData.get(i).getSecond());
				int tMin = Math.min(mLstData.get(i).getFirst(), mLstData.get(i).getSecond());
				mMaxValue = tMax > mMaxValue ? tMax : mMaxValue;
				mMinValue = tMin < mMinValue ? tMin : mMinValue;
			}
		}
		else {
			mMaxValue = 0;
			mMinValue = 0;
		}

	}
	
	public void forceAdjustUI()
	{
		calcMaxAndMinValue();
		regulateAll(mMinValue, mMaxValue);
	}

}
