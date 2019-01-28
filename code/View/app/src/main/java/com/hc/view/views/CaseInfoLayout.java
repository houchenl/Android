package com.hc.view.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hc.view.R;

/**
 * Created by liu_lei on 2017/12/8.
 * 显示病历的layout
 */

public class CaseInfoLayout extends ScrollView {

    private LinearLayout llDetails;

    private Context mContext;

    public CaseInfoLayout(Context context) {
        this(context, null);
    }

    public CaseInfoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CaseInfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        removeAllViews();

        View rootLayout = LayoutInflater.from(context).inflate(R.layout.layout_case_info, null);
        llDetails = rootLayout.findViewById(R.id.ll_details);
        addView(rootLayout);
    }

    public void addItem(String key, String value) {
        View item = LayoutInflater.from(mContext).inflate(R.layout.layout_msg_info_item, null);
        TextView tvKey = item.findViewById(R.id.tv_item_key);
        TextView tvValue = item.findViewById(R.id.tv_item_value);
        tvKey.setText(key);
        tvValue.setText(value);
        llDetails.addView(item);
    }

}
