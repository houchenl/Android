package com.hc.animation.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.hc.animation.R;

public class LayoutChangeActivity extends AppCompatActivity {

    private LinearLayout mContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_change);

        mContainer = (LinearLayout) findViewById(R.id.container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add:
                addItem();
                break;
            default:
                break;
        }

        return false;
    }

    private void addItem() {
        final View item = getLayoutInflater().inflate(R.layout.layout_item, mContainer, false);

        item.findViewById(R.id.img_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContainer.removeView(item);
            }
        });

        mContainer.addView(item);
    }

}
