package com.houchen.rxjava.operator;

public class Test {

    public void test() {
        //        // step 1: Hello World
//        Flowable.just("Hello World").subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
////                Log.d(TAG, "accept: " + s);
//            }
//        });
//
//        // step 2: common usage, run some code in background, and get result in UI thread
//        Flowable.fromCallable(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                Thread.sleep(5000);
//                return "Done";
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.single())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
////                        Log.d(TAG, "accept: " + s);
//                    }
//                });
//
//        // step 3: rewrite step 2 in details
//        Flowable<String> source = Flowable.fromCallable(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                Thread.sleep(2000);
//                return "Done in detail";
//            }
//        });
//        // 通常，可以通过subscribeOn将计算或阻塞IO操作转移到其它线程中
//        Flowable<String> runBackground = source.subscribeOn(Schedulers.io());
//        // 一旦数据准备好，你就可以确保数据可以通过observeOn转移到前台或ui
//        Flowable<String> showForeground = runBackground.observeOn(Schedulers.single());
//        showForeground.subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
////                Log.d(TAG, "accept: " + s);
//            }
//        });
//
//        // step 4: 按顺序串行执行
//        Flowable.range(1, 10)
//                .observeOn(Schedulers.computation())
//                .map(new Function<Integer, Integer>() {
//                    @Override
//                    public Integer apply(@NonNull Integer integer) throws Exception {
////                        Log.d(TAG, "apply: multiple " + integer);
//                        return integer * integer;
//                    }
//                })
//                .blockingSubscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
////                        Log.d(TAG, "accept: " + integer);
//                    }
//                });
//
//        // step 5: 并行执行
//        Flowable.range(1, 10)
//                .flatMap(new Function<Integer, Publisher<Integer>>() {
//                    @Override
//                    public Publisher<Integer> apply(@NonNull Integer integer) throws Exception {
//                        return Flowable.just(integer)
//                                .subscribeOn(Schedulers.computation())
//                                .map(new Function<Integer, Integer>() {
//                                    @Override
//                                    public Integer apply(@NonNull Integer integer) throws Exception {
////                                        Log.d(TAG, "apply: do multi in flat map " + integer);
//                                        return integer * integer;
//                                    }
//                                });
//                    }
//                })
//                .blockingSubscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
////                        Log.d(TAG, "flat map accept: " + integer);
//                    }
//                });
//
//        // step 6: 并行执行，使用操作符实现
//        Flowable.range(1, 10)
//                .parallel()
//                .runOn(Schedulers.computation())
//                .map(new Function<Integer, Integer>() {
//                    @Override
//                    public Integer apply(@NonNull Integer integer) throws Exception {
////                        Log.d(TAG, "apply: parallel multi " + integer);
//                        return integer * integer;
//                    }
//                })
//                .sequential()
//                .blockingSubscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
////                        Log.d(TAG, "parallel accept: " + integer);
//                    }
//                });
//
//        // step 7: 传统Observable模式
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onComplete();
//            }
//        });
//        Observer<Integer> observer = new Observer<Integer>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NonNull Integer integer) {
//                Log.d(TAG, "onNext: " + integer);
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete: ");
//            }
//        };
//        observable.subscribe(observer);
    }

}
