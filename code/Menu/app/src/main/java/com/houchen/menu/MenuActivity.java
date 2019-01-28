package com.houchen.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = "houchenMenuActivity";

    private boolean isKeyDownMenuClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        isKeyDownMenuClick = true;

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
        Log.d(TAG, "onDestroy: " + isKeyDownMenuClick);
    }

    public void startMenu(View view) {
        startActivity(new Intent(this, MenuActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            isKeyDownMenuClick = true;
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

}
