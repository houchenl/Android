package com.yulin.viewpager.size;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yulin.viewpager.Data;
import com.yulin.viewpager.R;

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
 * 获取网络图片尺寸
 */
public class GetImageSizeActivity extends AppCompatActivity {

    private static final String TAG = "GetImageSizeActivity";

    private StringBuffer sbText = new StringBuffer();

    private TextView mTvImageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_image_size);

        mTvImageSize = findViewById(R.id.tv_image_size);

        getNetworkImageSize2();
    }

    /**
     * 方法1：使用glide获取网络图片尺寸
     * 结果：使用glide加载出网络图片尺寸需要先把图片下载下来，耗时较长
     */
    private void getNetworkImageSize() {
        for (int i = 0; i < Data.aliyunImageUrls.length; i++) {
            if (i != 24) continue;
            final String url = Data.aliyunImageUrls[i];
            final long loadBeginTime = System.currentTimeMillis();
            Glide.with(this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    int width = resource.getWidth();
                    int height = resource.getHeight();
                    long currentTime = System.currentTimeMillis();
                    long duration = currentTime - loadBeginTime;
                    Log.d(TAG, "onResourceReady: loadBeginTime " + loadBeginTime + ", currentTime " + currentTime + ", duration " + duration);
//                    Log.d(TAG, "onResourceReady: url " + url + ", width " + width + ", height " + height + ", duration " + duration);
                    sbText.append("url: ").append(url).append(", width: ").append(width).append(", height: ")
                            .append(height).append(", duration: ").append(duration).append("\n");
                    mTvImageSize.setText(sbText.toString());
                }
            });
        }
    }

    /**
     * 方法2：使用数据流获取网络图片尺寸
     * 结果：不是把图片完整下载下来，耗时200ms左右
     * */
    private void getNetworkImageSize2() {
        for (int i = 0; i < Data.aliyunImageUrls.length; i++) {
            if (i != 9) continue;
            final String url = Data.aliyunImageUrls[i];
//            new GetImageSizeTask(url).execute();
            Disposable disposable = Observable.create(new ObservableOnSubscribe<BitmapFactory.Options>() {
                @Override
                public void subscribe(ObservableEmitter<BitmapFactory.Options> emitter) throws Exception {
                    try {
                        URL mUrl = new URL(url);
                        HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
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
                public void accept(BitmapFactory.Options options) throws Exception {
                    String msg = "url: " + url + ", width: " + options.outWidth + ", height: " + options.outHeight;
                    mTvImageSize.setText(msg);
                }
            });
        }
    }

}
