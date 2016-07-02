package com.ht.fyforandroid.net.threadpoolnet.request;

import android.util.Log;

import com.ht.fyforandroid.net.simplenet.base.Response;

import java.util.List;

public abstract class Request<T> {
    public enum RequestMethod {
        GET, POST, DELETE, PUT
    }

    private RequestMethod method;
    private String url;
    private RequestCallback mCallback;
    private List<RequestParameter> mParams;

    public Request(String url, RequestMethod method, RequestCallback callback, List<RequestParameter> params) {
        this.url = url;
        this.method = method;
        this.mCallback = callback;
        this.mParams = params;
    }

    /**
     * 从原生的网络请求中解析结果
     *
     * @param response
     * @return
     */
    public abstract T parseResponse(Response response);

    /**
     * 处理Response,该方法运行在UI线程.
     *
     * @param response
     */
    public final void deliveryResponse(Response response) {
        T result = parseResponse(response);
        if (mCallback != null) {
            int stCode = response != null ? response.getStatusCode() : -1;
            String msg = response != null ? response.getMessage() : "unkown error";
            Log.e("", "### 执行回调 : stCode = " + stCode + ", result : " + result + ", err : " + msg);
            mCallback.onSuccess(result);
        }
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public RequestCallback getCallback() {
        return mCallback;
    }

    public void setCallback(RequestCallback callback) {
        mCallback = callback;
    }

    public List<RequestParameter> getParams() {
        return mParams;
    }

    public void setParams(List<RequestParameter> params) {
        mParams = params;
    }
}
