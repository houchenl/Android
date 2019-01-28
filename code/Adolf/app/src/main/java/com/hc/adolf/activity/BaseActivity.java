package com.hc.adolf.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * Created by liulei0905 on 2016/8/4.
 * BaseActivity fro subActivity.
 * 模板方法模式
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBlock();
        initNetErrorBar();
    }

    protected abstract void initBlock();

    /**
     * 初始化网络连接提示
     * */
    private void initNetErrorBar() {

    }

    @Override
    public void finish() {
        closeKeyboard();
        super.finish();
    }

    private void closeKeyboard() {

    }

}
