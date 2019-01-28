package com.yulin.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yulin.list.expand.ExpandListActivity;
import com.yulin.list.grid.GridActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_cross_recycler).setOnClickListener(this);
        findViewById(R.id.btn_expand_list).setOnClickListener(this);
        findViewById(R.id.btn_grid).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cross_recycler:
                startActivity(new Intent(this, CrossRecyclerActivity.class));
                break;
            case R.id.btn_expand_list:
                startActivity(new Intent(this, ExpandListActivity.class));
                break;
            case R.id.btn_grid:
                startActivity(new Intent(this, GridActivity.class));
                break;
        }
    }

}
