package com.hc.recycler.demo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hc.recycler.R;

/**
 * Created by liulei on 2017/5/10.
 * 使用传统方式实现ViewHolder
 */

public class NormalViewHolderV2 extends RecyclerView.ViewHolder {

    private View mRootLayout;
    private TextView mTvTitle, mTvAuthor;

    public NormalViewHolderV2(View itemView) {
        super(itemView);

        mRootLayout = itemView.findViewById(R.id.root_layout);
        mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mTvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
    }

    public View getRootLayout() {
        return mRootLayout;
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    public TextView getTvAuthor() {
        return mTvAuthor;
    }

}
