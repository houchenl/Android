package com.hc.fragment.comm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hc.fragment.R;

/**
 * All Fragment-to-Fragment communication is done through the associated Activity.
 * Two Fragments should never communicate directly
 * */
public class CommuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

}
