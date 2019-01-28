package com.hc.service.broadcast;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hc.service.R;

/**
 * Created by houchen on 2016/6/11.
 *
 */
public class BroadcastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
    }

    public void start(View view) {
        Intent intent = new Intent(this, BroadcastService.class);
        startService(intent);
    }

    public void stop(View view) {
        Intent intent = new Intent(this, BroadcastService.class);
        stopService(intent);
    }

    public void playVideo(View view) {
        Intent intent = new Intent();
        intent.setAction("asdffdsa");
        sendBroadcast(intent);
    }
}
