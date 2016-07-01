package com.ht.fyforandroid.net.threadpoolnet;

/**
 * RequestCallback是回调
 */
public interface RequestCallback {
    public void onSuccess(String content);

    public void onFail(String errorMessage);
}
