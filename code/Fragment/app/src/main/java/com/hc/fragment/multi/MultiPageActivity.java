package com.hc.fragment.multi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hc.fragment.R;
import com.hc.fragment.base.BaseFragment;
import com.hc.fragment.util.RouteUtil;

import java.util.List;

public class MultiPageActivity extends AppCompatActivity {

    private static final String TAG = "MultiPageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_page);

        // 管理添加到本activity中的所有fragment
        final FragmentManager fm = getSupportFragmentManager();

        // add three fragment
        Fragment fragment1 = BaseFragment.newInstance("one", "#ff0000");
        RouteUtil.addFragment(fm, fragment1, R.id.container_1, "one");
        Fragment fragment2 = BaseFragment.newInstance("two", "#ffff00");
        RouteUtil.addFragment(fm, fragment2, R.id.container_2, "two");
        Fragment fragment3 = BaseFragment.newInstance("three", "#0000ff");
        RouteUtil.addFragment(fm, fragment3, R.id.container_3, "three");

        findViewById(R.id.btn_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get fragment count
                List<Fragment> fragments = fm.getFragments();
                int count = fragments.size();
                Log.d(TAG, "onCreate: fragment count " + count);
            }
        });
    }

}
