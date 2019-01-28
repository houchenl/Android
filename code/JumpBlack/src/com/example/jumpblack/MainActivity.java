package com.example.jumpblack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.btn_jump).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doJump();
            }
        });
    }
    
    private void doJump() {
        startActivity(new Intent(this, LandscapeActivity.class));
    }
    
}
