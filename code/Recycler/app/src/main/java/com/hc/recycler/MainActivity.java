package com.hc.recycler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hc.recycler.demo.TestRecyclerViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_demo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_demo:
                start(TestRecyclerViewActivity.class);
                break;
        }
    }

    private void start(Class clz) {
        startActivity(new Intent(this, clz));
    }

}
