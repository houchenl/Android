package com.hc.camera;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class CropActivity extends AppCompatActivity {

    private static final int CUT_OK = 222;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.CAMERA",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    Uri imageUri;
    ImageView ivCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        verifyStoragePermissions(this);

        ivCapture = findViewById(R.id.iv_capture);
    }

    public void crop(View view) {
        //1、图片保存路径
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path, System.currentTimeMillis() + ".jpg");
        imageUri = Uri.fromFile(file);

        //.....2、在点击事件中开启截图意图
        //调用系统功能进行截图  用系统的东西  隐式意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        // 下面这个crop = true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", false);//设置之后 onActivityResult 中data 就为null了
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//设置裁剪之后的图片保存位置
        startActivityForResult(intent, CUT_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CUT_OK:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "裁剪成功", Toast.LENGTH_SHORT).show();
                    try {
                        //将裁剪好的图片显示到界面上
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        ivCapture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "裁剪失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
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
