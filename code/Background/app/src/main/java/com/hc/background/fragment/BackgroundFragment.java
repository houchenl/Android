package com.hc.background.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hc.background.R;

/**
 * Created by liu_lei on 2018/1/31.
 * test background corner
 *
 * 1. activity的container为圆角，而fragment的内容为全屏矩形时，显示全屏矩形
 * 2. fragment中父布局为圆角时，父布局显示为圆角。子布局为矩形时，子布局显示为矩形。因为子布局显示在上，所以看到的是子布局，也就是看到矩形。
 * 3. 所以fragment想要显示圆角，不能在activity的container中设置，必须在fragment的布局中设置，而且父布局和相关子布局都要设置圆角。
 */

public class BackgroundFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater().inflate(R.layout.fragment_background, container, false);
    }

}
