package com.hc.textureview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private Camera mCamera;
    private TextureView mTextureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkPermission()) {
            setListener();
        } else {
            requestPermission();
        }

        findViewById(R.id.btn_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() {

                    }
                }, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {

                    }
                }, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {

                    }
                });
            }
        });
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mCamera = Camera.open();
//        mCamera.takePicture(null, null, null);
//        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
//        mTextureView.setLayoutParams(new FrameLayout.LayoutParams(previewSize.width, previewSize.height, Gravity.CENTER));
        try {
            if (mCamera != null) {
            mCamera.setPreviewTexture(surface);
        mCamera.startPreview();

            }
        } catch (IOException t) {
        }
//        mTextureView.setAlpha(1.0f);
//        mTextureView.setRotation(90.0f);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    private boolean checkPermission() {
        int ret = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        return ret == PackageManager.PERMISSION_GRANTED;
    }
    
    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setListener();
        }
    }

    private void setListener() {
        mTextureView = findViewById(R.id.texture_view);
        mTextureView.setSurfaceTextureListener(this);
    }

}
