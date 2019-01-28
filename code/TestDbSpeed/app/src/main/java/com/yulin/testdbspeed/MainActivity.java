package com.yulin.testdbspeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yulin.testdbspeed.greendao.GreendaoActivity;
import com.yulin.testdbspeed.wcdb.WcdbActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void wcdb(View v) {
        startActivity(new Intent(this, WcdbActivity.class));
    }

    public void greendao(View v) {
        startActivity(new Intent(this, GreendaoActivity.class));
    }

}
