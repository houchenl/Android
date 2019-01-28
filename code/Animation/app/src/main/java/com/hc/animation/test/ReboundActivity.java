package com.hc.animation.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.hc.animation.R;

/**
 * Created by liulei on 2017/5/2.
 */

public class ReboundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebound);

        final ImageView myView = (ImageView) findViewById(R.id.iv_rebound);

        // Create a system to run the physics loop for a set of springs.
        SpringSystem springSystem = SpringSystem.create();

        // Add a spring to the system.
        Spring spring = springSystem.createSpring();

        // Add a listener to observe the motion of the spring.
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                myView.setScaleX(scale);
                myView.setScaleY(scale);
            }
        });

        // Set the spring in motion; moving from 0 to 1
        spring.setEndValue(1);
    }

}
