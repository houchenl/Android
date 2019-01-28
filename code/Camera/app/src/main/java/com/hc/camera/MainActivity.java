package com.hc.camera;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "houchenl-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Camera camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();

        List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
        for (Camera.Size size : supportedPictureSizes) {
            Log.d(TAG, "support picture size width " + size.width + ", height " + size.height + ", ratio " + size.width * 1.0 / size.height);
        }

        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        for (Camera.Size size : supportedPreviewSizes) {
            Log.d(TAG, "support preview size width " + size.width + ", height " + size.height + ", ratio " + size.width * 1.0 / size.height);
        }
    }

}
