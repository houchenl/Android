package com.hc.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hc.service.bind.BindServiceActivity;
import com.hc.service.broadcast.BroadcastActivity;
import com.hc.service.broadcast.BroadcastService;
import com.hc.service.mix.MixInterface;
import com.hc.service.mix.MixServiceActivity;
import com.hc.service.start.StartServiceActivity;
import com.hc.service.thread.ThreadServiceActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start_service).setOnClickListener(this);
        findViewById(R.id.btn_bind_service).setOnClickListener(this);
        findViewById(R.id.btn_thread_service).setOnClickListener(this);
        findViewById(R.id.btn_broadcast_service).setOnClickListener(this);
        findViewById(R.id.btn_mix_service).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                startActivity(new Intent(this, StartServiceActivity.class));
                break;
            case R.id.btn_bind_service:
                startActivity(new Intent(this, BindServiceActivity.class));
                break;
            case R.id.btn_thread_service:
                startActivity(new Intent(this, ThreadServiceActivity.class));
                break;
            case R.id.btn_broadcast_service:
                startActivity(new Intent(this, BroadcastActivity.class));
                break;
            case R.id.btn_mix_service:
                startActivity(new Intent(this, MixServiceActivity.class));
                break;
            default:
                break;
        }
    }

}
