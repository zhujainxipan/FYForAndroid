package com.ht.fyforandroid.net.asynctasknet.http;

import com.ht.fyforandroid.net.asynctasknet.Request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by niehongtao on 16/6/22.
 */
public class HttpUrlUtil {
    private static final int TIMEOUT_CONNECTION = 111;
    private static final int TIMEOUT_READ = 222;

    public static HttpURLConnection execute(Request request) {
        switch (request.method) {
            case GET:
            case DELETE:
                return get(request);
            case POST:
            case PUT:
                return post(request);
            default:
                throw new IllegalStateException("the method" + request.method.name() + "doesn't support");
        }
    }

    private static HttpURLConnection post(Request request) {

        return null;
    }

    private static HttpURLConnection get(Request request) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(request.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request.method.name());
            connection.setConnectTimeout(TIMEOUT_CONNECTION);
            connection.setReadTimeout(TIMEOUT_READ);
            addHeader(connection, request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }

    private static void addHeader(HttpURLConnection connection, Request request) {

    }
}
