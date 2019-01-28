package com.hc.adolf.config;

import android.app.Application;

/**
 * Created by liulei0905 on 2016/8/4.
 *
 */
public class HcApplication extends Application {

    private static HcApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static HcApplication getInstance() {
        return mInstance;
    }

}
