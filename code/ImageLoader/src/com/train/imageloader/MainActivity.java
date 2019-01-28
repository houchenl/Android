package com.train.imageloader;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    
    private List<String> listDatas = new ArrayList<String>();
    
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        imageLoader = VolleyHelper.getInstance(this).getImageLoader();
        
        initDatas();
        MyAdapter adapter = new MyAdapter(this);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
    
    private void initDatas() {
        listDatas.add("http://pic.818today.com/imgsy/image/2015/1223/6358646689684556034921399.jpg");
        listDatas.add("http://pic.818today.com/imgsy/image/2016/0113/6358829091060124071821971.jpg");
        listDatas.add("http://pic.818today.com/imgsy/image/2016/0113/6358829473437075684418360.jpg");
        listDatas.add("http://image.xinmin.cn/2015/07/11/20150711092332873106.jpg");
        listDatas.add("http://finance.gucheng.com/UploadFiles_7830/201512/2015122514490203.jpg");
        listDatas.add("http://img.67.com/upload/images/2015/02/22/1424566290_963023376.jpg");
        listDatas.add("http://img1.imgtn.bdimg.com/it/u=3263061973,745170726&fm=11&gp=0.jpg");
    }
    
    private class MyAdapter extends BaseAdapter {
        
        private LayoutInflater inflater;
        
        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return listDatas.size();
        }

        @Override
        public String getItem(int position) {
            return listDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item, parent, false);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            
            String url = getItem(position);
            vh.niv.setDefaultImageResId(R.drawable.ic_launcher);
            vh.niv.setErrorImageResId(R.drawable.ic_launcher);
            vh.niv.setImageUrl(url, imageLoader);
            
            return convertView;
        }
        
        private class ViewHolder {
            public NetworkImageView niv;
            
            public ViewHolder(View convertView) {
                niv = (NetworkImageView) convertView.findViewById(R.id.listitem_img_icon);
            }
        }
        
    }
    
}
