package com.yulin.messagepack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.msgpack.core.MessagePack;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            MessagePackExample.basicUsage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeReadJavaObject();
    }

    private void writeReadJavaObject() {
        Student student = new Student();
        student.setAge(27);
        student.setName("jack");
        student.setCountry("USA");

        MessagePack msgPack = MessagePack.newDefaultPack
    }

}
