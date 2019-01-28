package com.hc.service.bind;

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
 * Created by houchen on 2016/6/10.
 * service is started when bind service.
 * service is destroyed when unBind service or activity is destroyed.
 */
public class BindServiceActivity extends AppCompatActivity {

    private static final String TAG = "bind_service";

    private BindInterface service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_service);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbind();
    }

    public void bind(View view) {
        /*
        * When service has not been bind, call this method will bind service to this activity,
        * onCreate() and onBind() will be called in service, onServiceConnected() will be called
        * in this activity.
        * Service bind to activity only once, when service has already been bind,
        * call this method will do nothing.
        * onServiceConnected() will return a instance of Binder, through this you can
        * call methods in service.
        * */
        Intent intent = new Intent(this, BindService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    public void unbind(View view) {
        unbind();
    }

    public void play(View view) {
        if (service != null)
            service.playMusic("SoundOfSilence");
    }

    private void unbind() {
        /*
        * If service has been bind to activity, it will be unbind when activity onDestroy() or
        * unbindService() is called.
        * unbindService() can only been called when service has been bind to activity, if call
        * unbindService() when service has not been bind to activity or service has already
        * been unbind, it will cause an exception, so this method should catch exception.
        * You should do unbind when onDestroy() in activity.
        * service should set to null after unbind.
        * */
        try {
            unbindService(conn);
            service = null;
        } catch (Exception e) {
            Log.d(TAG, "unbind: " + e.getMessage());
        }
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d(TAG, "onServiceConnected: ");
            service = (BindInterface) binder;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

}
