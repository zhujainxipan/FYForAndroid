package com.ht.fyforandroid.net.asynctasknet.callback;

import com.ht.fyforandroid.net.asynctasknet.IProgressListener;

import org.apache.http.HttpResponse;

/**
 * Created by niehongtao on 16/6/18.
 */
public interface ICallBack {
    void onFailure(Exception result);

    void onSuccess(Object result);

    Object handle(HttpResponse response, IProgressListener iProgressListener);

    void onProgressUpdate(int curPos, int contentLength);

    /**
     * 在子线程中对返回值做预处理，比如保存到数据库等等操作（预处理返回的对象）
     * 如果不需要什么处理的话，什么都不需要做
     * @param object
     * @return
     */
    Object onPreHandle(Object object);

    Object onPresRequest();
}
