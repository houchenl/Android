package com.hc.image.fresco;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hc.image.R;

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
        Fresco.initialize(this);
        setContentView(R.layout.activity_list);

        inflater = LayoutInflater.from(this);

        initDatas();
        adapter = new MyAdapter();
        mList = (ListView) findViewById(R.id.list);
        mList.setAdapter(adapter);
    }

    private void initDatas() {
        mUrls.add("http://img4.imgtn.bdimg.com/it/u=1365080916,3639569330&fm=21&gp=0.jpg");
        mUrls.add("http://ww1.sinaimg.cn/crop.3.45.1919.1919.1024/6b805731jw1em0hze051hj21hk1isn5k.jpg");
        mUrls.add("http://img3.duitang.com/uploads/item/201504/13/20150413H5548_BuNcZ.thumb.700_0.jpeg");
        mUrls.add("http://ww2.sinaimg.cn/crop.0.0.1536.1536.1024/73213515jw8eswynq2pm8j216o16otce.jpg");
        mUrls.add("http://ww2.sinaimg.cn/crop.0.0.1080.1080.1024/0060HVQdjw8esl7mp9hpmj30u00u0acv.jpg");
        mUrls.add("http://ww2.sinaimg.cn/crop.0.0.1080.1080.1024/0062o3f0jw8epi5yvv8gjj30u00u0mzu.jpg");
        mUrls.add("http://tva2.sinaimg.cn/crop.72.0.1007.1007.1024/6a0bf347jw8er5bdo5q8zj20u00rz7a9.jpg");
        mUrls.add("http://ww2.sinaimg.cn/crop.0.0.1080.1080.1024/bd0b9290jw8ethx1k4tfcj20u00u0mzk.jpg");
        mUrls.add("http://img4.duitang.com/uploads/item/201407/08/20140708101547_c8MAA.jpeg");
        mUrls.add("http://img5q.duitang.com/uploads/item/201506/15/20150615160811_iG5vj.jpeg");
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
                convertView = inflater.inflate(R.layout.layout_list_item_fresco, viewGroup, false);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            Uri uri = Uri.parse(getItem(i));
            vh.img.setImageURI(uri);

            return convertView;
        }

        class ViewHolder {
            public SimpleDraweeView img;
            public ViewHolder(View layout) {
                img = (SimpleDraweeView) layout.findViewById(R.id.item_img_fresco);
            }
        }
    }

}
