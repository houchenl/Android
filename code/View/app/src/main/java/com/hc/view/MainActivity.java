package com.hc.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.hc.view.views.CaseInfoLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private CaseInfoLayout caseInfoLayout;
    private ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv_bitmap);
        caseInfoLayout = findViewById(R.id.case_info_layout);
        findViewById(R.id.btn).setOnClickListener(this);
    }

    public Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
//            scrollView.getChildAt(i).setBackgroundColor(
//                    Color.parseColor("#ffffff"));
        }
        // 创建对应大小的bitmap
        Log.d(TAG, "getBitmapByView: width " + scrollView.getWidth() + ", height " + h);
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    @Override
    public void onClick(View v) {
        caseInfoLayout.addItem("初步诊断： ", "否认药物及食物过敏史1");
        caseInfoLayout.addItem("初步诊断： ", "否认药物及食物过敏史2");
        caseInfoLayout.addItem("初步诊断： ", "否认药物及食物过敏史3,否认药物及食物过敏史3,否认药物及食物过敏史3,否认药物及食物过敏史3,否认药物及食物过敏史3");
        caseInfoLayout.addItem("初步诊断： ", "否认药物及食物过敏史4");
        caseInfoLayout.addItem("初步诊断： ", "否认药物及食物过敏史5");
        caseInfoLayout.addItem("初步诊断： ", "否认药物及食物过敏史6");
        caseInfoLayout.addItem("初步诊断： ", "否认药物及食物过敏史7");
        caseInfoLayout.addItem("初步诊断： ", "否认药物及食物过敏史8");
        caseInfoLayout.addItem("初步诊断： ", "否认药物及食物过敏史9");
        caseInfoLayout.invalidate();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = getBitmapByView(caseInfoLayout);
                iv.setImageBitmap(bitmap);
            }
        }, 100);

    }
}
