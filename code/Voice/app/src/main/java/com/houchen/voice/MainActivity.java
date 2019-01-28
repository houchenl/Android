package com.houchen.voice;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private AudioManager mAm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAm = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    public void musicMode(View view) {
//        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mAm.setMode(AudioManager.MODE_NORMAL);
    }

    public void callMode(View view) {
//        setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
        mAm.setMode(AudioManager.MODE_IN_COMMUNICATION);
    }

    public void ringMode(View view) {
        setVolumeControlStream(AudioManager.STREAM_RING);
    }

    public void alarmMode(View view) {
        setVolumeControlStream(AudioManager.STREAM_ALARM);
    }

}
