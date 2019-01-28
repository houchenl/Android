package com.hc.bitmap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "houchen-MainActivity";
    private static final int MY_PERMISSIONS_REQUEST_SDCARD = 22;

    private ImageView mIvBitmap;
    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("hello world");

        mIvBitmap = findViewById(R.id.iv_bitmap);
        mScrollView = findViewById(R.id.scrollView);
        findViewById(R.id.btn_generate).setOnClickListener(this);

        if (!checkSdCardPermission()) {
            requestSdCardPermission();
        }

        getPath();
    }

    @Override
    public void onClick(View v) {
        // 生成bitmap需要在页面加载完成后执行，不能在onCreate中执行
        // onCreate时view尚未初始化，width和height为0，不能创建bitmap
//        Bitmap bitmap = ViewUtil.getBitmapByView(mScrollView);
//        mIvBitmap.setImageBitmap(bitmap);

//        String path = "/sdcard/zhihuiyaodian/uvc_capture_20171203_101158.png";
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        Log.d(TAG, "onClick: " + bitmap);
//        mIvBitmap.setImageBitmap(bitmap);

        readAndSave();
    }

    private void getPath() {
        File dir = new File(Environment.getExternalStorageDirectory(), "/zhihuiyaodian");
        if (!dir.exists()) {
            dir.mkdir();
        }
        String path = dir.getPath();
        String absolutePath = dir.getAbsolutePath();
        Log.d(TAG, "getPath: path " + path + ", absolutePath " + absolutePath);
    }

    private long lastTime;

    private void readAndSave() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                lastTime = System.currentTimeMillis();
                Log.d(TAG, "run: 1 " + duration());

                Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Pictures/lake.jpg");
                Log.d(TAG, "run: 2 " + duration());

                String path = "/sdcard/Pictures/lake_" + System.currentTimeMillis() + ".jpeg";
                File file = new File(path);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                        Log.d(TAG, "run: 3 " + duration());

                        FileOutputStream fos = new FileOutputStream(file);
                        Log.d(TAG, "run: 4 " + duration());

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fos);
                        Log.d(TAG, "run: 5 " + duration());

                        fos.flush();
                        fos.close();
                        Log.d(TAG, "run: 6 " + duration());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private long duration() {
        long currentTime = System.currentTimeMillis();
        long duration = currentTime - lastTime;
        lastTime = currentTime;
        return duration;
    }

    private boolean checkSdCardPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    private void requestSdCardPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_SDCARD);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SDCARD:
                break;
        }
    }

}
