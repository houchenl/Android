package com.yulin.viewpager.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.yulin.viewpager.R;
import com.yulin.viewpager.photoview.PhotoView;

public class ImageFragment extends Fragment implements View.OnClickListener {

    private static final String EXTRA_IMAGE = "extra_image";
    private static final String EXTRA_POSITION = "extra_position";

    private String mUrl;

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        mLoadingBar = view.findViewById(R.id.loading);
        mImageView = view.findViewById(R.id.image);

        mImageView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

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
    public void onStop() {
        super.onStop();

        // clear(view)不会停止下载图片，但可以保证下载完成后不再显示图片内容到view，这样可以保证列表中图片不乱序
        Glide.with(this).clear(mImageView);
        mLoadingBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        // 如果缩小，回复原来大小
        mImageView.setScale(1);

        Activity activity = getActivity();
        if (activity instanceof ImagePreviewActivity) {
            ImagePreviewActivity previewActivity = (ImagePreviewActivity) activity;
            previewActivity.finishActivity();
        }
    }

}
