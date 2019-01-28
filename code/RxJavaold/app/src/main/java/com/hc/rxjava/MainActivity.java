package com.hc.rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_test_rxjava).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test_rxjava:
                testRxJava();
                break;
            default:
                break;
        }
    }

    private void testRxJava() {
        // 方案1，完整实现方式
        /*// 1. 创建被监听者，决定什么时候触发事件，以及触发什么样的事件
        Observable<String> mObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("Hello RxJava.");
                        subscriber.onCompleted();
                    }
                }
        );

        // 2. 创建监听者
        Subscriber<String> mSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("complete");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

        // 3. 关联
        mObservable.subscribe(mSubscriber);*/

        // 方案2，简约形式
        Observable
                // just()方法适用于只发送一个事件的场合
                .just("Hello RxJava.")
                // subscribe()方法有一个重载版本，接收3个Action1类型参数，分别对应onNext/onComplete/onError
                .subscribe(
                        new Action1<String>() {
                            @Override
                            public void call(String s) {
                                System.out.println(s);
                            }
                        }
                );
    }

    /**
     * 变换事件中的消息
     */
    private void testChange() {
        /*
        * 方案1，直接在Observable对象中修改。
        * 局限在于有些Observable对象是第三方库提供的，可能改不了。
        * 也有可能一直Observable对象被多个地方订阅，而只希望一个订阅者改变，所以这种方式不好。
        * */

        /*
        * 方案2，在Subscriber中修改，而Subscriber运行在主线程中，这样会使主线程做更多的工作，也不好。
        * */

        /*
        * 方案3，使用操作符在Observable和最终的Subscriber之间修改Observable发出的事件
        * */
        Observable
                .just("Hello RxJava.")
                .map(
                        new Func1<String, Integer>() {
                            @Override
                            public Integer call(String s) {
                                return s.hashCode();
                            }
                        }
                )
                .map(
                        new Func1<Integer, String>() {
                            @Override
                            public String call(Integer integer) {
                                return Integer.toString(integer);
                            }
                        }
                )
                .subscribe(
                        new Action1<String>() {
                            @Override
                            public void call(String s) {
                                System.out.println(s);
                            }
                        }
                );

        /*
        * 方案4，输出Observable中数据是集合时，flatMap接收集合作为参数，一个一个输出其中数据是单个的Observable对象
        * */
        query("key")
                .flatMap(
                        new Func1<List<String>, Observable<String>>() {
                            @Override
                            public Observable<String> call(List<String> strings) {
                                return Observable.from(strings);
                            }
                        }
                )
                .flatMap(
                        new Func1<String, Observable<String>>() {
                            @Override
                            public Observable<String> call(String url) {
                                return getTitle(url);
                            }
                        }
                )
                .filter(
                        new Func1<String, Boolean>() {
                            @Override
                            public Boolean call(String s) {
                                return s != null;
                            }
                        }
                )
                .take(5)
                .doOnNext(
                        new Action1<String>() {
                            @Override
                            public void call(String s) {
                                // save s
                            }
                        }
                )
                .subscribe(
                        new Action1<String>() {
                            @Override
                            public void call(String s) {
                                System.out.println(s);
                            }
                        }
                );
    }

    private Observable<List<String>> query(String key) {
        List<String> list = new ArrayList<>();
        return Observable.just(list);
    }

    private Observable<String> getTitle(String url) {
        return Observable.just("title");
    }

    private void addImages() {
        List<File> folders = new ArrayList<>();

        Observable.from(folders)
                .flatMap(
                        new Func1<File, Observable<File>>() {
                            @Override
                            public Observable<File> call(File file) {
                                return Observable.from(file.listFiles());
                            }
                        }
                )
                .filter(
                        new Func1<File, Boolean>() {
                            @Override
                            public Boolean call(File file) {
                                return file.getName().endsWith(".png");
                            }
                        }
                )
                .map(
                        new Func1<File, Bitmap>() {
                            @Override
                            public Bitmap call(File file) {
                                return getBitmapFromFile(file);
                            }
                        }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Bitmap>() {
                            @Override
                            public void call(Bitmap bitmap) {
                                // add bitmap to image view.
                            }
                        }
                );
    }

    private Bitmap getBitmapFromFile(File file) {
        return BitmapFactory.decodeFile(file.getPath());
    }

}
