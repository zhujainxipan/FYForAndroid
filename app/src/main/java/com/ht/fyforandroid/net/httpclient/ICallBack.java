package com.ht.fyforandroid.net.httpclient;

import org.apache.http.HttpResponse;

/**
 * Created by niehongtao on 16/6/18.
 */
public interface ICallBack {
    void onFailure(Exception result);

    void onSuccess(Object result);

    Object handle(HttpResponse response, IProgressListener iProgressListener);

}
