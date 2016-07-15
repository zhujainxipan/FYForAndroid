package com.ht.fyforandroid.net.volleynet;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by niehongtao on 16/7/15.
 */
public abstract class BaseRequest {

    public abstract String getMobileApi();

    /**
     * 一些参数
     * @return
     */
    public abstract Map<String, String> getParams();

    final String getUrl() {
        return getMobileApi().concat("?").concat(parseParams(getParams()));
    }

    private String parseParams(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        Iterator<String> keys= params.keySet().iterator();
        while(keys.hasNext()) {
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
