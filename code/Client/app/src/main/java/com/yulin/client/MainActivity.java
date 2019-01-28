package com.yulin.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private TextView mTvMsg;
    private static final String URL = "http://192.168.2.103:9999/chemayi/app/sa/detail/page";
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkManager.getInstance().doInit(this);

        mTvMsg = (TextView) findViewById(R.id.tv_msg);
    }

    public void request(View view) {
        String tag = System.currentTimeMillis() + "";
        mSubscription = NetworkManager.getInstance().httpGet(URL, tag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mTvMsg.setText("error " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                mTvMsg.setText(s);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }
}
