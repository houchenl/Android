package com.hc.videosdk;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import tbsdk.sdk.TBSDK;

/**
 * Created by liu_lei on 2017/11/7.
 */

public class VideoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /*
        * OnCreate 会被多个进程重入，这段保护代码，
        */
        if ( getApplicationInfo().packageName.equals( getCurProcessName( getApplicationContext() ) ) )
        {
            TBSDK.getInstance().init( getApplicationContext(), "123g3hw45" );
        }


    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName( Context context )
    {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = ( ActivityManager ) context
                .getSystemService( Context.ACTIVITY_SERVICE );

        for ( ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses() )
        {

            if ( appProcess.pid == pid )
            {
                return appProcess.processName;
            }
        }
        return null;
    }
}
