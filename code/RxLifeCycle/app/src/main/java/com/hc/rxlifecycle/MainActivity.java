package com.hc.rxlifecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainActivity extends RxAppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * 在子类使用Observable中的compose操作符，调用，完成Observable发布的事件和当前的组件绑定，实现生命周期同步。
        * 从而实现当前组件生命周期结束时，自动取消对Observable订阅。
        * */
        Observable.interval(1, TimeUnit.SECONDS)
                .compose(this.<Long>bindToLifecycle())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        Log.d(TAG, "onNext: " + aLong);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });

        /*
        * 使用ActivityEvent类，其中的CREATE、START、 RESUME、PAUSE、STOP、 DESTROY分别对应生命周期内的方法。
        * 使用bindUntilEvent指定在哪个生命周期方法调用时取消订阅。
        * */
//        Observable.interval(1, TimeUnit.SECONDS)
//                .compose(this.<Long>bindUntilEvent(ActivityEvent.STOP))
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        Log.d(TAG, "onSubscribe: ");
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Long aLong) {
//                        Log.d(TAG, "onNext: " + aLong);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.e(TAG, "onError: ", e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "onComplete: ");
//                    }
//                });
    }

}
