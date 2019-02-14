package com.yulin.viewpager;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class Tool {

    private static int screenWidth = 0;
    private static int screenHeight = 0;
    private static int statusBarHeight = 0;

    /**
     * 获取屏幕宽度，像素单位
     * */
    public static int getScreenWidth(Activity activity) {
        if (activity != null && (screenWidth < 1 || screenHeight < 1)) {
            WindowManager windowManager = activity.getWindowManager();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;
        }

        return screenWidth;
    }

    /**
     * 获取屏幕高度，像素单位
     * */
    public static int getScreenHeight(Activity activity) {
        if (activity != null && (screenWidth < 1 || screenHeight < 1)) {
            WindowManager windowManager = activity.getWindowManager();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;
        }

        return screenHeight;
    }

    /**
     * 获取标题栏高度，像素单位
     * */
    public static int getTitleBarHeight(Activity activity) {
        int height = 0;

        if (activity != null) {
            // ANDROID_CONTENT表示除状态栏和标题栏之外内容显示区域，它的top表示相对于除状态栏之外区域顶部的高度，也就是标题栏高度
            height = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        }

        return height;
    }

    /**
     * 获取状态栏高度，像素单位
     * */
    public static int getStatusBarHeight(Activity activity) {
        if (activity != null && statusBarHeight < 1) {
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            statusBarHeight = rect.top;
        }

        return statusBarHeight;
    }

    /**
     * 大屏显示图片时，是否需要下载原图
     * 如果需要，则先显示压缩图，再下载原图。如果不需要，直接显示原图。
     * 如果图片宽度大小屏幕宽度，或者图片高度大于屏幕高度，需要下载原图。
     * @param width 原图宽度
     * @param height 原图高度
     * */
    public static boolean isDownloadOriginImage(Activity activity, int width, int height) {
        int screenWidth = getScreenWidth(activity);
        int screenHeight = getScreenHeight(activity);
        return width > screenWidth || height > screenHeight;
    }

}
