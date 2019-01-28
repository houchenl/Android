package com.yulin.poem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
        // 这里把apikey存放于manifest文件中，只是一种存放方式，
        // 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,
        // "api_key")
                // 启动百度push
                PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
                        Utils.getMetaValue(MainActivity.this, "api_key"));
    }

}
