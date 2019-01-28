package com.hc.image.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hc.image.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liu_lei on 2017/6/5.
 *
 * 拍照
 */

public class CaptureCameraActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_SYSTEM_CAMERA = 1001;
    private static final int CROP_PHOTO = 1002;
    private static final int MY_CAMERA_REQUEST_CODE = 3;

    private Uri imageUri;    // 保存拍摄的图片

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_camera);

        findViewById(R.id.btn_camera).setOnClickListener(this);

        checkPermission();
    }

    // 如果系统版本大于等于23，检查是否已获取camera权限，如果没有，代码请求该权限
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    /**
     * 调起系统相机
     */
    private void startSystemCamera() {
        Intent intent = new Intent();

        // 指定开启系统相机的Action
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        // 指定保存图片地址
//        imageUri = getImageUri();
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);    //指定图片输出地址

        startActivityForResult(intent, REQUEST_CODE_SYSTEM_CAMERA);
    }

    /**
     * 获取只在图片的uri
     */
    private Uri getImageUri() {
        //图片名称 时间命名
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);

        //创建File对象用于存储拍照的图片 SD卡根目录
        //存储至DCIM文件夹
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File outputImage = new File(path, filename + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将File对象转换为Uri并启动照相程序
        return Uri.fromFile(outputImage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                startSystemCamera();
                break;
        }
    }

}
