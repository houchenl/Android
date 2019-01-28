package com.houchen.rxjava.operator;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MapOperator {
    private static final String TAG = "houchenl-MapOperator";

    /*
    * map操作符对每一个发送的事件应用一个函数，在函数中做变化
    * map 基本作用就是将一个 Observable 通过某种函数关系，转换为另一种 Observable
    * */

    public void test() {
        Disposable result = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Observable emit 1");
                emitter.onNext(1);
                Log.d(TAG, "Observable emit 2");
                emitter.onNext(2);
                Log.d(TAG, "Observable emit 3");
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                Log.d(TAG, "map apply: " + integer);
                return "Result is " + integer * integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);
            }
        });
    }

}
