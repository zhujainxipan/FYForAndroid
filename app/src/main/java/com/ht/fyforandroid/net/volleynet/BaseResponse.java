package com.ht.fyforandroid.net.volleynet;

import com.google.gson.Gson;

/**
 * Created by niehongtao on 16/7/15.
 */
public abstract class BaseResponse<T> {
    public static final String ERROR_JSON_PARSE_MSG = "数据解析错误";

    public abstract void onSuccess(T t);

    public abstract void onError(String msg);

    public abstract Class<T> getResponseClass();

    public void doParase(String jsonString) {
        try {
            T t = new Gson().fromJson(jsonString, getResponseClass());
            onSuccess(t);
        } catch (Exception e) {
            onError(ERROR_JSON_PARSE_MSG);
        }
    }

}
