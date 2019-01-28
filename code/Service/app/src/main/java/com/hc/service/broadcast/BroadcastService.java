package com.hc.service.broadcast;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by houchen on 2016/6/11.
 * This service has a broadcastReceiver inside, user can send broadcast outside,
 * when broadcast receiver receives it can call methods in service.
 * In this way user can call methods in service.
 */
public class BroadcastService extends Service {

    private static final String TAG = "BroadcastService";

    private MyBroadcastReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

        receiver = new MyBroadcastReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction("asdffdsa");

        registerReceiver(receiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        unregisterReceiver(receiver);
        receiver = null;
    }
    
    private void playVideo(String name) {
        Log.d(TAG, "playVideo: " + name);
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ");
            playVideo("Casablanca");
        }
    }

}
