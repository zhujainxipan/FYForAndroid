package com.ht.fyforandroid.net.threadpoolnet.request;

/**
 * RequestCallback是回调
 */
public interface RequestCallback<T> {
    public void onSuccess(T response);

    public void onFail(String errorMessage);
}
