package com.hc.recycler.demo;

import android.view.View;

/**
 * Created by liulei on 2017/5/10.
 */

public interface OnItemClickListener {

    void onItemClick(int position, View view);

    boolean onItemLongClick(int position, View view);

}
