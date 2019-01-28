package com.hc.adolf.util;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by liulei0905 on 2016/8/4.
 * Store and hold activity.
 */
public enum  HcActivityManager {

    instance;

    /**
     * 维护一个后入先出的栈
     * */
    private Stack<Activity> mStackActivity = new Stack<>();

    /**
     * 把activity压入栈顶
     * 写代码时不要自己调用
     * */
    public final void addActivity(Activity item) {
        mStackActivity.push(item);
    }

    /**
     * 把activity从栈中移除
     * 写代码时不要自己调用
     * */
    public final void removeActivity(Activity item) {
        mStackActivity.remove(item);
    }

    /**
     * 返回当前显示的activity
     * 可能返回null，使用时小心
     * */
    public final Activity getCurrentActivity() {
        if (mStackActivity.size() > 0) {
            return mStackActivity.peek();
        }

        return null;
    }

    /**
     * 获取当前activity前面的activity
     * 可能返回null，使用时小心
     * */
    public final Activity getLastActivity() {
        int lastIndex = mStackActivity.size() - 2;
        if (lastIndex > -1) {
            return mStackActivity.get(lastIndex);
        }

        return null;
    }

    /**
     * 获取栈中activity的数量
     * */
    public final int getCount() {
        return mStackActivity.size();
    }

    /**
     * 获取activity栈
     * */
    public final Stack<Activity> getActivityList() {
        return mStackActivity;
    }

}
