package com.hc.scrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_top).requestFocus();
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
        ListView mListView = (ListView) findViewById(R.id.listview);
        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(mListView);
//        scrollView.smoothScrollTo(0, 0);
    }

    private static class MyAdapter extends BaseAdapter {

        private List<String> listItems;

        MyAdapter() {
            listItems = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                listItems.add("Item " + (i + 1));
            }
        }

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public String getItem(int i) {
            return listItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, final ViewGroup viewGroup) {
            View convertView = View.inflate(viewGroup.getContext(), R.layout.list_item, null);
            TextView tv = (TextView) convertView.findViewById(R.id.item_tv);
            tv.setText(getItem(i));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(viewGroup.getContext(), getItem(i), Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}
