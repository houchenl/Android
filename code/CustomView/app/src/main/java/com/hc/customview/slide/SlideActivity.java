package com.hc.customview.slide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hc.customview.R;

/**
 * Created by liu_lei on 2017/7/24.
 */

public class SlideActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomHorizontalScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        scrollView = (CustomHorizontalScrollView) findViewById(R.id.scrollView);
        final SlideLayout slideLayout = (SlideLayout) findViewById(R.id.slide_layout);

        for (int i = 0; i < 8; i++) {
            slideLayout.addItem("item " + i);
        }
        slideLayout.setIndex(0);
        slideLayout.setOnItemClickListener(new SlideLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Toast.makeText(SlideActivity.this, "click position " + position, Toast.LENGTH_SHORT).show();
                slideLayout.setIndex(position);
            }
        });

        findViewById(R.id.btn_scroll_to).setOnClickListener(this);
        findViewById(R.id.btn_scroll_by).setOnClickListener(this);
        findViewById(R.id.btn_smooth_scroll_by).setOnClickListener(this);
        findViewById(R.id.btn_smooth_scroll_to).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scroll_by:
                int x = -60;
                scrollView.scrollBy(x, 0);
                break;
            case R.id.btn_scroll_to:
                scrollView.scrollTo(600, 0);
                break;
            case R.id.btn_smooth_scroll_by:
                int distance = -60;
                scrollView.smoothScrollBy(distance, 0);
                break;
            case R.id.btn_smooth_scroll_to:
                scrollView.smoothScrollTo(600, 0);
                break;
        }
    }

}
