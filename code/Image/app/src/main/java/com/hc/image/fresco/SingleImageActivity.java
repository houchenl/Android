package com.hc.image.fresco;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hc.image.R;

/**
 * Created by liulei0905 on 2016/6/20.
 */

public class SingleImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_single_img);

        String path = "http://img3.duitang.com/uploads/item/201504/13/20150413H5548_BuNcZ.thumb.700_0.jpeg";
        Uri uri = Uri.parse(path);

        SimpleDraweeView img = (SimpleDraweeView) findViewById(R.id.img);
        img.setImageURI(uri);
    }

}
