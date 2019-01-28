package com.hc.app;

import android.app.Application;
import android.util.Log;

/**
 * Created by liu_lei on 2017/11/20.
 */

public class MyApp extends Application {

    private static final String TAG = "houchen-MyApp";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate: called");
    }

}
