package com.hc.recycler.demo;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

/**
 * Created by liulei on 2017/5/10.
 * 使用databinding实现ViewHolder
 */

class NormalViewHolder extends CommonViewHolder {

    private ViewDataBinding mBinding;

    public NormalViewHolder(View itemView) {
        super(itemView);

        mBinding = DataBindingUtil.bind(itemView);
    }

    public ViewDataBinding getBinding() {
        return mBinding;
    }

}
