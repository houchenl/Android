package com.hc.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hc.customview.percent.RingPercentActivity;
import com.hc.customview.round.RoundActivity;
import com.hc.customview.circle.CircleActivity;

/**
 * Created by liu_lei on 2018/1/12.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_circle).setOnClickListener(this);
        findViewById(R.id.btn_round).setOnClickListener(this);
        findViewById(R.id.btn_ring_percent).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_circle:
                startActivity(new Intent(this, CircleActivity.class));
                break;
            case R.id.btn_round:
                startActivity(new Intent(this, RoundActivity.class));
                break;
            case R.id.btn_ring_percent:
                startActivity(new Intent(this, RingPercentActivity.class));
                break;
        }
    }

}
