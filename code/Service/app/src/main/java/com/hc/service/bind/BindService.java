package com.hc.service.bind;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by houchen on 2016/6/10.
 * service which is bind to a activity.
 */
public class BindService extends Service {

    private static final String TAG = "bind_service";

    private MyBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");

        binder = new MyBinder();
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        binder = null;
        Log.d(TAG, "onDestroy: ");
    }

    private static class MyBinder extends Binder implements BindInterface {

        @Override
        public void playMusic(String name) {
            Log.d(TAG, "playMusic: " + name);
        }

    }
    
}
