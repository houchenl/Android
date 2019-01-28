package com.hc.service.thread;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hc.service.R;

/**
 * Created by houchen on 2016/6/11.
 * service which start thread
 */
public class ThreadServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_service);
    }

    public void thread(View view) {
        Intent intent = new Intent(this, BusyService.class);
        startService(intent);
    }

    public void intentService(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);
    }

}
