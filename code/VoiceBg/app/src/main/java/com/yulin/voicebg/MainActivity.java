package com.yulin.voicebg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layout);
        findViewById(R.id.btn_long).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetWidth(540);
            }
        });
        findViewById(R.id.btn_short).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetWidth(180);
            }
        });

    }

    private void resetWidth(int width) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        lp.width = width;
        layout.setLayoutParams(lp);
    }

}
