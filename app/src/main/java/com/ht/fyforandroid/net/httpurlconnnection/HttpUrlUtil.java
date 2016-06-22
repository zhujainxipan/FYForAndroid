package com.ht.fyforandroid.net.httpurlconnnection;

import com.ht.fyforandroid.net.httpclient.Request;

import java.net.HttpURLConnection;

/**
 * Created by niehongtao on 16/6/22.
 */
public class HttpUrlUtil {
    public static HttpURLConnection execute(Request request) {
        switch (request.method) {
            case GET:
                return get(request);
            case POST:
                return post(request);
            case DELETE:

            case PUT:
            default:
                throw new IllegalStateException("the method" + request.method.name() + "doesn't support");
        }
    }

    private static HttpURLConnection post(Request request) {
        return null;
    }

    private static HttpURLConnection get(Request request) {
        return null;
    }
}
