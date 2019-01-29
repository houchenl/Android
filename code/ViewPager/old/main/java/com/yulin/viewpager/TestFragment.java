package com.yulin.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by liulei0905 on 2016/2/6.
 */
public class TestFragment extends Fragment {

    public static TestFragment getInstance(String text, String color) {
        TestFragment fragment = new TestFragment();

        Bundle extras = new Bundle();
        extras.putString("hello", text);
        extras.putString("color", color);
        fragment.setArguments(extras);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle extras = getArguments();
        String text = extras != null ? extras.getString("hello") : "hello_default";
        String color = extras != null ? extras.getString("color") : "#dddddd";

        View view = inflater.inflate(R.layout.layout_fragment, container, false);
        TextView tvContent = (TextView) view.findViewById(R.id.tv);
        tvContent.setText(text);
        tvContent.setBackgroundColor(Color.parseColor(color));

        return view;
    }

}
