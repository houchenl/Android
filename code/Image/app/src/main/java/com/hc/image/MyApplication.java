package com.hc.image;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by liulei0905 on 2016/6/20.
 */

public class MyApplication extends Application {

    private static MyApplication instance = null;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Fresco.initialize(this);
    }

}
