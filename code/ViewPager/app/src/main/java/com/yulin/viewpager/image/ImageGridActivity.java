package com.yulin.viewpager.image;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yulin.viewpager.R;
import com.yulin.viewpager.Tool;

import java.util.ArrayList;

public class ImageGridActivity extends AppCompatActivity implements ImageGridAdapter.OnItemClickListener {

    private static final String TAG = "houchenl-ImageGrid";

    private ArrayList<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);

        initData();

        ImageGridAdapter adapter = new ImageGridAdapter(this, images);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setOnClickListener(this);
    }

    @Override
    public void onItemClick(int position, float x, float y, int width, int height) {
        Log.d(TAG, "onItemClick: position " + position);
        int titleBarHeight = Tool.getTitleBarHeight(this);
        ImagePreviewActivity.startActivity(this, images, position, x, y, width, height, titleBarHeight);
    }

    private void initData() {
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu01.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu02.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu03.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu04.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu05.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu06.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu07.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu08.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu09.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu10.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu11.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu12.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu13.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu14.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu15.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu16.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu17.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu18.jpg");
        images.add("https://yulinwork.oss-cn-hangzhou.aliyuncs.com/image/yunhaifu19.jpg");
    }

}
