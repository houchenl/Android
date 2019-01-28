package com.yulin.touch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by liu_lei on 2017/12/13.
 */

public class ScrollClickableActivity extends AppCompatActivity implements View.OnClickListener {

    private ScrollView mScrollView;
    private ImageView mImageView;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_clickable);

        findViewById(R.id.btn_show_image).setOnClickListener(this);

        mScrollView = findViewById(R.id.scroll_view_container);
        mImageView = findViewById(R.id.iv_picture);
        mLinearLayout = findViewById(R.id.ll_container);
    }

    @Override
    public void onClick(View v) {
        mScrollView.setVisibility(View.VISIBLE);
//        mImageView.setImageResource(R.drawable.img13);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.setVisibility(View.GONE);
            }
        });
    }

}
