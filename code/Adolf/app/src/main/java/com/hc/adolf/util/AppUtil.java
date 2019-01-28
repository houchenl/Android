package com.hc.adolf.util;

import android.content.res.Resources;

import com.hc.adolf.config.HcApplication;

/**
 * Created by liulei0905 on 2016/8/4.
 * 系统相关
 */
public class AppUtil {

    public static Resources getResources() {
        return HcApplication.getInstance().getResources();
    }

}
