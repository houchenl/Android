package com.hc.rxjava.basic;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class Basic {

    /**
     * 完整版本
     * */
    public void test1() {
        // Create a basic observable.
        // String is the type of value returned to subscriber.
        // observable is the observable to be subscribed.
        Observable<String> observable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    /*
                    * 1. called when a subscriber subscribe the observable.
                    * 2. String is the type of value returned by observable to subscriber.
                    * 3. subscriber is the one who subscribe the observable.
                    * */
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        // In here observable will emit items to subscriber and send value to it.
                        String value = "Hello RxJava!";
                        subscriber.onNext(value);
                        subscriber.onCompleted();
                    }
                }
        );

        // String is the type of value returned by observable when onNext().
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

        // Bind observable and subscriber.
        Subscription subscription = observable.subscribe(mySubscriber);
        subscription.unsubscribe();
    }

    /**
     * 简化Observable和Subscriber
     * */
    public void test2() {
        /*
        * 1. Observable.just()
        *    emit a single item then complete.
        *    just like subscriber.onNext(value), subscriber.onComplete().
        * */
        Observable<String> observable1 = Observable.just("Hello RxJava");

        /*
        * 2. We don't care about onComplete() nor onError()
        * */
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };

        // myObservable.subscribe(onNextAction, onErrorAction, onCompleteAction);
        observable1.subscribe(onNextAction);

        Observable.just("Hello RxJava")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    /**
     * Test map operator
     * Used to transform one emitted item into another.
     * */
    public void testMap() {
        Observable.just("Hello ")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + " RxJava.";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    public void testFlatMap() {
        // 1. normal
        query("hello").subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> strings) {
                for (String url : strings) {
                    System.out.println(url);
                }
            }
        });

        // 2. better
        query("hello")
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        // takes a collection of items and emits each them one at a time:
                        Observable.from(strings)
                                .subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String s) {
                                        System.out.println(s);
                                    }
                                });
                    }
                });

        // 3. best
        /*
        * Observable.flatMap() takes the emissions of one Observable and returns the emissions of
        * another Observable to take its place.
        * */
        query("hello")
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                })
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return getTitle(s);
                    }
                })
                // filter() emits the same item it received, but only if it passes the boolean check.
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s != null;
                    }
                })
                // only show 5 results at most.
                .take(5)
                // doOnNext() allows us to add extra behavior each time an item is emitted
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        // save data to disk.
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    private Observable<String> getTitle(String url) {
        return Observable.just(url);
    }

    private Observable<List<String>> query(String text) {
        return Observable.create(new Observable.OnSubscribe<List<String>>(){
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                List<String> list = new ArrayList<>();
                subscriber.onNext(list);
            }
        });
    }

}
