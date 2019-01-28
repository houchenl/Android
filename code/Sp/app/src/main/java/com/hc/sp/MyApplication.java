package com.hc.sp;

import android.app.Application;

/**
 * Created by liu_lei on 2017/11/20.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

}
