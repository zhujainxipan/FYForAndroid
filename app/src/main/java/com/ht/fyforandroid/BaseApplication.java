package com.ht.fyforandroid;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import java.util.Stack;

/**
 * Created by niehongtao on 16/5/18.
 */
public class BaseApplication extends Application {
    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }


    public static Context getContext() {
        return context;
    }
}
