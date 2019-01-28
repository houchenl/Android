package com.hc.sp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvStatus = findViewById(R.id.tv_status);
        findViewById(R.id.btn_set_integer).setOnClickListener(this);
        findViewById(R.id.btn_get_integer).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_integer:
                int age = SharedPrefUtil.getInstance().get("age", 25);
                mTvStatus.setText("" + age);
                break;
            case R.id.btn_set_integer:
                SharedPrefUtil.getInstance().put("age", 28);
                break;
        }
    }

}
