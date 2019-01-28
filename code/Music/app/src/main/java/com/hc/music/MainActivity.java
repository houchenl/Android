package com.hc.music;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "houchen-MainActivity";

    private static final String DIRECTORY_NAME = "/weiyi";
    private static final String FILE_NAME = "/test_audio.amr";

    private static final int RECORD_STATE_INIT = 0;
    private static final int RECORD_STATE_RECORDING = 1;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1001;
    private static final int MY_PERMISSIONS_REQUEST_SDCARD = 1002;
    private static final int PLAY_STATUS_INIT = 2;
    private static final int PLAY_STATUS_PLAYING = 3;

    private TextView tvStatus;
    private Button btnPlay, btnRecord;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;

    private int mRecordState = RECORD_STATE_INIT;
    private int mPlayStatus = PLAY_STATUS_INIT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);
        btnPlay = findViewById(R.id.btn_play);
        btnRecord = findViewById(R.id.btn_record);

        btnPlay.setOnClickListener(this);
        btnRecord.setOnClickListener(this);

        tvStatus.setText(getFilePath());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_record:
                stopPlay();
                record();
                break;
            case R.id.btn_play:
                stopRecord();
                play();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void record() {
        // 如果没有录音权限，请求录音权限
        if (!checkRecordAudioPermission()) {
            requestRecordPermission();
            return;
        }

        // 如果没有文件读写权限，请求文件读写权限
        if (!checkSdCardPermission()) {
            requestSdCardPermission();
            return;
        }

        if (mRecordState == RECORD_STATE_INIT) {
            try {
                startRecord();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "record: fail for " + e.getMessage(), e);
            }
        } else if (mRecordState == RECORD_STATE_RECORDING) {
            stopRecord();
        }
    }

    // 开始录制
    private synchronized void startRecord() throws IOException {
        // 如果目标文件为空，停止
        if (TextUtils.isEmpty(getFilePath())) {
            return;
        }

        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
        }
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(getFilePath());
        mRecorder.prepare();
        mRecorder.start();

        mRecordState = RECORD_STATE_RECORDING;
        tvStatus.setText("录制中。。。");
        btnRecord.setText("停止录制");
    }

    // 停止录制
    private synchronized void stopRecord() {
        if (mRecorder != null && mRecordState == RECORD_STATE_RECORDING) {
            try {
                mRecorder.stop();
                mRecorder.reset();    // You can reuse the object by going back to setAudioSource() step

                mRecordState = RECORD_STATE_INIT;
                tvStatus.setText(getFilePath());
                btnRecord.setText("开始录制");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "stopRecord: fail to stop record for " + e.getMessage(), e);
            }
        }
    }

    private void play() {
        if (mPlayStatus == PLAY_STATUS_INIT) {
            try {
                startPlay();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (mPlayStatus == PLAY_STATUS_PLAYING) {
            stopPlay();
        }
    }

    private void startPlay() throws IOException {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }

        mPlayer.setDataSource(getFilePath());
        mPlayer.prepare();
        mPlayer.start();

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 播放完成
                stopPlay();
            }
        });
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // 播放失败
                stopPlay();
                return false;
            }
        });

        mPlayStatus = PLAY_STATUS_PLAYING;

        tvStatus.setText("播放中。。。");
        btnPlay.setText("停止播放");
    }

    // 停止播放
    private void stopPlay() {
        if (mPlayer != null && mPlayStatus == PLAY_STATUS_PLAYING) {
            mPlayer.stop();
            mPlayer.reset();

            mPlayStatus = PLAY_STATUS_INIT;

            tvStatus.setText(getFilePath());
            btnPlay.setText("开始播放");
        }
    }

    // 获取存储文件路径
    private String getFilePath() {
        String sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filePath = sdCardPath + DIRECTORY_NAME + FILE_NAME;
        if (createFileIfNotExist(sdCardPath + DIRECTORY_NAME, FILE_NAME))
            return filePath;
        else
            return null;
    }

    // 检查文件是否存在，没有的话，创建
    private boolean createFileIfNotExist(String path, String fileName) {
        Log.d(TAG, "createFileIfNotExist: 1 " + path);
        if (createDir(path)) {
            if (!TextUtils.isEmpty(fileName)) {
                File file = new File(path + fileName);
                try {
                    return file.exists() || file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return false;
    }

    // 创建文件夹
    private boolean createDir(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            return file.exists() || file.mkdir();
        }
        return false;
    }

    // 检查是否有录音的权限
    private boolean checkRecordAudioPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    // 检查是否有文件读写权限
    private boolean checkSdCardPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    // 请求录音和文件读写权限
    private void requestRecordPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
    }

    // 请求文件读写权限
    private void requestSdCardPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_SDCARD);
    }

    // 请求权限返回
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECORD_AUDIO:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    record();
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: refuse permission.");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            case MY_PERMISSIONS_REQUEST_SDCARD:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    record();
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: refuse permission.");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

}
