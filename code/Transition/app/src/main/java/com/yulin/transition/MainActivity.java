package com.yulin.transition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startSecond(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
        // 必须在 StartActivity() 或 finish() 之后立即调用。
        overridePendingTransition(android.R.anim.fade_in, 0);
        // enterAnim, 本次跳转过程中进入显示页面的动画
        // exitAnim, 本次跳转过程中退出显示页面的动画
    }

    public void finishActivity(View view) {
        finish();
    }

}
