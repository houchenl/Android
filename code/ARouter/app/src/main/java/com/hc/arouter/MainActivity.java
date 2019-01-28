package com.hc.arouter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start_second).setOnClickListener(this);
        findViewById(R.id.btn_start_user).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_second:
                // 应用内简单跳转
                ARouter.getInstance().build("/activity/second").navigation();
                break;
            case R.id.btn_start_user:
                // 带参数跳转
                ARouter.getInstance().build("/activity/user")
                        .withLong("key1", 123)
                        .withString("key2", "cn")
//                        .withObject("key3", new User("li", "mei"))
                        .navigation();
                break;
        }
    }

}
