package com.yulin.buildconfig;

/**
 * Created by liulei on 2017/4/13.
 */

public class Utils2 {

    public static String printBuildConfig() {
        String msg = "DEBUG " + BuildConfig.DEBUG + ", ApplicationId " + BuildConfig.APPLICATION_ID
                + ", buildType " + BuildConfig.BUILD_TYPE + ", flavor " + BuildConfig.FLAVOR
                + ", versionName " + BuildConfig.VERSION_NAME + ", versionCode " + BuildConfig.VERSION_CODE
                + ", LOG_DEBUG " + BuildConfig.LOG_DEBUG;
        System.out.println("BuildConfigContent: " + msg);
        return msg;
    }

}
