package com.yulin.edittext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yulin.edittext.auto.AutoShowInputActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void autoShowInput(View view) {
        startActivity(new Intent(this, AutoShowInputActivity.class));
    }

}
