package com.houchen.menu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "houchenMainActivity";

    private boolean isKeyDownMenuClick;

    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new MyReceiver();
        IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        registerReceiver(receiver, homeFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        isKeyDownMenuClick = false;

        Log.d(TAG, "onResume: " + isKeyDownMenuClick);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: " + isKeyDownMenuClick);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + isKeyDownMenuClick);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        Log.d(TAG, "onDestroy: " + isKeyDownMenuClick);
    }

    public void startMenu(View view) {
        startActivity(new Intent(this, MenuActivity.class));
    }

    /**
     * 按home键和多任务键时，不会回调onKeyDown，但系统会在这两种情况下发出广播
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: keyCode is " + keyCode);
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            isKeyDownMenuClick = true;
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    private class MyReceiver extends BroadcastReceiver {

        private final String SYSTEM_DIALOG_REASON_KEY = "reason";
        private final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        private final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                Log.d(TAG, "onReceive: " + action + ", " + reason);

                if (reason == null)
                    return;

                // Home键
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
//                    Toast.makeText(getApplicationContext(), "按了Home键", Toast.LENGTH_SHORT).show();
                }

                // 最近任务列表键
                if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
//                    Toast.makeText(getApplicationContext(), "按了最近任务列表", Toast.LENGTH_SHORT).show();
                    isKeyDownMenuClick = true;
                }
            }
        }
    }

}
