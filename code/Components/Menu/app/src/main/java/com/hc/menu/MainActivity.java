package com.hc.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_spring:
                toast("春天");
                break;
            case R.id.menu_summer:
                toast("夏天");
                break;
            case R.id.menu_autumn:
                toast("秋天");
                break;
            case R.id.menu_winter:
                toast("冬天");
                break;
            default:
                break;
        }

        return true;
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, 0).show();
    }

}
