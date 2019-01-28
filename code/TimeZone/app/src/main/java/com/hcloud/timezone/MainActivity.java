package com.hcloud.timezone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvTime = (TextView) findViewById(R.id.tv_time);

        String time1 = System.currentTimeMillis() + "";
        String time2 = Calendar.getInstance().getTimeInMillis() + "";
        String time3 = new Date().getTime() + "";

        tvTime.setText(time1 + "\n" + time2 +"\n" + time3);
    }
}
