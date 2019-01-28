package com.hc.recycler;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liulei on 2017/5/10.
 */

public class Util {

    public static Context getContext() {
        return MyApplication.getInstance().getApplicationContext();
    }

    public static void toast(String toast) {
        Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
    }

}
