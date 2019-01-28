package com.houchen.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.houchen.rxjava.operator.ConcatOperator;
import com.houchen.rxjava.operator.MapOperator;
import com.houchen.rxjava.operator.TestCreate;
import com.houchen.rxjava.operator.ZipOperator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new TestCreate().test();
//        new MapOperator().test();
//        new ZipOperator().test();
        new ConcatOperator().test();
    }

}
