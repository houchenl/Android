package com.yulin.viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yulin.viewpager.R;

public class TextFragment extends Fragment {

    private static final String EXTRA_TEXT = "extra_text";

    private String mText;

    public static TextFragment getInstance(String text) {
        Bundle extras = new Bundle();
        extras.putString(EXTRA_TEXT, text);
        TextFragment fragment = new TextFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(EXTRA_TEXT)) {
            mText = arguments.getString(EXTRA_TEXT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        TextView tvMsg = view.findViewById(R.id.fragment_text_tv_msg);
        tvMsg.setText(mText);
        return view;
    }

}
