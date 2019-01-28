package com.houchen.rxjava.operator;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class ZipOperator {

    private static final String TAG = "houchenl-ZipOperator";

    /*
    * 运行过程：source1发射的事件先发射完成；然后source2中的事件开始发射，并依次和source1中已发射的事件结合；
    * 当source1或source2中某个事件源先没有事件时，停止合并，但事件多出的一方仍继续发射事件，但不会被监听处理
    *
    * zip操作符对source1和source2两个事件源发射的事件，依次两两配对，然后传递给一个映射函数，生成一个新的事件；
    * zip操作符发送的事件数和两个事件源发射事件少的一方相同；
    * */

    public void test() {
        Disposable result = Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                Log.d(TAG, "apply: " + s + ", " + integer);
                return s + " " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);
            }
        });
    }

    private Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    Log.d(TAG, "String emit A");
                    emitter.onNext("A");

                    Log.d(TAG, "String emit B");
                    emitter.onNext("B");

                    Log.d(TAG, "String emit C");
                    emitter.onNext("C");
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
