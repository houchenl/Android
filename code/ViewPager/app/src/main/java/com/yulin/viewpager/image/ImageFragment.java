package com.yulin.viewpager.image;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yulin.viewpager.GlideApp;
import com.yulin.viewpager.R;
import com.yulin.viewpager.Tool;
import com.yulin.viewpager.photoview.PhotoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 获取缩放后的图片
 * https://help.aliyun.com/document_detail/44688.html
 * <p>
 * 获取图片信息
 * https://help.aliyun.com/document_detail/44975.html?spm=a2c4g.11186623.6.1220.61a5c1f6j88gPm#h2-url-2
 */
public class ImageFragment extends Fragment implements View.OnClickListener {

    private static final String EXTRA_IMAGE = "extra_image";
    private static final String EXTRA_POSITION = "extra_position";

    private String mImagePath;

    private ProgressBar mLoadingBar;
    private PhotoView mImageView;
    private TextView mTvImageSize;

    private Disposable disposable;
    private String fileSize;

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
        mTvImageSize = view.findViewById(R.id.tv_origin_image_size);

        mImageView.setOnClickListener(this);
        mTvImageSize.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // load image onResume
        loadOriginImageFromCache();
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
        } else if (vid == R.id.tv_origin_image_size) {
            loadImageFromNetwork(mImagePath);
            mTvImageSize.setVisibility(View.GONE);
        }
    }

    /**
     * 显示图片
     * 1. 从缓存中加载原图，如果加载成功，return，否则继续
     * 2. 从缓存中加载大图(宽度等于屏幕)，如果加载成功，return，否则继续从网络获取图片
     * 3. 获取网络图片宽高，本机屏幕宽高，判断是否显示原图
     * 4. 若直接显示原图，加载并显示原图
     * 5. 若先显示大图，加载显示大图，并显示查看原图按钮
     */
    private void loadOriginImageFromCache() {
        // 只从缓存加载原图，如果失败，继续从网络获取
        GlideApp
                .with(this)
                .load(mImagePath)
                .onlyRetrieveFromCache(true)
                .error(loadBigImageFromCache())
                .into(mImageView);
    }

    /**
     * 从缓存中加载大图(宽度等于屏幕宽度)
     */
    private RequestBuilder<Drawable> loadBigImageFromCache() {
        return GlideApp
                .with(this)
                .load(getBigImagePath())
                .onlyRetrieveFromCache(true)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        getNetworkImageSize(true);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        getNetworkImageSize(false);
                        return false;
                    }
                });
    }

    /**
     * 如果缓存中有大图，那么获取图片信息后，就不用再加载图片
     * @param isLoad 获取图片信息后，是否需要加载
     * */
    private void getNetworkImageSize(final boolean isLoad) {
        mLoadingBar.setVisibility(View.VISIBLE);
        disposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                getImageInfo(emitter);
            }
        })
                .map(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(String responseJson) {
                        return parseImageInfo(responseJson);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isDownloadOrigin) {
                        mLoadingBar.setVisibility(View.GONE);
                        mTvImageSize.setText(getResources().getString(R.string.load_origin_image, fileSize));

                        // 如果需要下载原图，就显示查看原图按钮，否则隐藏
                        mTvImageSize.setVisibility(isDownloadOrigin ? View.VISIBLE : View.GONE);

                        // 如果不需要加载图片，return
                        if (!isLoad) return;

                        // ?x-oss-process=image/resize,m_lfit,w_836
                        String imagePath = mImagePath;
                        if (isDownloadOrigin) {
                            imagePath = getBigImagePath();
                        }
                        loadImageFromNetwork(imagePath);
                    }
                });
    }

    /**
     * @param imagePath    网络图片地址
     */
    private void loadImageFromNetwork(String imagePath) {
        mLoadingBar.setVisibility(View.VISIBLE);

        GlideApp
                .with(this)
                .load(imagePath)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        mLoadingBar.setVisibility(View.GONE);
                        mTvImageSize.setVisibility(View.VISIBLE);
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

    private String getFormatSize(long bytes) {
        if (bytes > 1024 * 1024) {
            // 大于1M
            int size = (int) (bytes / 1024 / 1024);
            return size + "M";
        } else {
            // nK
            int size = (int) (bytes / 1024);
            return size + "K";
        }
    }

    private String getBigImagePath() {
        int requiredWidth = Tool.getScreenWidth(getActivity()) * 2 / 3;
        int requiredHeight = Tool.getScreenHeight(getActivity());
        return mImagePath + "?x-oss-process=image/resize,m_lfit,w_" + requiredWidth + ",h_" + requiredHeight;
    }

    /**
     * 获取图片信息
     * */
    private void getImageInfo(ObservableEmitter<String> emitter) {
        try {
            URL url = new URL(mImagePath + "?x-oss-process=image/info");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (200 == urlConnection.getResponseCode()) {
                InputStream is = urlConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while (-1 != (len = is.read(buffer))) {
                    baos.write(buffer, 0, len);
                    baos.flush();
                }
                String response = baos.toString("utf-8");
                emitter.onNext(response);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析图片信息，获取图片宽度、高度、大小
     * @return 是否下载原图
     * */
    private boolean parseImageInfo(String responseJson) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(responseJson);

            // get file size
            if (jsonObject.has("FileSize")) {
                JSONObject sizeObject = jsonObject.getJSONObject("FileSize");
                if (sizeObject != null && sizeObject.has("value")) {
                    String value = sizeObject.getString("value");
                    long size = Long.parseLong(value);
                    fileSize = getFormatSize(size);
                }
            }

            // get width
            int width = 0;
            if (jsonObject.has("ImageWidth")) {
                JSONObject widthObject = jsonObject.getJSONObject("ImageWidth");
                if (widthObject != null && widthObject.has("value")) {
                    String value = widthObject.getString("value");
                    width = Integer.parseInt(value);
                }
            }

            // get height
            int height = 0;
            if (jsonObject.has("ImageHeight")) {
                JSONObject heightObject = jsonObject.getJSONObject("ImageHeight");
                if (heightObject != null && heightObject.has("value")) {
                    String value = heightObject.getString("value");
                    height = Integer.parseInt(value);
                }
            }

            return Tool.isDownloadOriginImage(getActivity(), width, height);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return true;
    }

}
