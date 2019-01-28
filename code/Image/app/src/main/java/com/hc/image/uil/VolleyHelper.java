package com.hc.image.uil;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.hc.image.MyApplication;
import com.hc.image.R;

/**
 * Created by liulei0905 on 2016/11/4.
 */

public enum VolleyHelper {
    SINGLETON_INSTANCE;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private LruCache<String, Bitmap> mCache = null;

    private VolleyHelper() {
        mRequestQueue = getRequestQueue();
        mCache = new LruCache<String, Bitmap>(1024 * 1024 * 30);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            @Override
            public Bitmap getBitmap(String url) {
                Bitmap tBitmap = mCache.get(url);
                return tBitmap;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });

    }

    public static synchronized VolleyHelper getInstance() {
        return SINGLETON_INSTANCE;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(MyApplication.getInstance().getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * 加载图片
     */
    public void loadImage(NetworkImageView niv, String url) {
        if (niv != null) {
            niv.setDefaultImageResId(R.drawable.img_head_icon_default);
            niv.setErrorImageResId(R.drawable.img_head_icon_default);
            niv.setImageUrl(url, mImageLoader);
        }
    }

    public void loadImage(NetworkImageView niv, String url, int defaultImgId) {
        if (niv != null) {
            niv.setDefaultImageResId(defaultImgId);
            niv.setErrorImageResId(defaultImgId);
            niv.setImageUrl(url, mImageLoader);
        }
    }

}
