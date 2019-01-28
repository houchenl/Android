package com.hc.fragment.show;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hc.fragment.R;
import com.hc.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class TestShowActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TestShowActivity";

    private static final String TAG_ONE = "one";
    private static final String TAG_TWO = "two";
    private static final String TAG_THREE = "three";
    private static final String TAG_FOUR = "four";

    private BaseFragment mOneFrament, mTwoFragment, mThreeFragment, FourFragment;

    private List<String> mAddTags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_show);

        findViewById(R.id.btn_fragment_one).setOnClickListener(this);
        findViewById(R.id.btn_fragment_two).setOnClickListener(this);
        findViewById(R.id.btn_fragment_three).setOnClickListener(this);
        findViewById(R.id.btn_fragment_four).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fragment_one:
                if (mOneFrament == null) {
                    mOneFrament = BaseFragment.newInstance("one");
                }
                showFragment(mOneFrament, TAG_ONE);
                break;
            case R.id.btn_fragment_two:
                if (mTwoFragment == null) {
                    mTwoFragment = BaseFragment.newInstance("two");
                }
                showFragment(mTwoFragment, TAG_TWO);
                break;
            case R.id.btn_fragment_three:
                if (mThreeFragment == null) {
                    mThreeFragment = BaseFragment.newInstance("three");
                }
                showFragment(mThreeFragment, TAG_THREE);
                break;
            case R.id.btn_fragment_four:
                if (FourFragment == null) {
                    FourFragment = BaseFragment.newInstance("four");
                }
                showFragment(FourFragment, TAG_FOUR);
                break;
        }
    }

    /**
     * 1. add只能执行一次，对同一个fragment重复执行add，会崩溃
     * 2. 已add的fragment可以同时显示。但为fragment设置背景色后，就不会同时重叠显示
     * 3. 已show的fragment，再次执行show，不会起作用。
     *
     * 所以
     * 1. 添加fragment时需要使用tag区别，避免重复添加同一个fragment
     * 2. 显示一个fragment时，需要同时隐藏其它fragment
     * */
    private void showFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        // 检查fragment是否已添加
        if (!fragment.isAdded()) {
            Log.d(TAG, "showFragment: add fragment " + fragment + ", " + tag);
            ft.add(R.id.fragment_container, fragment, tag);
//            mAddTags.add(tag);
        }

        hideShowFragment(fm, ft, R.id.fragment_container);

        // 显示当前fragment，隐藏其它fragment
//        hideFragments(fm, ft, mAddTags);

        ft.show(fragment);
        ft.commit();
    }

    private void hideFragments(FragmentManager fm, FragmentTransaction ft, List<String> tags) {
        for (String tag : tags) {
            Fragment fragment = fm.findFragmentByTag(tag);
            Log.d(TAG, "hideFragments: " + fragment + ", " + tag);
            // commit之后fragment才正式添加成功
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
    }

    private void hideShowFragment(FragmentManager fm, FragmentTransaction ft, int containerId) {
        Fragment fragment = fm.findFragmentById(containerId);
        Log.d(TAG, "hideShowFragment: " + fragment);
        if (fragment != null) {
//            if (fragment instanceof BaseFragment) {
//                BaseFragment baseFragment = (BaseFragment) fragment;
//                Log.d(TAG, "hideShowFragment: " + baseFragment.getText());
//            }
            ft.hide(fragment);
        }
    }

}
