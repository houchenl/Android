package com.hc.picasso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hc.picasso.list.ImageListActivity;
import com.hc.picasso.show.ShowImageActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_show_normal).setOnClickListener(this);
        findViewById(R.id.btn_img_list).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_normal:
                startActivity(new Intent(this, ShowImageActivity.class));
                break;
            case R.id.btn_img_list:
                startActivity(new Intent(this, ImageListActivity.class));
                break;
        }
    }

}
