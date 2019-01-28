package com.hc.fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hc.fragment.close.TestCloseActivity;
import com.hc.fragment.comm.CommuActivity;
import com.hc.fragment.multi.MultiPageActivity;
import com.hc.fragment.radius.RadiusActivity;
import com.hc.fragment.show.TestShowActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_fragment_fragment_communicate).setOnClickListener(this);
        findViewById(R.id.btn_test_close).setOnClickListener(this);
        findViewById(R.id.btn_test_corner).setOnClickListener(this);
        findViewById(R.id.btn_show_fragment).setOnClickListener(this);
        findViewById(R.id.btn_show_multi).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fragment_fragment_communicate:
                startActivity(new Intent(this, CommuActivity.class));
                break;
            case R.id.btn_test_close:
                startActivity(new Intent(this, TestCloseActivity.class));
                break;
            case R.id.btn_test_corner:
                startActivity(new Intent(this, RadiusActivity.class));
                break;
            case R.id.btn_show_fragment:
                startActivity(new Intent(this, TestShowActivity.class));
                break;
            case R.id.btn_show_multi:
                startActivity(new Intent(this, MultiPageActivity.class));
                break;
        }
    }

}
