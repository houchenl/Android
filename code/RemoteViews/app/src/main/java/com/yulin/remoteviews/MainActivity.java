package com.yulin.remoteviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_normal_notification).setOnClickListener(this);
        findViewById(R.id.btn_custom_notification).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal_notification:
                openNormalNotification();
                break;
            case R.id.btn_custom_notification:
                openCustomNotification();
                break;
        }
    }

    private void openNormalNotification() {

    }

    private void openCustomNotification() {

    }

}
