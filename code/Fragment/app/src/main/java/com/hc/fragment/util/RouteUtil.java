package com.hc.fragment.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by liu_lei on 2018/1/17.
 *
 */

public class RouteUtil {

    public static void addFragment(FragmentManager fm, Fragment fragment, int containerId, String tag) {
        FragmentTransaction ft = fm.beginTransaction();

        // 检查fragment是否已添加
        if (fm.findFragmentByTag(tag) == null) {
            ft.add(containerId, fragment, tag);
        }

        ft.show(fragment);
        ft.commit();
    }

}
