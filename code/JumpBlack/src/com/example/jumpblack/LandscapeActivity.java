package com.example.jumpblack;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class LandscapeActivity extends FragmentActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landscape);
        
        findViewById(R.id.btn_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doFinish();
            }
        });
    }
    
    private void doFinish() {
        finish();
    }

}
