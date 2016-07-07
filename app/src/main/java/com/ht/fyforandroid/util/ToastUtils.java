package com.ht.fyforandroid.util;

import android.widget.Toast;

import com.ht.fyforandroid.BaseApplication;

/**
 * Created by niehongtao on 16/5/21.
 * 吐司工具类
 */
public class ToastUtils {
    /**
     * 系统默认短时间吐司
     *
     * @param text 字符串
     */
    public static void shortShow(String text) {
        if (!StringUtils.isEmpty(text)) {
            Toast toast = Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * 系统默认长时间吐司
     *
     * @param text
     */
    public static void longShow(String text) {
        if (!StringUtils.isEmpty(text)) {
            Toast toast = Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
