package com.yulin.rxjava2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Flowable.just("Hello World").subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println(s);
//            }
//        });
//        Flowable.just("Hello World").subscribe(System.out::println);

//        Flowable<Integer> flow = Flowable.range(1, 5)
//                .map(v -> v * v)
//                .filter(v -> v % 3 == 0);
        // 到这里数据还没有流动

//        flow.subscribe(System.out::println);
        // 数据开始流动

//        Flowable.fromCallable(() -> {
//            Thread.sleep(3000);
//            return "Done";
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.single())
//                .subscribe(System.out::println, Throwable::printStackTrace);

//        observable1();
//        observable2();
//        observableJust();
//        observableFromArray();
//        observableFromCallable();
//        observableFromFuture();
        observableFromIterable();
    }

    /**
     * 经典模式：被观察者、观察者、订阅
     * 分别定义被观察者和观察者，再以订阅方法连接
     * */
    private void observable1() {
        // 创建被观察者，数据源对象
        Observable<String> novelObservable = Observable.create((emitter) -> {
            emitter.onNext("连载1");
            emitter.onNext("连载2");
            emitter.onNext("连载3");
            emitter.onComplete();
        });

        // 创建观察者
        Observer<String> reader = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
                mDisposable = d;
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        // 创建订阅关系
        novelObservable.subscribe(reader);
    }

    /**
     * 链式调用被观察者和观察者
     * */
    private void observable2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    private void observableJust() {
        Observable.just(111, 222, 333)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    private void observableFromArray() {
        String[] season = {"spring", "summer", "autumn", "winter"};

        Observable.fromArray(season)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(String season) {
                        System.out.println("onNext: " + season);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    private void observableFromCallable() {
        Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("accept: " + integer);
            }
        });
    }

    private void observableFromFuture() {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "返回结果";
            }
        });

        Observable.fromFuture(futureTask)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println("accept: start task");
                        futureTask.run();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("accept: " + s);
                    }
                });
    }

    private void observableFromIterable() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Observable.fromIterable(list)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

}
