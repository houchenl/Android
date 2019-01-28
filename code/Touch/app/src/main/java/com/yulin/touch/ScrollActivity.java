package com.yulin.touch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ScrollActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        tv = (TextView) findViewById(R.id.tv);

        findViewById(R.id.btn_to_top).setOnClickListener(this);
        findViewById(R.id.btn_to_right).setOnClickListener(this);
        findViewById(R.id.btn_to_bottom).setOnClickListener(this);
        findViewById(R.id.btn_to_left).setOnClickListener(this);
        findViewById(R.id.btn_by_top).setOnClickListener(this);
        findViewById(R.id.btn_by_right).setOnClickListener(this);
        findViewById(R.id.btn_by_bottom).setOnClickListener(this);
        findViewById(R.id.btn_by_left).setOnClickListener(this);
        findViewById(R.id.btn_reset).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int aa = getResources().getDimensionPixelSize(R.dimen.aa);    // > 0
        int bb = getResources().getDimensionPixelSize(R.dimen.bb);    // < 0

        switch (v.getId()) {
            case R.id.btn_to_top:
                tv.scrollTo(0, aa);
                break;
            case R.id.btn_to_right:
                tv.scrollTo(bb, 0);
                break;
            case R.id.btn_to_bottom:
                tv.scrollTo(0, bb);
                break;
            case R.id.btn_to_left:
                tv.scrollTo(aa, 0);
                break;

            case R.id.btn_by_top:
                tv.scrollBy(0, aa);
                break;
            case R.id.btn_by_right:
                tv.scrollBy(bb, 0);
                break;
            case R.id.btn_by_bottom:
                tv.scrollBy(0, bb);
                break;
            case R.id.btn_by_left:
                tv.scrollBy(aa, 0);
                break;

            case R.id.btn_reset:
                tv.scrollTo(0, 0);
                break;
        }
    }

}
