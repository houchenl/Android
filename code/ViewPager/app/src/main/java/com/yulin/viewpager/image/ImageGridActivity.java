package com.yulin.viewpager.image;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yulin.viewpager.Data;
import com.yulin.viewpager.R;
import com.yulin.viewpager.Tool;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageGridActivity extends AppCompatActivity implements ImageGridAdapter.OnItemClickListener {

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
        int titleBarHeight = Tool.getTitleBarHeight(this);
        ImagePreviewActivity.startActivity(this, images, position, x, y, width, height, titleBarHeight);
    }

    private void initData() {
        images.addAll(Arrays.asList(Data.aliyunImageUrls));
    }

}
