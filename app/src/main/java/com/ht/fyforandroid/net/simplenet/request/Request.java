package com.ht.fyforandroid.net.simplenet.request;

import com.ht.fyforandroid.net.simplenet.response.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by niehongtao on 16/6/24.
 */
public abstract class Request<T> implements Comparable<Request<T>> {



    public Priority getPriority() {
        return mPriority;
    }

    public int getSerialNumber() {
        return mSerialNum;
    }

    // http请求方法枚举
    public static enum HttpMethod {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");

        private String mHttpMethod = "";

        private HttpMethod(String method) {
            mHttpMethod = method;
        }


        @Override
        public String toString() {
            return mHttpMethod;
        }
    }

    // 优先级枚举
    public static enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    // 默认的编码方式
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    // 请求序列号
    private int mSerialNum = 0;
    // 优先级默认设置为normal
    private Priority mPriority = Priority.NORMAL;
    // 是否取消该请求
    private boolean isCancel = false;
    // 该请求是否应该缓存
    private boolean mShouldCache = true;
    // 请求Listener
    private RequestListener<T> mRequestListener;
    // 请求的URL
    private String mUrl = "";
    // 请求的方法
    HttpMethod mHttpMethod = HttpMethod.GET;
    // 请求的header
    private Map<String, String> mHeaders = new HashMap<>();
    // 请求参数
    private Map<String, String> mBodyParams = new HashMap<>();

    /**
     * @param method   请求方式
     * @param url      请求的目标url
     * @param listener 请求回调，将结果回调给用户
     */
    public Request(HttpMethod method, String url, RequestListener<T> listener) {
        this.mHttpMethod = method;
        this.mUrl = url;
        this.mRequestListener = listener;
    }


    public final void deliveryResponse(Response response) {
        T result = parseResponse(response);
        if (mRequestListener != null) {
            int stConde = response != null ? response.getStatusCode() : -1;
            String msg = response != null ? response.getMessage() : "unknow error";
            mRequestListener.onComplete(stConde, result, msg);
        }
    }

    protected String getParamsEncoding() {
        return DEFAULT_PARAMS_ENCODING;
    }

    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    /**
     * 返回post或者put请求时的body参数字节数组
     *
     * @return
     */
    public byte[] getBody() {
        Map<String, String> params = getParams();
        if (params != null && params.size() > 0) {
            return encodeParameters(params, getParamsEncoding());
        }
        return null;
    }

    /**
     * 将参数转换为url编码的参数串，格式为key1=value1&key2=value2
     *
     * @param params
     * @param paramsEncoding
     * @return
     */
    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodeParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodeParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodeParams.append("=");
                encodeParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodeParams.append("&");
            }
            return encodeParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getParams() {
        return mBodyParams;
    }


    @Override
    public int compareTo(Request<T> another) {
        Priority myPriority = this.getPriority();
        Priority anotherPriority = another.getPriority();
        return myPriority.equals(anotherPriority) ? this.getSerialNumber() - another.getSerialNumber() : myPriority.ordinal() - anotherPriority.ordinal();
    }

    /**
     * 从原生的网络请求中解析结果，子类必须覆写
     *
     * @param response
     * @return
     */
    public abstract T parseResponse(Response response);


}
