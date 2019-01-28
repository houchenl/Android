package com.hc.animation.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hc.animation.R;

/**
 * Created by liulei on 2017/5/2.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
        setContentView(R.layout.activity_test);

        findViewById(R.id.btn_rebound).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rebound:
                start(ReboundActivity.class);
                break;
        }
    }

    private void start(Class clz) {
        startActivity(new Intent(this, clz));
    }

}
