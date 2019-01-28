package com.hc.recycler.student.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.hc.recycler.R;
import com.hc.recycler.databinding.ActivityStudentBinding;
import com.hc.recycler.student.model.Student;
import com.hc.recycler.student.viewmodel.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private List<Student> mListStudents = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStudentBinding studentBinding = DataBindingUtil.setContentView(this, R.layout.activity_student);

        initDatas();
        StudentAdapter adapter = new StudentAdapter(mListStudents);

        studentBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentBinding.recyclerView.setAdapter(adapter);
    }

    private void initDatas() {
        for (int i = 0; i < 20; i++) {
            mListStudents.add(new Student("jack", i + 1));
        }
    }

}
