package com.yulin.viewpager.size;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetImageSizeTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "GetImageSizeTask";

    private String url;

    private int width, height;
    private long startTime;

    public GetImageSizeTask(String url) {
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        startTime = System.currentTimeMillis();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        boolean result = false;
        try {
            URL mUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
            InputStream is = connection.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);
            width = options.outWidth;
            height = options.outHeight;
            result = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if (aBoolean) {
            Log.d(TAG, "url" + url + ", width: " + width + ", height: " + height + ", duration " + duration);
        } else {
            Log.d(TAG, "获取失败");
        }
    }

}
