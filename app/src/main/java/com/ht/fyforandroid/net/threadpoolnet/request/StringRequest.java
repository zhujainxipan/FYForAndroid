package com.ht.fyforandroid.net.threadpoolnet.request;

import com.ht.fyforandroid.net.simplenet.base.Response;

import java.util.List;

/**
 * Created by niehongtao on 16/7/2.
 */
public class StringRequest extends Request {
    public StringRequest(String url, RequestMethod method, RequestCallback callback, List params) {
        super(url, method, callback, params);
    }

    @Override
    public Object parseResponse(Response response) {
        return new String(response.getRawData());
    }
}
