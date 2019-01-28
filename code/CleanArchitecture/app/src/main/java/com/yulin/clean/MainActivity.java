package com.yulin.clean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * https://medium.com/@dmilicic/a-detailed-guide-on-developing-android-apps-using-the-clean-architecture-pattern-d38d71e94029#.83x2bl9i7
 * Structure
 * The general structure for an Android app looks like this.
 * Outer layer packages: UI, Storage, Network, etc.
 * Middle layer packages: Presenters, Converters
 * Inner layer packages: Interactors, Models, Repositories, Executor
 * */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
