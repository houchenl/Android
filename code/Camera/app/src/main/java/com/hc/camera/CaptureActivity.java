package com.hc.camera;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CaptureActivity extends AppCompatActivity implements Camera.PreviewCallback, SurfaceHolder.Callback {

    private static final String TAG = "houchenl-CaptureAct";

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.CAMERA",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private boolean mIsCapture = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        verifyStoragePermissions(this);

        mSurfaceView = findViewById(R.id.surface_view);
//
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);

        mCamera = Camera.open(0);

        mCamera.setPreviewCallback(this);
    }

    public void startPreview(View view) {
        try {
            Log.d(TAG, "startPreview: ");
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void capture(View view) {
        mIsCapture = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.lock();
        mCamera.release();
        mCamera = null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated: ");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d(TAG, "surfaceChanged: ");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceDestroyed: ");
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        Log.d(TAG, "onPreviewFrame: ");

        Camera.Size size = camera.getParameters().getPreviewSize();
        try {
            YuvImage image = new YuvImage(bytes, ImageFormat.NV21, size.width, size.height, null);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, stream);

            Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());

            if (mIsCapture) {
                saveMyBitmap("", bmp);
                mIsCapture = false;
            }

//            Log.d(TAG, "onPreviewFrame: width " + size.width + ", height " + size.height + ", bmp " + bmp);

            //**********************
            //因为图片会放生旋转，因此要对图片进行旋转到和手机在一个方向上
//                rotateMyBitmap(bmp);
            //**********************************

            stream.close();
        } catch (Exception ex) {
            Log.e("Sys", "Error:" + ex.getMessage());
        }
    }

    public String saveMyBitmap(String bitName, Bitmap bitmap) {
        Log.d(TAG, "saveMyBitmap: " + bitmap);
        File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");

        try {
            Log.d(TAG, "saveMyBitmap: create new file");
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("在保存图片时出错：" + e.toString());
            Log.d(TAG, "saveMyBitmap: fail to create file");
        }
        FileOutputStream fOut = null;
        try {
            Log.d(TAG, "saveMyBitmap: create fos");
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "saveMyBitmap: fail to create fos", e);
        }
        try {
            Log.d(TAG, "saveMyBitmap: compress");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        } catch (Exception e) {
            Log.e(TAG, "saveMyBitmap: fail to compress", e);
            return "create_bitmap_error";
        }
        try {
            Log.d(TAG, "saveMyBitmap: flush");
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "saveMyBitmap: fail to flush", e);
        }
        try {
            Log.d(TAG, "saveMyBitmap: close fos");
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "saveMyBitmap: fail to close fos",e );
        }

        return file.getAbsolutePath();
    }

    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,33);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
