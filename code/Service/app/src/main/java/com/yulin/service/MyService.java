package com.yulin.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by liu_lei on 2017/9/29.
 */

public class MyService extends Service {

    private static final String TAG = "Service - MyService";

    private MyBinder mBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // service 在主线程中运行，与MainActivity一样，线程id都是1
        Log.d(TAG, "onCreate: MyService thread id is " + Thread.currentThread().getId());
        Log.d(TAG, "onCreate: MyService process id is " + Process.myPid());

        // 启动前台service，提升service优先级
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 1, intent, 0);
        mBuilder.setContentTitle("ContentTitle")
                .setContentText("ContentText")
                .setContentIntent(pi)
                .setTicker("Ticker")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = mBuilder.build();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        stopForeground(true);
    }

    class MyBinder extends Binder {
        void startDownload() {
            Log.d(TAG, "startDownload: ");
        }
    }

}
