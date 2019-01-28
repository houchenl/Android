package com.yulin.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.yulin.remoteservice.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Service - MainActivity";

    private MyService.MyBinder mBinder;
    private IMyAidlInterface mRemoteBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: MainActivity thread id is " + Thread.currentThread().getId());
        Log.d(TAG, "onCreate: MainActivity process id is " + Process.myPid());
    }

    public void startService(View view) {
        startService(new Intent(this, MyService.class));
    }

    public void stopService(View view) {
        stopService(new Intent(this, MyService.class));
    }

    public void bindService(View view) {
//        Intent service = new Intent(this, MyService.class);
        Intent service = new Intent("com.yulin.remoteservice.RemoteService");
        bindService(service, conn, BIND_AUTO_CREATE);
    }

    public void unbindService(View view) {
        try {
            unbindService(conn);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "unbindService: ", e);
        }
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: ");
//            mBinder = (MyService.MyBinder) iBinder;
//            mBinder.startDownload();
            mRemoteBinder = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                int result = mRemoteBinder.plus(3, 5);
                String upperStr = mRemoteBinder.toUpperCase("hello world");
                Log.d(TAG, "result is " + result);
                Log.d(TAG, "upperStr is " + upperStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

}
