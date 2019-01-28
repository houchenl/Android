package com.hc.okhttp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "houchen-MainActivity";

    private static final String URL_UPLOAD_IMAGE = "http://192.168.135.109:8080/mtc/upload/uploadkano.json";
    private static final int MY_PERMISSIONS_REQUEST_SDCARD = 1223;
    private OkHttpClient client = new OkHttpClient();

    private String filePath = "/storage/emulated/0/Pictures/Screenshots/Screenshot_20160104-132209.png";

    private TextView mTvMsg;
    private MyHandler mHandler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_upload).setOnClickListener(this);
        mTvMsg = findViewById(R.id.tv_msg);
    }

    private void upload(final File sourceFile) {

        new Thread() {
            @Override
            public void run() {
                super.run();

                final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", sourceFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                        .addFormDataPart("type", "1")
                        .build();

                Request request = new Request.Builder()
                        .url(URL_UPLOAD_IMAGE)
                        .post(requestBody)
                        .build();

                Response response = null;
                try {
                    mHandler.obtainMessage(22, "send request...").sendToTarget();
                    response = client.newCall(request).execute();
                    String content = response.body().string();
                    mHandler.obtainMessage(22, content).sendToTarget();
                    Log.d(TAG, "run: " + content);
                } catch (IOException e) {
                    e.printStackTrace();
                    mHandler.obtainMessage(22, "fail for " + e.getMessage()).sendToTarget();
                    Log.e(TAG, "run: fail for " + e.getMessage(), e);
                }
            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        if (checkPermission()) {
            upload(new File(filePath));
        } else {
            requestSdCardPermission();
        }

//        File file = new File(filePath);
//        String name = file.getName();
//        Log.d(TAG, "onClick: " + name);
    }

    private boolean checkPermission() {
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
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    upload(new File(filePath));
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: refuse permission.");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String text = (String) msg.obj;
            mTvMsg.setText(text);
        }
    }

}
