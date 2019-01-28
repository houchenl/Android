package com.hc.fragment.close;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hc.fragment.R;

/**
 * Created by liu_lei on 2017/11/29.
 *
 */

public class TestCloseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);

        findViewById(R.id.btn_jump).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        CloseFragment cf = new CloseFragment();

        ft.add(R.id.container, new CloseFragment());
//        ft.addToBackStack(cf.getStackKey());
        ft.commitAllowingStateLoss();
    }

}
