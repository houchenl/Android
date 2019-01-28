package com.houchen.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_play).setOnClickListener(this);
        findViewById(R.id.btn_silent).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.haibin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        mediaPlayer.release();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                play();
                break;
            case R.id.btn_silent:
                closeSpeaker();
                break;
            case R.id.btn_stop:
                stop();
                break;
        }
    }

    private void play() {
        mediaPlayer.start();
    }

    private void stop() {
        mediaPlayer.stop();
    }

    private static final String TAG = "MainActivity";
    private void closeSpeaker() {
        Log.d(TAG, "closeSpeaker: 1");
        try {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

            Log.d(TAG, "closeSpeaker: 2");
            if (audioManager != null) {
                // 必须把mode设置为STREAM_MUSIC时，关闭扬声器才起作用
                audioManager.setMode(AudioManager.STREAM_MUSIC);
//                audioManager.setMicrophoneMute(false);
                Log.d(TAG, "closeSpeaker: 3");
                audioManager.setSpeakerphoneOn(false);
//                if (audioManager.isSpeakerphoneOn()) {
//                    Log.d(TAG, "closeSpeaker: 4");
//                    audioManager.
//                    Log.d(TAG, "closeSpeaker: 5");
//                    audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 0, AudioManager.STREAM_VOICE_CALL);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
