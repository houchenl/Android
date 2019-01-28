package com.hc.picasso.show;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.hc.picasso.R;
import com.squareup.picasso.Picasso;

/**
 * Created by liulei0905 on 2016/6/20.
 */

public class ShowImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        mContext = this;

        img = (ImageView) findViewById(R.id.img);
        findViewById(R.id.btn_show_normal).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_normal:
                showNormalImage("http://pic24.nipic.com/20121003/10754047_140022530392_2.jpg");
                break;
        }
    }

    private void showNormalImage(String path) {
        Picasso.with(mContext).load(path).into(img);
    }

}
