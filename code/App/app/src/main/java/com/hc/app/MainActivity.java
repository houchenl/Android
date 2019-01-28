package com.hc.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRestartDialog(MainActivity.this);
            }
        });
    }

    private void showRestartDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("确认重启")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartApp(context);
                    }
                })
                .setNegativeButton("取消", null)
                .setCancelable(false)
                .create()
                .show();
    }

    private void restartApp(Context context) {
        if (context == null) return;

        // 拿到launcher intent
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        // 创建启动intent
        PendingIntent restartIntent = PendingIntent.getActivity(context, 123456, launchIntent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            // 延时启动
            alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);

            // 杀死当前应用所在进程
            Process.killProcess(Process.myPid());
        }
    }

}
