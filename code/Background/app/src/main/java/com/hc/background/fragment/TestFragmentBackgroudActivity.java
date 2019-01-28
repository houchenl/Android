package com.hc.background.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hc.background.R;

public class TestFragmentBackgroudActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment_backgroud);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new BackgroundFragment())
                .commit();
    }

}
