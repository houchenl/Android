package com.houchen.rxjava.operator;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ConcatOperator {

    private static final String TAG = "houchenl-ConcatOperator";

    /**
     * concat操作符把两个事件源发送的事件顺次连接在一起，变成一个事件源。
     * 注意！第1个事件源结束后，第2个事件源才会被连接进来，所以第一个事件源需要调用onComplete方法
     * 注意！两个事件源需要是同一个数据类型
     * */

    public void test() {
        Disposable result = Observable.concat(getStringObservable(), getStringObservable2()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);
            }
        });

        result = Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6)).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept: " + integer);
            }
        });
    }

    private Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "source1 isDisposed " + emitter.isDisposed());
                if (!emitter.isDisposed()) {
                    Log.d(TAG, "String emit A");
                    emitter.onNext("A");

                    Log.d(TAG, "String emit B");
                    emitter.onNext("B");

                    Log.d(TAG, "String emit C");
                    emitter.onNext("C");

                    emitter.onComplete();
                }
            }
        });
    }

    private Observable<String> getStringObservable2() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "source2 isDisposed " + emitter.isDisposed());
                if (!emitter.isDisposed()) {
                    Log.d(TAG, "String emit a");
                    emitter.onNext("a");

                    Log.d(TAG, "String emit b");
                    emitter.onNext("b");

                    Log.d(TAG, "String emit c");
                    emitter.onNext("c");

                    emitter.onComplete();
                }
            }
        });
    }

    private Observable<Integer> getIntegerObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    Log.d(TAG, "Integer emit 1");
                    emitter.onNext(1);

                    Log.d(TAG, "Integer emit 2");
                    emitter.onNext(2);

//                    Log.d(TAG, "Integer emit 3");
//                    emitter.onNext(3);
//
//                    Log.d(TAG, "Integer emit 4");
//                    emitter.onNext(4);
//
//                    Log.d(TAG, "Integer emit 5");
//                    emitter.onNext(5);
                }
            }
        });
    }

}
