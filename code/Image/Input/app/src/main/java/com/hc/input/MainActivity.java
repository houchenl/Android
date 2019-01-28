package com.hc.input;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testAdjust(View view) {
        Intent intent = new Intent(this, AdjustActivity.class);
        startActivity(intent);
    }
    public void testInput(View view) {
        Intent intent = new Intent(this, InputActivity.class);
        startActivity(intent);
    }
}
