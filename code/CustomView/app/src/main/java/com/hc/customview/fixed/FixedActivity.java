package com.hc.customview.fixed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hc.customview.R;

public class FixedActivity extends AppCompatActivity {

    private FixedItemView mView;
    private TextView mTv;
    private int mStartPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed);

        mView = (FixedItemView) findViewById(R.id.hello);
        mTv = (TextView) findViewById(R.id.tv);
    }

    public void move(View view) {
        mStartPos += 200;
        mView.scrollTo(mStartPos, 0);
        mTv.setText("" + mStartPos);
    }

    public void back(View view) {
        mStartPos -= 200;
        mView.scrollTo(mStartPos, 0);
        mTv.setText("" + mStartPos);
    }

}
