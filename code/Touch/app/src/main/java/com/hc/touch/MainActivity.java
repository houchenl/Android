package com.hc.touch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SimpleAdapter;

import com.yulin.touch.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "houchen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_list);

        initList();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "MainActivity:dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);    // 事件分发给子View的dispatchTouchEvent消费
//        return true;    // 事件在当前Activity当前方法内消费，不会继续传递
//        return false;    // 事件在当前Activity当前方法内消费，不会继续传递
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "MainActivity:onTouchEvent: ");
        return super.onTouchEvent(event);
    }

    private void initList() {
        MyList myList = (MyList) findViewById(R.id.list);
        List<Map<String, String>> listItems = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Map<String, String> map = new HashMap();
            map.put("name", "Item " + (i + 1));
            listItems.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, android.R.layout.simple_list_item_1, new String[]{"name"}, new int[]{android.R.id.text1});
        myList.setAdapter(adapter);
    }

}
