package com.hc.shell;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "houchenl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Process process = Runtime.getRuntime().exec("getprop sys.hdmiin.connected");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = br.readLine();
            System.out.println("houchenl " + line);
            Log.d(TAG, "onCreate: " + line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
