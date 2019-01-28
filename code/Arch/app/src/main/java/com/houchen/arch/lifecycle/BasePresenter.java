package com.houchen.arch.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

public class BasePresenter implements IPresenter {

    private static final String TAG = "houchenl-BasePresenter";

    @Override
    public void onCreate(LifecycleOwner owner) {
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onLifecycleChanged(LifecycleOwner owner, Lifecycle.Event event) {
        Log.d(TAG, "onLifecycleChanged: event is " + event.name());
    }

}
