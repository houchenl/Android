package com.yulin.client;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public enum NetworkManager {

    INSTANCE;

    private OkHttpClient mOkHttpClient;
    private Context mContext;

    public static NetworkManager getInstance() {
        return INSTANCE;
    }

    // should be called before getOkHttpClient.
    public void doInit(Context context) {
        mContext = context;
        getOkHttpClient();
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            if (mContext != null) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();

                builder.writeTimeout(10, TimeUnit.SECONDS);
                builder.connectTimeout(10, TimeUnit.SECONDS);
                builder.readTimeout(10, TimeUnit.SECONDS);
                int cacheSize = 10 * 1024 * 1024; // 10 MiB
                Cache cache = new Cache(mContext.getCacheDir(), cacheSize);
                builder.cache(cache);

                mOkHttpClient = builder.build();
            }
        }

        return mOkHttpClient;
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public void enqueue(Request request, Callback responseCallback) {
        if (getOkHttpClient() != null) {
            mOkHttpClient.newCall(request).enqueue(responseCallback);
        }
    }

    public void cancelRequest(String tag) {
        if (getOkHttpClient() == null) {
            return;
        }
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
    }

    public Observable<String> httpGet(final String reqUrl, final String tag) {
        return Observable.create(new Observable.OnSubscribe<byte[]>() {

            @Override
            public void call(final Subscriber<? super byte[]> subscriber) {
                if (TextUtils.isEmpty(reqUrl)) {
                    subscriber.onError(new Throwable("empty url to load " + reqUrl));
                }

                final Request.Builder reqBuilder = new Request.Builder();
                reqBuilder.url(reqUrl);
                reqBuilder.tag(tag);

                enqueue(reqBuilder.build(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(new Throwable(e.getMessage()));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        byte[] retBytes = response.body().bytes();
                        subscriber.onNext(retBytes);
                        subscriber.onCompleted();
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .flatMap(new Func1<byte[], Observable<String>>() {
            @Override
            public Observable<String> call(byte[] bytes) {
                if (bytes != null) {
                    try {
                        String jsonString = new String(bytes, "UTF-8");
                        return Observable.just(jsonString);
                    } catch (UnsupportedEncodingException e) {

                        return Observable.error(new Throwable("byte to json err"));
                    }
                }
                return Observable.error(new Throwable("respone empty"));
            }
        });
    }

}
