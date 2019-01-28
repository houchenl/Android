package com.hc.service.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hc.service.R;

/**
 * Created by houchen on 2016/6/10.
 * start service by startService(intent)
 * stop service by stopService(intent)
 */
public class StartServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_service);
    }

    public void start(View view) {
        /*
        * When service is not started, call this method will start service,
        * onCreate() and onStartCommand() will be called in service.
        * When service has already started, call this method will not start another service,
        * only one service will be started, and onStartCommand() will be called again.
        * */
        Intent intent = new Intent(this, StartService.class);
        startService(intent);
    }

    public void stop(View view) {
        /*
        * When service is running, call this method will stop service,
        * onDestroy() will be called in service.
        * When service has already destroyed or not has been started yet,
        * nothing will be done when call this method.
        * If service has been started, it will be running all the time
        * until stopService() is called.
        * */
        Intent intent = new Intent(this, StartService.class);
        stopService(intent);
    }

}
