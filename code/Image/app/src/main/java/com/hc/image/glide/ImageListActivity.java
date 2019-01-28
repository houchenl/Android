package com.hc.image.glide;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hc.image.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by liulei0905 on 2016/6/20.
 */

public class ImageListActivity extends AppCompatActivity {

    private ListView mList;
    private ArrayList<String> mUrls = new ArrayList<>();
    private MyAdapter adapter;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        inflater = LayoutInflater.from(this);
        Toast al;

        initDatas();
        adapter = new MyAdapter();
        mList = (ListView) findViewById(R.id.list);
        mList.setAdapter(adapter);
    }

    private void initDatas() {
        mUrls.add("http://img3.fengniao.com/forum/attachpics/919/27/36725227_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/919/27/36725228_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/919/27/36725229_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/919/27/36725230_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/919/27/36725231_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/919/27/36725232_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/919/27/36725233_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/919/27/36725234_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/919/27/36725235_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/919/27/36725236_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/916/139/36627786_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/916/139/36627787_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/916/139/36627788_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/916/139/36627789_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/916/139/36627790_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/916/139/36627791_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/916/139/36627792_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/916/139/36627793_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/916/139/36627794_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/916/139/36627795_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/914/101/36540086_1024.jpg");
        mUrls.add("http://img3.fengniao.com/forum/attachpics/914/101/36540087_1024.jpg");
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mUrls.size();
        }

        @Override
        public String getItem(int i) {
            return mUrls.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.layout_list_item, viewGroup, false);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            Glide
                    .with(ImageListActivity.this)
                    .load(getItem(i))
                    .into(vh.img);

            return convertView;
        }

        class ViewHolder {
            public ImageView img;
            public ViewHolder(View layout) {
                img = (ImageView) layout.findViewById(R.id.item_img);
            }
        }
    }

}
