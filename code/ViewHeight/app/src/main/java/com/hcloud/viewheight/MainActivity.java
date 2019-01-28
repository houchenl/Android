package com.hcloud.viewheight;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout layoutRoot;
    private EditText et;
    private TextView tv;

    private int rootWidth, rootHeight, etWidth, etHeight, tvWidth, tvHeight, softHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("houchen", "onCreate");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        layoutRoot = (RelativeLayout) findViewById(R.id.layout_root);
        et = (EditText) findViewById(R.id.et);
        tv = (TextView) findViewById(R.id.tv);

        // 监听视图树的布局改变
        layoutRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                layoutRoot.getWindowVisibleDisplayFrame(rect);

                // 整个屏幕最高处作为Y方向的0位置，向下递加，在状态栏之上
                // screenHeight: 整个屏幕去除底部导航栏之外剩余部分的像素高度，包括状态栏的高度
                int screenHeight = layoutRoot.getRootView().getHeight();
                // rect: 布局显示部分，top和bottom的像素高度从整个屏幕最高处开始算起
//                softHeight = screenHeight - (rect.bottom - rect.top) - rect.top;
                softHeight = screenHeight - rect.bottom;
                Log.d("houchen", "onGlobalLayout, screenHeight: " + screenHeight + ", bottom: "
                    + rect.bottom + ", top: " + rect.top + ", softHeight: " + softHeight);

                refreshWidthHeight();
                refreshText("onGlobalLayout");
//                layoutRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        refreshWidthHeight();
        refreshText("onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("houchen", "onResume");

        refreshWidthHeight();
        refreshText("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("houchen", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    private void refreshWidthHeight() {
        rootWidth = layoutRoot.getWidth();
        rootHeight = layoutRoot.getHeight();
        etWidth = et.getWidth();
        etHeight = et.getHeight();
        tvWidth = tv.getWidth();
        tvHeight = tv.getHeight();
    }

    private void refreshText(String pre) {
        StringBuffer sb = new StringBuffer(pre);
        sb.append("\n");

        sb.append("layoutRoot, width: " + rootWidth + ", height: " + rootHeight);
        sb.append("\n");

        sb.append("editText, width: " + etWidth + ", height: " + etHeight);
        sb.append("\n");

        sb.append("textView, width: " + tvWidth + ", height: " + tvHeight);
        sb.append("\n");

        sb.append("soft, height: " + softHeight);

        tv.setText(sb.toString());
    }

}
