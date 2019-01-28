package com.houchen.arch.lifecycle;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.houchen.arch.R;

import java.util.Random;

public class NameActivity extends AppCompatActivity {

    private static final String TAG = "houchenl-NameActivity";

    private NameViewModel mNameViewModel;
    private TextView mTvName;

    private final String[] NAMES = {"William", "Bill", "John", "Lucy", "Julia"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        mTvName = findViewById(R.id.textView);

        mNameViewModel = new NameViewModel();

        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mTvName.setText(s);
            }
        };

        mNameViewModel.getCurrentName().observe(this, nameObserver);

        findViewById(R.id.button_change_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 生成[0-4]之间的随机整数
                int index = new Random().nextInt(5);
                Log.d(TAG, "onClick: index is " + index);
                mNameViewModel.getCurrentName().postValue(NAMES[index]);
//                mNameViewModel.getCurrentName().setValue(NAMES[index]);
            }
        });
    }

}
