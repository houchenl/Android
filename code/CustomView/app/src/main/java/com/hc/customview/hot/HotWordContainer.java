package com.hc.customview.hot;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by liu_lei on 2017/7/31.
 * 显示热门搜索关键词，从左向右，从下向下排列，到达屏幕右边缘就换行显示
 */

public class HotWordContainer extends ViewGroup {

    private static final String TAG = "HotWordContainer";

    private int mScreenWidth = 0;

    private OnItemClickListener listener;

    public HotWordContainer(Context context) {
        this(context, null);
    }

    public HotWordContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HotWordContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScreenWidth = getScreenWidth(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 计算出所有子view的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // Get the width based on the measure specs
        int widthSize = View.resolveSize(getDesireWidth(), widthMeasureSpec);

        // Get the height based on the measure specs
        int heightSize = View.resolveSize(getDesireHeight(), heightMeasureSpec);

        Log.d(TAG, "onMeasure: width " + widthSize + ", height: " + heightSize);

        // 获取此ViewGroup上级容器为它推荐的宽和高，以及计算模式
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 从左向右，从上到下，排列子view，遇到到达屏幕右边缘，换行显示
        int contentWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int rowHeight = 0;    // 向下换行时，top需要加上一行的高度
        int previousViewWidth = 0;
        int rowWidth = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
//            MarginLayoutParams cParams = (MarginLayoutParams) childView.getLayoutParams();
            int width = childView.getMeasuredWidth()/* + cParams.leftMargin + cParams.rightMargin*/;
            int height = childView.getMeasuredHeight()/* + cParams.topMargin + cParams.bottomMargin*/;

            if (i == 0) {
                rowHeight = height;
            }

            rowWidth += width;
            if (i > 0) {
                rowHeight = Math.max(rowHeight, height);
            }

            // 判断该子view是否是重起一行的第1个，如果是，重置left和top及rowWidth/rowHeight，否则，left右移previousViewWidth的距离
            // 如果rowWidth大于contentWidth，重起一行
            if (rowWidth > contentWidth) {
                left = getPaddingLeft();
                top += rowHeight;

                rowWidth = width;
                rowHeight = height;
            } else {
                left += previousViewWidth;
            }

//            childView.layout(left + cParams.leftMargin, top + cParams.topMargin,
//                    left + cParams.leftMargin + childView.getMeasuredWidth(), top + cParams.topMargin + childView.getMeasuredHeight());
            childView.layout(left, top,
                    left + childView.getMeasuredWidth(), top + childView.getMeasuredHeight());

            previousViewWidth = width;
        }
    }

    private int getDesireWidth() {
        // 假设所有子view都水平排列，计算他们的宽度总和。
        // 取子view宽度总和与屏幕宽度的小者，为container的宽度。
        int childCount = getChildCount();
        int childTotalWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int cWidth = childView.getMeasuredWidth();
//            MarginLayoutParams cParams = (MarginLayoutParams) childView.getLayoutParams();
            childTotalWidth += cWidth/* + cParams.leftMargin + cParams.rightMargin*/;
        }
        childTotalWidth += getPaddingLeft() + getPaddingRight();

        Log.d(TAG, "getDesireWidth: totalWidth: " + childTotalWidth + ", screenWidth: " + mScreenWidth);

        return Math.min(childTotalWidth, mScreenWidth);
    }

    private int getDesireHeight() {
        // 子view水平排列，遇到边界，折到下一行显示，计算出总高度
        int desireHeight = 0;

        // 计算第一行的高度
        int rowHeight = 0;
        int rowWidth = 0;    // 统计子view的宽度总和，如果到达右边缘，重起一行，重新统计
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
//            MarginLayoutParams cParams = (MarginLayoutParams) childView.getLayoutParams();
            int width = childView.getMeasuredWidth()/* + cParams.leftMargin + cParams.rightMargin*/;
            int height = childView.getMeasuredHeight()/* + cParams.topMargin + cParams.bottomMargin*/;

            // 如果是第1个子view，设置子view的宽高为布局的宽高
            if (i == 0) {
                rowWidth = width;
                rowHeight = height;

                if (childCount == 1) {
                    desireHeight = rowHeight;
                }
                continue;
            }

            // 正常情况下，如果没有转到下一行，这样计算
            rowWidth += width;
            rowHeight = Math.max(rowHeight, height);    // 取一行中子view height的大者为高度

            // 如果rowWidth大于屏幕宽度，表示，新的一行已经开始，先把rowHeight添加到总高度中，再重置rowWidth为width，设置rowHeight为height
            if (rowWidth > mScreenWidth) {
                desireHeight += rowHeight;

                rowWidth = width;
                rowHeight = height;
            }

            // 如果是第后一个子view，将rowHeight添加到总高度中
            if (i == childCount - 1) {
                desireHeight += rowHeight;
            }
        }

        desireHeight = desireHeight + getPaddingTop() + getPaddingBottom();
        Log.d(TAG, "getDesireHeight: " + desireHeight);

        return desireHeight;
    }

    public int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public void addChildView(final View view, final int pos) {
        if (view != null) {
            addView(view);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(view, pos);
                    }
                }
            });
        }
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }

}
