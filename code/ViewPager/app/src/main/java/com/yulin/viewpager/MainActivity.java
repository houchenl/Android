package com.yulin.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yulin.viewpager.fragment.BasicFragmentSlideActivity;
import com.yulin.viewpager.image.ImageGridActivity;
import com.yulin.viewpager.size.GetImageSizeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startTextSlide(View view) {
        startActivity(new Intent(this, BasicFragmentSlideActivity.class));
    }

    public void startImagePreview(View view) {
        startActivity(new Intent(this, ImageGridActivity.class));
    }

    public void getImageSize(View view) {
        startActivity(new Intent(this, GetImageSizeActivity.class));
    }

}
