package com.hc.recycler.demo;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by liulei on 2017/5/10.
 * 实现点击处理
 */

public abstract class CommonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private int mPosition = RecyclerView.NO_POSITION;
    private OnItemClickListener mOnItemClickListener;

    public CommonViewHolder(View itemView) {
        super(itemView);
    }

    public void setPosition(int pos) {
        mPosition = pos;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void registClick(View view) {
        if (view != null)
            view.setOnClickListener(this);
    }

    public void registLongClick(View view) {
        if (view != null)
            view.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null && mPosition != RecyclerView.NO_POSITION)
            mOnItemClickListener.onItemClick(mPosition, v);
    }

    @Override
    public boolean onLongClick(View v) {
        return mOnItemClickListener != null && mPosition != RecyclerView.NO_POSITION
                && mOnItemClickListener.onItemLongClick(mPosition, v);
    }

}
