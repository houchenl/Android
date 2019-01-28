package com.hc.regex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private String mobileNum = "+8618655492627";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Pattern pattern = Pattern.compile("^+?8?6?\\s*1\\d{10}$");
//        Matcher matcher = pattern.matcher(mobileNum);
//        if (matcher.matches()) {
//            Toast.makeText(MainActivity.this, "match", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(MainActivity.this, "fail match", Toast.LENGTH_SHORT).show();
//        }

        String downloadUrl = "http://wap.emoney.cn/download/android/trade_pf_305.apk";
        Matcher matcher = Pattern.compile("^\\S+/trade_pf_(\\d+).apk$").matcher(downloadUrl);
        if (matcher.matches()) {
            String versionCode = matcher.group(1);
            Toast.makeText(MainActivity.this, versionCode, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "不匹配", Toast.LENGTH_SHORT).show();
        }
    }

}
