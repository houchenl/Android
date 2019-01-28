package com.hc.eventbus;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liu_lei on 2018/1/5.
 */

public class EventThread extends Thread {

    private static final String TAG = "houchen-EventThread";

    @Override
    public void run() {
        super.run();

        Log.d(TAG, "run: thread start");

        try {
            Thread.sleep(15000);

            Log.d(TAG, "run: post event");
            MessageEvent event = new MessageEvent();
            event.text = "Hello Event Bus";
            EventBus.getDefault().post(event);

            Log.d(TAG, "run: thread stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
