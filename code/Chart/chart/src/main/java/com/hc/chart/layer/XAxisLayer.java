package com.hc.chart.layer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

public class XAxisLayer extends ChartLayer {
	private Align mAlign = Align.CENTER;
	private int mTextSize = 18;
	private float mTextHeight = 0;
	
	private float mSpace = 0;
	private String mMinLeftPaddingString = "";
	private String mMinRightPaddingString = "";
	private float mAxisLeftPadding = 0;
	private float mAxisRighttPadding = 0;
	private List<String> mLstAxises = new ArrayList<String>();
	@Override
	public RectF prepareBeforeDraw(RectF rect) {
		getPaint().setColor(mColor);
		getPaint().setTextSize(mTextSize);
		getPaint().setAntiAlias(true);
		getPaint().setTextAlign(mAlign);
		
		Paint.FontMetrics fm = getPaint().getFontMetrics();
		mTextHeight = (float) (Math.ceil(fm.descent - fm.ascent) + 2);
		
		float ww = getPaint().measureText(mMinLeftPaddingString);
		if(ww > mAxisLeftPadding)
		{
			mAxisLeftPadding = ww;
		}
		
		ww = getPaint().measureText(mMinRightPaddingString);
		if (ww > mAxisRighttPadding) {
			mAxisRighttPadding = ww;
		}
		
		mLeft = rect.left;
		mTop = rect.top;
		mRight = rect.right;
		mBottom = rect.top + mTextHeight;
		
		float totalWidth = mRight - mLeft - mPaddingLeft - mPaddingRight - mAxisLeftPadding - mAxisRighttPadding;
		
		if(mLstAxises.size() == 1)
		{
			mSpace = totalWidth/2;
		}
		else if(mLstAxises.size() > 1)
		{
			mSpace = totalWidth / (mLstAxises.size() - 1);
		}
		
		return new RectF(mLeft, mTop, mRight, mBottom);
	}

	@Override
	public void doDraw(Canvas canvas) {
		Paint.FontMetrics fm = getPaint().getFontMetrics();
		
		float startY = mTop + (mBottom - mTop - fm.bottom + fm.top) / 2 - fm.top;
		int size = mLstAxises.size();
		if(size == 1)
		{
			float startX = mLeft + mPaddingLeft + mSpace + mAxisLeftPadding;
			canvas.drawText(mLstAxises.get(0), startX , startY, getPaint());
		}
		else
		{
			for(int i = 0; i < size; i++)
			{
				String text = mLstAxises.get(i);
				if (text == null) {
					continue;
				}
				float startX = mLeft + mPaddingLeft + mSpace*i + mAxisLeftPadding;
				if(i == 0)
				{
					float w = getPaint().measureText(text);
					canvas.drawText(text, startX + w/2, startY, getPaint());
				}
				else if(i == size - 1)
				{
					float w = getPaint().measureText(text);
					canvas.drawText(text, startX - w/2, startY, getPaint());
				}
				else
				{
					canvas.drawText(text, startX, startY, getPaint());
				}
			}
		}
	}

	@Override
	public void rePrepareWhenDrawing(RectF rect) {
		float totalWidth = mRight - mLeft - mPaddingLeft - mPaddingRight - mAxisLeftPadding - mAxisRighttPadding;
		if(mLstAxises.size() == 1)
		{
			mSpace = totalWidth/2;
		}
		else if(mLstAxises.size() > 1)
		{
			mSpace = totalWidth / (mLstAxises.size() - 1);
		}
	}

	public void addValue(String value)
	{
		mLstAxises.add(value);
	}
	
	public void clearValue()
	{
		mLstAxises.clear();
	}
	
	public void setMinLeftPaddingString(String str)
	{
		mMinLeftPaddingString = str;
	}
	public void setMinLeftPadding(float rightWidth)
	{
		mAxisLeftPadding = rightWidth;
	}
	
	public void setMinRightPaddingString(String str)
	{
		mMinRightPaddingString = str;
	}
	public void setMinRightPadding(float rightWidth)
	{
		mAxisRighttPadding = rightWidth;
	}
	
	public void setTextSize(int size)
	{
		mTextSize = size;
	}
}
