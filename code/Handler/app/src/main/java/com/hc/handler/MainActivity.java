package com.hc.handler;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    private TextView mTvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        findViewById(R.id.btn_change).setOnClickListener(this);
//        mTvMsg = findViewById(R.id.tv_msg);
    }

    @Override
    public void onClick(View v) {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mTvMsg.setText(String.valueOf(System.currentTimeMillis()));
//            }
//        }, 1000);
    }

}
