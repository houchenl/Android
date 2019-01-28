package com.yulin.testdbspeed.wcdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yulin.testdbspeed.Constant;
import com.yulin.testdbspeed.R;

public class WcdbActivity extends AppCompatActivity {

    private TextView tvMsg;
    private StringBuffer sb = new StringBuffer();

    private DbManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wcdb);

        tvMsg = findViewById(R.id.tv_time);

        mManager = new DbManager(this);
    }

    public void insert(View v) {
        long start = System.currentTimeMillis();
        for (int i = 1; i <= Constant.TIMES; i++) {
            long currentTime = System.currentTimeMillis();
            String a = String.valueOf(currentTime % 10);
            int b = (int) (currentTime % 100);
            String c = String.valueOf(currentTime % 10000);

            PlainPerson person = new PlainPerson();
            person.setName(a);
            person.setAddress(c);
            person.setAge(b);
            person.setNumber(i);

            mManager.addPersonData(person);
        }

        long end = System.currentTimeMillis();
        long spend = end - start;
        sb.append("单插1万次耗时").append(spend).append("ms").append("\n");
        tvMsg.setText(sb.toString());
    }

    public void inserts(View v) {
        long spend = mManager.addPersonDatas();
        sb.append("连插1万次耗时").append(spend).append("ms").append("\n");
        tvMsg.setText(sb.toString());
    }

    public void delete(View v) {
        long start = System.currentTimeMillis();
        for (int i = 1; i <= Constant.TIMES; i++) {
            mManager.deletePersonData(i);
        }

        long end = System.currentTimeMillis();
        long spend = end - start;
        sb.append("单删1万次耗时").append(spend).append("ms").append("\n");
        tvMsg.setText(sb.toString());
    }

    public void deletes(View v) {
        long spend = mManager.deletePersonDatas();
        sb.append("连删1万次耗时").append(spend).append("ms").append("\n");
        tvMsg.setText(sb.toString());
    }

}
