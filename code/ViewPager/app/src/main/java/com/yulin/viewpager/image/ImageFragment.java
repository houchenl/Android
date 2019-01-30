package com.yulin.viewpager.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;

import com.yulin.viewpager.R;

public class ImageFragment extends Fragment implements View.OnClickListener {

    private static final String EXTRA_IMAGE = "extra_image";
    private static final String EXTRA_POSITION = "extra_position";
    private static final String TAG = "houchenl-ImageFragment";

    private String mUrl;
    private int mPosition;

    private ProgressBar mLoadingBar;
    private PhotoView mImageView;

    public static ImageFragment getInstance(String url, int position) {
        Bundle args = new Bundle();
        args.putString(EXTRA_IMAGE, url);
        args.putInt(EXTRA_POSITION, position);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(EXTRA_IMAGE)) {
            mUrl = arguments.getString(EXTRA_IMAGE);
        }
        if (arguments != null && arguments.containsKey(EXTRA_POSITION)) {
            mPosition = arguments.getInt(EXTRA_POSITION);
        }

        Log.d(TAG, "onCreate: " + mPosition);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: " + mPosition);

        View view = inflater.inflate(R.layout.fragment_image, container, false);
        mLoadingBar = view.findViewById(R.id.loading);
        mImageView = view.findViewById(R.id.image);

        mImageView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: " + mPosition);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: " + mPosition);

        // load image onResume
        final Context context = getContext();
        if (context != null) {
            Glide.with(context).load(mUrl).addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
                                            boolean isFirstResource) {
                    mLoadingBar.setVisibility(View.GONE);
                    Toast.makeText(context, "load fail", Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                               DataSource dataSource, boolean isFirstResource) {
                    mLoadingBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(mImageView);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: " + mPosition);
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop: " + mPosition);

        // clear(view)不会停止下载图片，但可以保证下载完成后不再显示图片内容到view，这样可以保证列表中图片不乱序
        Glide.with(this).clear(mImageView);
        mLoadingBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: " + mPosition);

    }

    @Override
    public void onClick(View v) {
        Activity activity = getActivity();
        if (activity instanceof ImagePreviewActivity) {
            ImagePreviewActivity previewActivity = (ImagePreviewActivity) activity;
            previewActivity.finishActivity();
        }
    }

}
