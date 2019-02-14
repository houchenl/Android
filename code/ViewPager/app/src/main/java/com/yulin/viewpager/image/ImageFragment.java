package com.yulin.viewpager.image;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yulin.viewpager.GlideApp;
import com.yulin.viewpager.R;
import com.yulin.viewpager.Tool;
import com.yulin.viewpager.photoview.PhotoView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * https://help.aliyun.com/document_detail/44688.html
 * */
public class ImageFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ImageFragment";

    private static final String EXTRA_IMAGE = "extra_image";
    private static final String EXTRA_POSITION = "extra_position";

    private String mImagePath;

    private ProgressBar mLoadingBar;
    private PhotoView mImageView;
    private Button mBtnLoadOriginImage;

    private Disposable disposable;

    public static ImageFragment getInstance(String imagePath, int position) {
        Bundle args = new Bundle();
        args.putString(EXTRA_IMAGE, imagePath);
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
            mImagePath = arguments.getString(EXTRA_IMAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        mLoadingBar = view.findViewById(R.id.loading);
        mImageView = view.findViewById(R.id.image);
        mBtnLoadOriginImage = view.findViewById(R.id.btn_load_origin_image);

        mImageView.setOnClickListener(this);
        mBtnLoadOriginImage.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // load image onResume
        loadImageFromCache();
    }

    @Override
    public void onStop() {
        super.onStop();

        // clear(view)不会停止下载图片，但可以保证下载完成后不再显示图片内容到view，这样可以保证列表中图片不乱序
        Glide.with(this).clear(mImageView);
        mLoadingBar.setVisibility(View.GONE);

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onClick(View v) {
        final int vid = v.getId();

        if (vid == R.id.image) {
            // 如果缩小，回复原来大小
            mImageView.setScale(1);

            Activity activity = getActivity();
            if (activity instanceof ImagePreviewActivity) {
                ImagePreviewActivity previewActivity = (ImagePreviewActivity) activity;
                previewActivity.finishActivity();
            }
        } else if (vid == R.id.btn_load_origin_image) {
            loadImageFromNetwork(mImagePath, true);
        }
    }

    /**
     * 显示图片
     * 1. 首先只从缓存中加载原图，如果加载成功，return，如果加载失败，继续
     * 2. 获取网络图片宽高，本机屏幕宽高
     * 3. 判断是否显示原图
     * 4. 若直接显示原图，加载并显示原图
     * 5. 若先显示缩小版，加载显示缩小版，并显示显示原图按钮
     */
    private void loadImageFromCache() {
        // 只从缓存加载原图，如果失败，继续从网络获取
        GlideApp
                .with(this)
                .load(mImagePath)
                .onlyRetrieveFromCache(true)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.d(TAG, "onLoadFailed: " + mImagePath);
                        getNetworkImageSize();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d(TAG, "onResourceReady: " + mImagePath);
                        return false;
                    }
                }).into(mImageView);
    }

    private void getNetworkImageSize() {
        disposable = Observable.create(new ObservableOnSubscribe<BitmapFactory.Options>() {
            @Override
            public void subscribe(ObservableEmitter<BitmapFactory.Options> emitter) {
                try {
                    URL url = new URL(mImagePath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream is = connection.getInputStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(is, null, options);
                    emitter.onNext(options);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BitmapFactory.Options>() {
                    @Override
                    public void accept(BitmapFactory.Options options) {
                        int width = options.outWidth;
                        int height = options.outHeight;
                        boolean isDownloadOrigin = Tool.isDownloadOriginImage(getActivity(), width, height);

                        // ?x-oss-process=image/resize,m_lfit,w_836
                        String imagePath = mImagePath;
                        if (isDownloadOrigin) {
                            imagePath = mImagePath + "?x-oss-process=image/resize,m_lfit,w_" + Tool.getScreenWidth(getActivity());
                        }
                        loadImageFromNetwork(imagePath, !isDownloadOrigin);
                    }
                });
    }

    /**
     * @param imagePath    网络图片地址
     * @param isShowOrigin 是否是原图
     */
    private void loadImageFromNetwork(String imagePath, final boolean isShowOrigin) {
        mLoadingBar.setVisibility(View.VISIBLE);
        if (isShowOrigin) {
            mBtnLoadOriginImage.setVisibility(View.GONE);
        } else {
            mBtnLoadOriginImage.setVisibility(View.VISIBLE);
        }

        GlideApp
                .with(this)
                .load(imagePath)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        mLoadingBar.setVisibility(View.GONE);
                        if (isShowOrigin) {
                            mBtnLoadOriginImage.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(getContext(), R.string.load_image_fail, Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mLoadingBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(mImageView);
    }

}
