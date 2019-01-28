package com.hc.rxjava.rxbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hc.rxjava.R;

import rx.Subscription;
import rx.functions.Action1;

public class BusActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "houchen";

    private Subscription mSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_test_rxjava).setOnClickListener(this);

        regist();
    }

    private void regist() {
        mSubscription = RxBus.INSTANCE.toObservable(UserEvent.class)
                .subscribe(new Action1<UserEvent>() {
                    @Override
                    public void call(UserEvent userEvent) {
                        Log.d(TAG, "call: " + userEvent.toString());
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();

        mSubscription.unsubscribe();
    }

    @Override
    public void onClick(View view) {
        RxBus.INSTANCE.post(new UserEvent(1, "yoyo"));
    }

}
