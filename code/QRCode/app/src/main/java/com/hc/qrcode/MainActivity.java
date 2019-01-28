package com.hc.qrcode;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivQrCode = findViewById(R.id.iv_qr_code);
        findViewById(R.id.btn_generate).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String url = "http://www.qq.com";
        Bitmap bitmap = QRCodeUtil.createQRCode(url);
        ivQrCode.setImageBitmap(bitmap);
    }

}
