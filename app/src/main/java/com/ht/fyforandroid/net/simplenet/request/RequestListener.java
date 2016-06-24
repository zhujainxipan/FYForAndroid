package com.ht.fyforandroid.net.simplenet.request;

/**
 * Created by niehongtao on 16/6/24.
 */
public interface RequestListener<T> {
    void onComplete(int stConde, T result, String msg);
}
