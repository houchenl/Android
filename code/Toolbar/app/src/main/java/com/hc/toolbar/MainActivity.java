package com.hc.toolbar;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        // 需要在setSupportActionBar前调用
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("标题");
        toolbar.setSubtitle("副标题");

        setSupportActionBar(toolbar);

        // 在setSupportActionBar后调用
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        // 需要先调用inflateMenu，menu才会显示

        toolbar.setOnMenuItemClickListener(listener);

    }

    private Toolbar.OnMenuItemClickListener listener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String msg = "";
            switch (item.getItemId()) {
                case R.id.action_edit:
                    msg += "Click edit";
                    break;
                case R.id.action_share:
                    msg += "Click share";
                    break;
                case R.id.action_settings:
                    msg += "Click setting";
                    break;
            }

            if(!msg.equals("")) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // 配置searchView...

        //定义一个监听器
        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this, "expand", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this, "collapse", Toast.LENGTH_SHORT).show();
                return true;
            }
        };

        //设置监听器
        MenuItemCompat.setOnActionExpandListener(searchItem, expandListener);

        return super.onCreateOptionsMenu(menu);
    }

    public void startSecond(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

}
