package com.yulin.second;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yulin.common.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.i(":second MainActivity onCreate");
    }

}
