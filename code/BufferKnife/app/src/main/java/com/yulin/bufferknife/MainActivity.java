package com.yulin.bufferknife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text1)
    TextView text1;

    @BindView(R.id.textView)
    TextView textView;

//    @Nullable
    @BindView(R.id.btn)
    protected Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.text1, R.id.textView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text1:
                break;
            case R.id.textView:
                break;
        }
    }

}
