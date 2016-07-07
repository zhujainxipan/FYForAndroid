package com.ht.fyforandroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.ht.fyforandroid.BaseApplication;

/**
 * Created by niehongtao on 16/5/21.
 * sharedpreference工具类
 * sharedpreference的应用场景：
 * 1. 登陆：记住密码功能
 * 2. 电商客户端：记住账号
 * 3. 常用的：设置信息：例如是否加载大图、是否开启推送通知、是否开启双击返回等
 */
public class PreferenceHelper {
    SharedPreferences mSharedPreferences;
    String name = "config";
    private static PreferenceHelper helper;

    public static PreferenceHelper getInstance() {
        if (helper == null) {
            helper = new PreferenceHelper();
        }
        return helper;
    }

    private PreferenceHelper() {
        mSharedPreferences =
                BaseApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }


    public void removeValue(String key) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.remove(key);
    }

    public void putStringValue(String key, String value) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public void putIntValue(String key, int value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putBooleanValue(String key, boolean  enable){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, enable);
        editor.apply();
    }

    public String getStringValue(String key){
        return mSharedPreferences.getString(key, "");
    }

    public int getIntValue(String key){
        return mSharedPreferences.getInt(key, 0);
    }

    public boolean getBooleanValue(String key){
        return mSharedPreferences.getBoolean(key, false);
    }
}
