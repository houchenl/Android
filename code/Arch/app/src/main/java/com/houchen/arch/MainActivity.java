package com.houchen.arch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.houchen.arch.lifecycle.BasePresenter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "houchenl-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

        getLifecycle().addObserver(new BasePresenter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
    
}
