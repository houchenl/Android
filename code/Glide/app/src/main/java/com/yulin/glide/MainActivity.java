package com.yulin.glide;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageView mIv;
    private View mask;

    private static final String ADDRESS = "http://desk.fd.zol-img.com.cn/t_s1920x1200c5/g5/M00/03/04/ChMkJ1oJW5SIfKCrAAs6A-fVlFQAAiH8gJQcWwACzob837.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIv = findViewById(R.id.iv);
        mask = findViewById(R.id.mask);


        Glide
                .with(this)
                .load(ADDRESS)
//                .placeholder(R.drawable.uvc_loading)
                .into(new GlideDrawableImageViewTarget(mIv) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.d(TAG, "onStart: ");
                    }

                    @Override
                    public void onStop() {
                        super.onStop();
                        Log.d(TAG, "onStop: ");
                    }

                    @Override
                    public void onDestroy() {
                        super.onDestroy();
                        Log.d(TAG, "onDestroy: ");
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                        super.onLoadCleared(placeholder);
                        Log.d(TAG, "onLoadCleared: ");
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        Log.d(TAG, "onLoadFailed: ");
                    }

                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        Log.d(TAG, "onLoadStarted: ");
                        mask.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        Log.d(TAG, "onResourceReady: ");
                        mask.setVisibility(View.GONE);
                    }
                });
    }

}
