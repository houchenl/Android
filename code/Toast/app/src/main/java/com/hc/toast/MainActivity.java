package com.hc.toast;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mLlToast;
    private ImageView mIvLoading;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLlToast = findViewById(R.id.ll_toast_change_to_voice);
        mIvLoading = findViewById(R.id.iv_loading);
    }

    public void show(View view) {
        showTip();
    }

    private void showTip() {
        mLlToast.setVisibility(View.VISIBLE);

        Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_roate);
        mIvLoading.startAnimation(animRotate);

        mHandler.postDelayed(stopTask, 5000);
    }

    Runnable stopTask = new Runnable() {
        @Override
        public void run() {
            hideTip();
        }
    };

    private void hideTip() {
        mLlToast.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mHandler != null) {
            mHandler.removeCallbacks(stopTask);
            stopTask = null;
        }
    }

}
