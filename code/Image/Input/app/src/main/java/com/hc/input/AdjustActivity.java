package com.hc.input;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by liulei0905 on 2016/5/4.
 */
public class AdjustActivity extends AppCompatActivity {

    /*
    * 1. 默认情况下，同adjustPan
    *
    * 2. manifest为activity指定为adjustNothing时，输入框获取焦点时，输入法会弹出，
    *    但是无法输入框是否会被遮挡，整个界面不做任何变化。如果输入法在界面底部，会被输入法遮挡。
    *
    * 3. manifest为activity指定为adjustPan时，输入法弹出时，如果输入法遮挡住输入框，输入框及整个页面会向上平移，
    *    直到输入法及输入框都能完整显示。如果输入法没有遮挡住输入框，页面不动。
    *
    * 4. manifest为activity指定为adjustResize时，窗口高度会自动更改以适应输入框显示，相对位置的输入框会在窗口
    *    高度变化后仍牌相对位置，绝对位置的输入框可以在窗口高度变化后上下滑动
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }
}
