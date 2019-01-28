package com.yulin.list.expand;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.yulin.list.R;

import java.util.ArrayList;

/**
 * Created by liu_lei on 2017/7/14.
 * 可折叠列表
 */

public class ExpandListActivity extends AppCompatActivity {

    private ArrayList<Group> mGroupData = new ArrayList<>();
    private ArrayList<ArrayList<Item>> mChildData = new ArrayList<>();

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_list);
        mContext = this;

        initData();
        MyExpandAdapter adapter = new MyExpandAdapter(mGroupData, mChildData, this);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expand_list);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                Toast.makeText(mContext, "你点击了：" + mChildData.get(groupPosition).get(childPosition).getName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void initData() {
        mGroupData.clear();
        mGroupData.add(new Group("诗词"));
        mGroupData.add(new Group("主题"));
        mGroupData.add(new Group("体裁"));
        mGroupData.add(new Group("风格"));
        mGroupData.add(new Group("朝代"));

        mChildData.clear();
        for (int i = 0; i < 5; i++) {
            ArrayList<Item> childs = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                childs.add(new Item("group " + i + " child " + j));
            }
            mChildData.add(childs);
        }
    }

}
