package com.hc.customview.hot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hc.customview.R;

/**
 * Created by liu_lei on 2017/7/31.
 */

public class HotWordActivity extends AppCompatActivity {

    private static final String TAG = "HotWordActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_word);

        HotWordContainer container = (HotWordContainer) findViewById(R.id.hot_word_container);

        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < 20; i++) {
            View view = inflater.inflate(R.layout.item_hot_word, container, false);
            TextView tv = (TextView) view.findViewById(R.id.tv_hot_word);
            tv.setText("你好世界你好世界" + i);
            container.addChildView(view, i);
        }

        container.setListener(new HotWordContainer.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Log.d(TAG, "onItemClick: " + pos);
            }
        });
    }

}
