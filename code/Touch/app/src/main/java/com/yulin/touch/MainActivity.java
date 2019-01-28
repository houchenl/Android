package com.yulin.touch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "houchen-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_velocity).setOnClickListener(this);
        findViewById(R.id.btn_scroll).setOnClickListener(this);
        findViewById(R.id.my_view).setOnClickListener(this);
        findViewById(R.id.btn_scroll_clickable).setOnClickListener(this);

        findViewById(R.id.my_view).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.d(TAG, "onTouch: return false");
                // 为view设置的setOnTouchListener的优先级高于View中的onTouchEvent方法，
                // 如果onTouch返回true，表明在这里处理MotionEvent事件，onTouchEvent方法将不再被调用。
                // 如果onTouch返回false，表明不处理事件，onTouchEvent方法将被调用。
//                return true;
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_velocity:
                startActivity(new Intent(this, VelocityActivity.class));
                break;
            case R.id.btn_scroll:
                startActivity(new Intent(this, ScrollActivity.class));
                break;
            case R.id.my_view:
                // 当View的onTouchEvent方法被调用，且onTouchEvent调用了super.onTouchEvent，且view设置了onClickListener时，
                // view的onClick方法才会被调用。以上3个条件任何一个不符合，onClick方法就不会被调用。
                Log.d(TAG, "onClick: ");
                break;
            case R.id.btn_scroll_clickable:
                startActivity(new Intent(this, ScrollClickableActivity.class));
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        Log.d(TAG, "onTouchEvent: return " + result + ", action " + event.getAction());
        return result;
    }

}
