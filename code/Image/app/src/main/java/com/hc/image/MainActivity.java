package com.hc.image;

//import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//import com.hc.image.camera.CaptureCameraActivity;
//import com.hc.image.http.HttpConnectionActivity;
//import com.hc.image.picasso.ImageListActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        findViewById(R.id.btn_capture_image).setOnClickListener(this);
//        findViewById(R.id.btn_picasso_img_list).setOnClickListener(this);
//        findViewById(R.id.btn_glide_img_list).setOnClickListener(this);
//        findViewById(R.id.btn_fresco_list).setOnClickListener(this);
//        findViewById(R.id.btn_fresco).setOnClickListener(this);
//        findViewById(R.id.btn_http).setOnClickListener(this);

//        int maxMemorySize = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024);
//        Toast.makeText(this, maxMemorySize + " MB", Toast.LENGTH_SHORT).show();    // 192 MB
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_capture_image:
//                startActivity(new Intent(this, CaptureCameraActivity.class));
//                break;
//            case R.id.btn_picasso_img_list:
//                startActivity(new Intent(this, ImageListActivity.class));
//                break;
//            case R.id.btn_glide_img_list:
//                startActivity(new Intent(this, com.hc.image.glide.ImageListActivity.class));
//                break;
//            case R.id.btn_fresco_list:
//                startActivity(new Intent(this, com.hc.image.fresco.ImageListActivity.class));
//                break;
//            case R.id.btn_fresco:
//                startActivity(new Intent(this, com.hc.image.fresco.SingleImageActivity.class));
//                break;
//            case R.id.btn_http:
//                startActivity(new Intent(this, HttpConnectionActivity.class));
//                break;
//        }
    }

}
