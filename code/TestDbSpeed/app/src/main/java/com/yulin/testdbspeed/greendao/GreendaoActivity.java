package com.yulin.testdbspeed.greendao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yulin.testdbspeed.App;
import com.yulin.testdbspeed.Constant;
import com.yulin.testdbspeed.R;

import java.util.ArrayList;
import java.util.List;

public class GreendaoActivity extends AppCompatActivity {

    private static final String TAG = "GreendaoActivity";

    private TextView tvMsg;
    private StringBuffer sb = new StringBuffer();

    private StudentDao mStudentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);

        tvMsg = findViewById(R.id.tv_time);

        DaoSession session = ((App)getApplication()).getSession();
        mStudentDao = session.getStudentDao();
    }

    public void insert(View v) {
        long start = System.currentTimeMillis();
        for (int i = 1; i <= Constant.TIMES; i++) {
            long currentTime = System.currentTimeMillis();
            String a = String.valueOf(currentTime % 10);
            int b = (int) (currentTime % 100);
            String c = String.valueOf(currentTime % 10000);

            Student student = new Student();
            student.setName(a);
            student.setAddress(c);
            student.setAge(b);
            student.setNumber(i);

            mStudentDao.insert(student);
        }

        long end = System.currentTimeMillis();
        long spend = end - start;
        sb.append("单插1万次耗时").append(spend).append("ms").append("\n");
        tvMsg.setText(sb.toString());
    }

    public void inserts(View v) {
        long start = System.currentTimeMillis();

        List<Student> datas = new ArrayList<>();

        for (int i = 1; i <= Constant.TIMES; i++) {
            long currentTime = System.currentTimeMillis();
            String a = String.valueOf(currentTime % 10);
            int b = (int) (currentTime % 100);
            String c = String.valueOf(currentTime % 10000);

            Student student = new Student();
            student.setName(a);
            student.setAddress(c);
            student.setAge(b);
            student.setNumber(i);

            datas.add(student);
        }

        mStudentDao.insertInTx(datas);

        long end = System.currentTimeMillis();
        long spend = end - start;
        sb.append("连插1万次耗时").append(spend).append("ms").append("\n");
        tvMsg.setText(sb.toString());
    }

    public void delete(View v) {
        long start = System.currentTimeMillis();

//        for (long i = 9971; i <= Constant.TIMES; i++) {
////            List<Student> data = mStudentDao.queryBuilder().where(StudentDao.Properties.Number.eq(i)).list();
////            Log.d(TAG, "delete: i = " + i + ", match size is " + data.size());
////            mStudentDao.deleteInTx(data);
//            mStudentDao.deleteByKey(i);
//        }
        List<Student> students = mStudentDao.queryBuilder().build().list();
        for (Student student : students) {
            Long id = student.getId();
            Log.d(TAG, "delete: id " + id + ", number " + student.getNumber());
            mStudentDao.deleteByKey(id);
        }

        long end = System.currentTimeMillis();
        long spend = end - start;
        sb.append("单删1万次耗时").append(spend).append("ms").append("\n");
        tvMsg.setText(sb.toString());
    }

}
