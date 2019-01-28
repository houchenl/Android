package com.yulin.touch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

public class VelocityActivity extends AppCompatActivity implements View.OnTouchListener {

    private VelocityTracker mVelocityTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_velocity);

        findViewById(R.id.view).setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        acquireVelocityTracker(event);

        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.computeCurrentVelocity(1000);
                int xVelocity = (int) mVelocityTracker.getXVelocity();
                int yVelocity = (int) mVelocityTracker.getYVelocity();
                Log.d("houchen", "x: " + xVelocity + " y: " + yVelocity);
                break;
            case MotionEvent.ACTION_UP:
                releaseVelocityTracker();
                break;
        }

        return true;
    }

    private void acquireVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        mVelocityTracker.addMovement(event);
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

}
