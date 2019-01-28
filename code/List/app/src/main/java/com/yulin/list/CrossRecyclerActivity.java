package com.yulin.list;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu_lei on 2017/7/11.
 */

public class CrossRecyclerActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private List<String> items = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_recycler);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        if (swipeRefreshLayout != null) {
            int[] colors = getResources().getIntArray(R.array.swipe_refresh_colors);
            swipeRefreshLayout.setColorSchemeColors(colors);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setEnabled(true);
        }

        initData();

        /*
        * 默认情况下，横向滑动的RecyclerView与ScrollView不会有冲突，不需特殊处理
        * 如果横向没去的RecyclerView左右滑动卡顿，可能是因为它与PtrFrameLayout冲突了
        * 使用AutoRefreshLayout时，不会影响RecyclerView的左右滑动
        * */
        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.setAdapter(new MyAdapter(items, R.layout.item_horizontal_text));

        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view_2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setAdapter(new MyAdapter(items, R.layout.item_horizontal_text));

        /*
        * 默认情况下，竖向滑动的列表滑动时，会先滑动该列表，当该列表滑动结果时，外层的ScrollView也会接着滑动。
        * 这样不合理，应该是直接就是外层ScrollView就可以滑动。
        * 但是，在LinearLayoutManager中复写canScrollVertically方法并return false时，会出现竖向列表显示不全的问题。
        * 解决办法是在RecyclerView外层嵌套一层RelativeLayout。
        * 如果只在外层嵌套一层RelativeLayout，而未复写canScrollVertically方法，虽然可以显示全列表，但是上下滑动会非常卡顿，所以需要复写，禁止列表的上下滑动处理。
        * */
        RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.recycler_view_3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerView3.setAdapter(new MyAdapter(items, R.layout.item_text));
    }

    private void initData() {
        items.clear();
        for (int i = 0; i < 10; i++) {
            items.add("Item " + i);
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshEnd();
            }
        }, 500);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyVh> {

        private List<String> items = new ArrayList<>();
        private int layoutId;

        MyAdapter(List<String> data, int id) {
            items = data;
            layoutId = id;
        }

        @Override
        public MyVh onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new MyVh(view);
        }

        @Override
        public void onBindViewHolder(MyVh holder, int position) {
            String name = items.get(position);
            holder.tvText.setText(name);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    class MyVh extends RecyclerView.ViewHolder {

        public TextView tvText;

        public MyVh(View itemView) {
            super(itemView);

            tvText = itemView.findViewById(R.id.tv_text);
        }

    }

    /**
     * 设置是否启用下拉刷新
     *
     * @param enabled 是否允许下拉刷新
     */
    public void setPullDownRefreshEnabled(boolean enabled) {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setEnabled(enabled);
    }

    /**
     * 刷新结束
     */
    public void refreshEnd() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
