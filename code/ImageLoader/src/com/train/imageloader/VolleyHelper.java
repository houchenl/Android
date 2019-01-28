package com.train.imageloader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class VolleyHelper {

    private static VolleyHelper instance;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private static Context context;

    private LruCache<String, Bitmap> cache;

    public VolleyHelper(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
        cache = new LruCache<String, Bitmap>(1024 * 1024 * 30);
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        });
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public static VolleyHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (VolleyHelper.class) {
                if (instance == null) {
                    instance = new VolleyHelper(context);
                }
            }
        }

        return instance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

}
