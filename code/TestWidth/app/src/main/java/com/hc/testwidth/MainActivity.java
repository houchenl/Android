package com.hc.testwidth;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMsg = findViewById(R.id.tv_msg);

        DisplayMetrics dm =getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        float density = dm.density;
        int densityDpi = dm.densityDpi;

        int dpWidth = (int) (width / density + 0.5f);

        // width: 1600, density: 0.875, dpWidth: 1829, densityDpi: 140
        tvMsg.setText("width " + width + ", density: " + density + ", dpWidth: " + dpWidth + ", densityDpi: " + densityDpi);

    }
}
