package com.hc.fragment.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hc.fragment.R;

public class BaseFragment extends Fragment {

    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_BACKGROUND_COLOR = "arg_background_color";

    private String mText;
    private String mBackgroundColor;

    public BaseFragment() {
        // Required empty public constructor
    }

    public static BaseFragment newInstance(String param1) {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static BaseFragment newInstance(String text, String color) {
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        args.putString(ARG_BACKGROUND_COLOR, color);
        BaseFragment fragment = new BaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mText = getArguments().getString(ARG_TEXT);
            mBackgroundColor = getArguments().getString(ARG_BACKGROUND_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.tv_msg);
        textView.setText(mText);

        if (!TextUtils.isEmpty(mBackgroundColor)) {
            int backgroundColor = Color.parseColor(mBackgroundColor);
            view.setBackgroundColor(backgroundColor);
        }
    }

    public String getText() {
        return mText;
    }

}
