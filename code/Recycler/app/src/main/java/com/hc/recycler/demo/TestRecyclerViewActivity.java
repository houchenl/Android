package com.hc.recycler.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.hc.recycler.R;
import com.hc.recycler.Util;
import com.hc.recycler.databinding.ActivityTestRecyclerViewBinding;
import com.hc.recycler.demo.model.Poem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liulei on 2017/5/10.
 * 测试RecyclerView
 */

public class TestRecyclerViewActivity extends AppCompatActivity {

    ActivityTestRecyclerViewBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test_recycler_view);

        // 设置布局管理器
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 设置adapter
//        mBinding.recyclerView.setAdapter(new NormalAdapter(getTestData()));
        NormalAdapter adapter = new NormalAdapter(getTestData());
        mBinding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                switch (view.getId()) {
                    case R.id.root_layout:
                        Util.toast("onItemClick " + position);
                        break;
                    case R.id.tv_click:
                        Util.toast("clickMe " + position);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(int position, View view) {
                switch (view.getId()) {
                    case R.id.root_layout:
                        Util.toast("onItemClick " + position);
                        break;
                    case R.id.tv_click:
                        Util.toast("clickMe " + position);
                        break;
                }

                return false;
            }
        });

        //设置Item增加、移除动画
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());

        //添加分割线
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
    }

    private List<Poem> getTestData() {
        List<Poem> items = new ArrayList<>();

        items.add(new Poem("静夜思", "李白"));
        items.add(new Poem("静夜思", "李白"));
        items.add(new Poem("静夜思", "李白"));
        items.add(new Poem("静夜思", "李白"));
        items.add(new Poem("静夜思", "李白"));

        return items;
    }

}
