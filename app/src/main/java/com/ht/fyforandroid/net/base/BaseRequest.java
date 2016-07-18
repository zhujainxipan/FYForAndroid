package com.ht.fyforandroid.net.base;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by niehongtao on 16/7/15.
 * 请求和响应做成公共的，不论是okhttp还是volley都可以使用，降低第三方和应用的耦合
 */
public abstract class BaseRequest {

    public abstract String getMobileApi();

    /**
     * 一些参数
     *
     * @return
     */
    public abstract Map<String, String> getParams();

    final public String getUrl() {
        String mobileApi = getMobileApi();
        String params = parseParams(getParams());
        return mobileApi.contains("?") ? mobileApi.concat("&").concat(params) : mobileApi.concat("?").concat(params);
    }

    private String parseParams(Map<String, String> params) {
        if (params == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Iterator<String> keys = params.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next().toString();
            String value = params.get(key);
            if (value == null) {
                keys.remove();
                continue;
            }
            sb.append(key).append("=").append(value).append("&");
        }
        return sb.toString();
    }
}
