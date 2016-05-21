package com.ht.fyforandroid.util;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import java.util.Stack;

/**
 * Created by niehongtao on 16/5/18.
 * activity堆栈式管理
 */
public class AppManager {
    private static Stack<FragmentActivity> mActivities;
    private static AppManager instance;

    private AppManager() {

    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public void addActivity(FragmentActivity activity) {
        if (mActivities == null) {
            mActivities = new Stack<>();
        }
        mActivities.add(activity);
    }

    /**
     * 获取当前activity
     * @return
     */
    public FragmentActivity getTopActivity() {
        return mActivities.lastElement();
    }

    /**
     * 从activity栈中移除一个activity
     * @param activity
     */
    public void removeActivity(FragmentActivity activity) {
        if (activity == null) {
            return;
        }
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }


    /**
     * 退出app
     * @param context
     */
    public void appExit(Context context) {
        if (!mActivities.empty()) {
            for (FragmentActivity activity : mActivities) {
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
            mActivities.clear();
        }
        // 杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


}
