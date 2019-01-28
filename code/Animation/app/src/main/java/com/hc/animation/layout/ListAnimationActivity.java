package com.hc.animation.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hc.animation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 为一个ViewGroup添加动画
 */
public class ListAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_anim);

        List<String> listDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            listDatas.add("草原 " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listDatas);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        LayoutAnimationController animationController = new LayoutAnimationController(
                AnimationUtils.loadAnimation(this, R.anim.zoom_in));
        animationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        listView.setLayoutAnimation(animationController);
        listView.startLayoutAnimation();
    }

}
