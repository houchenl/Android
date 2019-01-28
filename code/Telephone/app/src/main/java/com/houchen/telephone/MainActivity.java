package com.houchen.telephone;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Handler mHandler = new Handler();

    private String callState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerPhoneStateListener();
        mHandler.postDelayed(loop, 500);
    }

    private void registerPhoneStateListener() {
        CustomPhoneStateListener customPhoneStateListener = new CustomPhoneStateListener();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            telephonyManager.listen(customPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    private class CustomPhoneStateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:      // 电话挂断
                    callState = "idle";
                    Log.d(TAG, "onCallStateChanged: CALL_STATE_IDLE");
                    break;
                case TelephonyManager.CALL_STATE_RINGING:   // 电话响铃
                    callState = "ring";
                    Log.d(TAG, "onCallStateChanged: CALL_STATE_RINGING");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:   // 电话进行中
                    callState = "offhook";
                    Log.d(TAG, "onCallStateChanged: CALL_STATE_OFFHOOK");
                    break;
            }
        }
    }

    private Runnable loop = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "run: status is " + callState);
            mHandler.postDelayed(this, 500);
        }
    };

}
