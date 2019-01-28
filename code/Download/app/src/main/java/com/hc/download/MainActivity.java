package com.hc.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private int mNotificationId = 123;

    private File mDestFile;

    private BootReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mReceiver = new BootReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addDataScheme("package");
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
    }

    public void start(View view) {
        String downloadUrl = "http://a.wdjcdn.com/release/files/phoenix/5.21.1.12053/wandoujia-wandoujia-web_direct_binded_5.21.1.12053.apk";
        new DownloadTask().execute(downloadUrl);
    }

    private class DownloadTask extends AsyncTask<String, Integer, Integer> {

        private int mDownloadProgress = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(MainActivity.this);
            mBuilder.setContentTitle("下载鑫财通")
                    .setContentText("预备下载")
                    .setSmallIcon(R.drawable.ic_launcher);
        }

        @Override
        protected Integer doInBackground(String... strings) {
            String srcPath = strings[0];
            String destPath = Environment.getExternalStorageDirectory().getPath();

            try {
                URL url = new URL(srcPath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                // 获取文件大小
                InputStream is = conn.getInputStream();
                mDestFile = new File(destPath, getFileName(srcPath));
                FileOutputStream fos = new FileOutputStream(mDestFile);
                BufferedInputStream bis = new BufferedInputStream(is);
                long size = conn.getContentLength();

                byte[] buffer = new byte[1024];
                int hasRead;
                long total = 0;

                while ((hasRead = bis.read(buffer)) != -1) {
                    fos.write(buffer, 0, hasRead);
                    total += hasRead;

                    int tPos = (int) (total * 100 / size);
                    if (tPos != mDownloadProgress) {
                        publishProgress(mDownloadProgress);
                    }
                    mDownloadProgress = tPos;
                }

                fos.close();
                bis.close();
                is.close();

                return 0;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return -1;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            int progress = values[0];
            progress = progress >= 100 ? 100 : progress;
            mBuilder.setProgress(100, progress, false);
            mBuilder.setContentText("已下载 " + progress + "%");
            Notification notification = mBuilder.build();
            notification.flags |= Notification.FLAG_NO_CLEAR;
            mNotificationManager.notify(mNotificationId, notification);
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);

            if (s == 0) {
                // 成功
                PendingIntent resultPendingIntent = PendingIntent.getActivity(
                        MainActivity.this,
                        0,
                        getInstallApk(mDestFile),
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

                mBuilder.setContentText("下载完成，点击安装")
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true)
                        .setProgress(0, 0, false);
                mNotificationManager.notify(mNotificationId, mBuilder.build());

                MainActivity.this.startActivity(getInstallApk(mDestFile));
            } else {
                // 失败
                mBuilder.setContentText("Download failure")
                        .setAutoCancel(true)
                        .setProgress(0, 0, false);
                mNotificationManager.notify(mNotificationId, mBuilder.build());
            }
        }

    }

    private String getFileName(String url) {
        int slashIndex = url.lastIndexOf("/");
        return url.substring(slashIndex + 1);
    }

    private Intent getInstallApk(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }

    public class BootReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("houchen", action);
            //接收安装广播
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                mNotificationManager.cancel(mNotificationId);
            }
        }
    }

}
