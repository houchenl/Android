package com.houchen.rxjava.operator;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TestCreate {

    private static final String TAG = "houchenl-Create";

    public void test() {
        Observable<Integer> result = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 在 RxJava 2.x 中，可以看到发射事件方法相比 1.x 多了一个 throws Excetion，意味着我们做一些特定操作再也不用 try-catch 了。

                Log.d(TAG, "Observable emit 1");
                emitter.onNext(1);
//                Log.d(TAG, "Observable emit 1");    // 这句代码的执行在Observer.onNext后

                Log.d(TAG, "Observable emit 2");
                emitter.onNext(2);

                Log.d(TAG, "Observable emit 3");
                emitter.onNext(3);

                emitter.onComplete();

                // 在发射事件中，我们在发射了数值 3 之后，直接调用了 e.onComplete()，虽然无法接收事件，但发送事件还是继续的。
                Log.d(TAG, "Observable emit 4");
                emitter.onNext(4);
            }
        });
        result.subscribe(new Observer<Integer>() {
            // 2.x 中有一个 Disposable 概念，这个东西可以直接调用切断，可以看到，当它的  isDisposed() 返回为 false 的时候，接收器能正常接收事件，但当其为 true 的时候，接收器停止了接收。所以可以通过此参数动态控制接收事件了。
            private Disposable mDisposable;
            private int i;

            @Override
            public void onSubscribe(Disposable d) {
                // 执行在发送事件前，只执行一次
                mDisposable = d;
                Log.d(TAG, "onSubscribe: isDisposable " + d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);

                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    // 不再接收上游事件后，onComplete也不会接收
                    mDisposable.dispose();
                    Log.d(TAG, "onNext: isDisposable " + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

}
