package com.hc.image.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hc.image.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liulei0905 on 2016/6/22.
 *
 * 首先，通过图片地址获取到InputStream，流程如下 path --> URL --> HttpURLConnection --> InputStream
 * 然后，通过InputStream有两种方式获取到Bitmap，
 *      一种是直接从InputStream转换为Bitmap，
 *      另一种是先从InputStream读取到byte[]数组，再从byte[]数组转换为Bitmap
 * 最后，显示Bitmap
 *
 * 缓存时，首先创建目标文件对象，然后通过目标文件对象构造BufferedOutputStream，最后使用Bitmap的compress方法输出图片到文件中
 */

public class HttpConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String FILE_PATH = "http://img1.gtimg.com/sports/pics/hv1/177/113/2087/135736167.jpg";
    private static final String FILE_NAME = "save.jpg";
    private static final String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/download/";

    private String mSaveMsg;

    private ImageView img;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_conn);

        img = (ImageView) findViewById(R.id.img);

        findViewById(R.id.btn_show).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                new Thread(showTask).start();
                break;
            case R.id.btn_save:
                new Thread(saveTask).start();
                break;
        }
    }

    private Runnable showTask = new Runnable() {
        @Override
        public void run() {
            // 以下是取得图片的两种方法

            // 1. 取得byte数组，从byte数组生成bitmap
//            byte[] data = getImage(FILE_PATH);
//            if (data != null) {
//                mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//            } else {
//                Toast.makeText(HttpConnectionActivity.this, "Show Image Error", Toast.LENGTH_SHORT).show();
//            }

            // 2. 取得InputStream，从InputStream直接生成bitmap
            InputStream is = getImageStream(FILE_PATH);
            if (is != null) {
                mBitmap = BitmapFactory.decodeStream(is);
            } else {
                Toast.makeText(HttpConnectionActivity.this, "Show Image Error", Toast.LENGTH_SHORT).show();
            }

            showHandler.sendEmptyMessage(0);
        }
    };

    private byte[] getImage(String path) {
        InputStream is = getImageStream(path);
        return readStream(is);
    }

    private InputStream getImageStream(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return conn.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private byte[] readStream(InputStream is) {
        if (is == null) return null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bos.toByteArray();
    }

    private Handler showHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mBitmap != null)
                img.setImageBitmap(mBitmap);
        }
    };

    private Runnable saveTask = new Runnable() {
        @Override
        public void run() {
            if (saveFile(mBitmap, FILE_NAME))
                mSaveMsg = "Save Success";
            else
                mSaveMsg = "Save Failed";

            saveHandler.sendEmptyMessage(1);
        }
    };

    private boolean saveFile(Bitmap bitmap, String fileName) {
        if (bitmap == null || fileName == null) return false;

        File directory = new File(ALBUM_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(ALBUM_PATH + fileName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);

            bos.flush();
            bos.close();

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Handler saveHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(HttpConnectionActivity.this, mSaveMsg, Toast.LENGTH_LONG).show();
        }
    };

}
