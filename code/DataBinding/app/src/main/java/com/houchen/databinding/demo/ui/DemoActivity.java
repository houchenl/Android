package com.houchen.databinding.demo.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.houchen.databinding.BR;
import com.houchen.databinding.R;
import com.houchen.databinding.databinding.ActivityDemoBinding;
import com.houchen.databinding.demo.vm.UserInfo;

public class DemoActivity extends AppCompatActivity {

    UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDemoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
//        binding = ActivityDemoBinding.inflate(getLayoutInflater());

//        mUserInfo = new UserInfo("first", "last");
//        binding.setUser(mUserInfo);
//        binding.setVariable(BR.user, mUserInfo);
//        binding.textView.setText("");

        // public String xxx 和 getXxx()同时存在时
        mUserInfo = new UserInfo();
        binding.setUser(mUserInfo);
//        mUserInfo.firstName = "firstName";
//        mUserInfo.lastName = "lastName";
    }

    @Override
    protected void onResume() {
        super.onResume();

        mUserInfo.setFirstName("hello");
        mUserInfo.setLastName("world");
//        mUserInfo.firstName = "hello";
//        mUserInfo.lastName = "world";
    }

}
