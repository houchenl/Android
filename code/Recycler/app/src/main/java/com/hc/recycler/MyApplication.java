package com.hc.recycler;

import android.app.Application;

/**
 * Created by liulei on 2017/5/10.
 */

public class MyApplication extends Application {

    private static MyApplication context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }

    public static MyApplication getInstance() {
        return context;
    }

}
