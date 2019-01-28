package com.hc.file;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "houchenlMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int selfPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (selfPermission == PackageManager.PERMISSION_GRANTED) {
                getPath();
            } else {
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 300);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 300 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
            getPath();
        }
    }

    private void getPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "getPath: media mounted");
            File externalStorageDir = Environment.getExternalStorageDirectory();
            Log.d(TAG, "getPath: externalStorageDir path " + externalStorageDir.getAbsolutePath());
            File dir = new File(externalStorageDir, "/Pictures/screenShot");
            boolean isDirExist = dir.exists() || dir.mkdirs();
            Log.d(TAG, "getPath: isDirExist " + isDirExist + ", dirPath " + dir.getAbsolutePath());
            if (isDirExist) {
                File pictureFile = new File(dir, "/local_camera_capture.jpg");
                try {
                    boolean isFileExist = pictureFile.exists();
                    if (isFileExist) {
                        Log.d(TAG, "getPath: delete exist image file");
                        pictureFile.delete();
                    }
                    isFileExist = pictureFile.createNewFile();
                    Log.d(TAG, "getPath: isFileExist " + isFileExist + ", filePath " + pictureFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "getPath: fail to create picture file");
                }
            }
        } else {
            Log.d(TAG, "getPath: media not mounted");
        }
    }

}
