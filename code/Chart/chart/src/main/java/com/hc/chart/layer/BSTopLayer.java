package com.hc.chart.layer;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Paint.Style;
//import android.graphics.RectF;
//import cn.emoney.acg.data.protocol.quote.CpxReply.Cpx_Reply.cpx_item;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * @author daizhipeng
 *
 */
public class BSTopLayer extends ChartLayer implements ColumnarLayer.DrawInfoWriteOutCallBack {
	@Override
	public RectF prepareBeforeDraw(RectF rect) {
		return null;
	}

	@Override
	public void doDraw(Canvas canvas) {

	}

	@Override
	public void rePrepareWhenDrawing(RectF rect) {

	}

	@Override
	public void out(float centerX, float topY, float bottomY, int time, float columWidth) {

	}

	@Override
	public void clear() {

	}

//	Context mContext = null;
//
//	List<cpx_item> mLstBSItems = new ArrayList<cpx_item>();
//
//	// protected List<List<Object>> mLstKLineData = new
//	// ArrayList<List<Object>>();
//	Map<Integer, List<Object>> mMapKLineData = new HashMap<>();
//	// 0:float centerX; 1:float top; 2:float bottom; 3:ColumnarAtom value
//
//	Bitmap mBt_b = null;
//	Bitmap mBt_s = null;
//	private int mImg_width = 0;
//	int mImg_height = 0;
//	int mHalf_width = 0;
//
//	protected float mColumWidth = 0;
//
//	protected float mStrokeWidth = 1;
//
//	protected Bitmap mBitmapAvgLineIdentfy = null;
//	protected boolean mIsAvgLineIdentifyShow = false;
//
//	public BSTopLayer(Context context) {
//		mContext = context;
//	}
//
//	@Override
//	public RectF prepareBeforeDraw(RectF rect) {
//		mLeft = rect.left;
//		mRight = rect.right;
//		mTop = rect.top;
//		mBottom = rect.bottom;
//
//		getPaint().setColor(mColor);
//		getPaint().setStrokeWidth(mStrokeWidth);
//		getPaint().setAntiAlias(true);
//		getPaint().setStyle(Style.FILL);
//
//		mBt_b = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_bspoint_b);
//		mBt_s = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_bspoint_s);
//		mImg_width = mBt_b.getWidth();
//		mImg_height = mBt_b.getHeight();
//		mHalf_width = mImg_width / 2;
//
//		// LogUtil.easylog("sky", " left:" + mLeft + " top:" + mTop + " right:"
//		// + mRight + " bottom:" + mBottom);
//		return new RectF(mLeft, mTop, mRight, mBottom);
//	}
//
//	@Override
//	public void doDraw(Canvas canvas) {
//	    if (!isShow()) {
//            return;
//        }
//		if (mIsAvgLineIdentifyShow && mBitmapAvgLineIdentfy != null) {
//			canvas.drawBitmap(mBitmapAvgLineIdentfy, mLeft, mTop, getPaint());
//		}
//
//		for (int j = 0; j < mLstBSItems.size(); j++) {
//			cpx_item bs_item = mLstBSItems.get(j);
//			String bs_flag = bs_item.getBsFlag();
//			int date_bs = bs_item.getDatetime();
////			int date_bs = 0;
//
//			if (mMapKLineData.containsKey(date_bs)) {
//				List<Object> lstItem = mMapKLineData.get(date_bs);
//				float centerX = (Float) lstItem.get(0);
//				float top = (Float) lstItem.get(1);
//				float bottom = (Float) lstItem.get(2);
//
//				if ("1".equals(bs_flag)) {
//					// getPaint().setColor(FontUtils.COLOR_RISE);
//					// getPaint().setColor(0xffb99c40);
//					// canvas.drawText("B", centerX, bottom + 22,
//					// getPaint());
//
//					canvas.drawBitmap(mBt_b, centerX - mHalf_width, bottom + 8, getPaint());
//				} else if ("-1".equals(bs_flag)) {
//					// getPaint().setColor(FontUtils.COLOR_FALL);
//					// getPaint().setColor(0xff8cc53d);
//					// canvas.drawText("S", centerX, top - 8, getPaint());
//					float sPotin_y = top - 8 - mImg_height;
//					if (sPotin_y < mTop) {
//						sPotin_y = mTop - 5;
//					}
//					canvas.drawBitmap(mBt_s, centerX - mHalf_width, sPotin_y, getPaint());
//				}
//			}
//		}
//
//	}
//
//	@Override
//	public void rePrepareWhenDrawing(RectF rect) {
//	}
//
//	public void setBSItems(List<cpx_item> items) {
//		if (items == null) {
//			return;
//		}
//
//		mLstBSItems.clear();
//		mLstBSItems.addAll(items);
//	}
//
//	public void clearLstKLineData() {
//		// if (mLstKLineData != null) {
//		// mLstKLineData.clear();
//		// }
//
//		if (mMapKLineData != null) {
//			mMapKLineData.clear();
//		}
//	}
//
//	@Override
//	public void clear() {
//		clearLstKLineData();
//	}
//
//	@Override
//	public void out(float centerX, float topY, float bottomY, int time, float columWidth) {
//		List<Object> t_lstKLine = new ArrayList<Object>();
//		t_lstKLine.add(centerX);
//		t_lstKLine.add(topY);
//		t_lstKLine.add(bottomY);
//		t_lstKLine.add(time);
//		mMapKLineData.put(time, t_lstKLine);
//		// mLstKLineData.add(t_lstKLine);
//
//		mColumWidth = columWidth;
//	}
//
//	public void setAvgLineBitmap(Bitmap bitmap) {
//		mBitmapAvgLineIdentfy = bitmap;
//	}
//
//	public void switchAvgLineIdentifyOn(boolean isOn) {
//		mIsAvgLineIdentifyShow = isOn;
//	}
//
//	/**
//	 *
//	 * @param nFlag
//	 * @return
//	 */
//	protected Bitmap getBSBitmapByFlag(int nFlag) {
//		if (nFlag == 1) {
//			return mBt_b;
//		} else if (nFlag == -1) {
//			return mBt_s;
//		} else {
//			return null;
//		}
//	}
}
