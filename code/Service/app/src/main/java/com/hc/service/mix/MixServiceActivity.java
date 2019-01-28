package com.hc.service.mix;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hc.service.R;

/**
 * Created by houchen on 2016/6/11.
 */
public class MixServiceActivity extends AppCompatActivity {

    private static final String TAG = "MixService";

    private MixInterface service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix_service);
    }

    public void start(View view) {
        startService(new Intent(this, MixService.class));
    }

    /**
     * you can not stop service if any activity bind to it.
     * */
    public void stop(View view) {
        stopService(new Intent(this, MixService.class));
    }

    /**
     * the second time you bind to a service which is running,
     * only onServiceConnected() will be called.
     * */
    public void bind(View view) {
        bindService(new Intent(this, MixService.class), conn, BIND_AUTO_CREATE);
    }

    /**
     * if stopService() has been called when service is bind to activity,
     * when unbindService() is called, onUnbind() and onDestroy() will be called together.
     * */
    public void unbind(View view) {
        try {
            unbindService(conn);
        } catch (Exception e) {
            Log.d(TAG, "unbind: " + e.getMessage());
        }
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d(TAG, "onServiceConnected: ");
            service = (MixInterface) binder;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            service = null;
        }
    };

}
