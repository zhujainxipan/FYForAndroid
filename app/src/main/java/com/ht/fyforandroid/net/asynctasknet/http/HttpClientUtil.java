package com.ht.fyforandroid.net.asynctasknet.http;

import com.ht.fyforandroid.net.asynctasknet.Request;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Map;

/**
 * Created by niehongtao on 16/6/18.
 */
public class HttpClientUtil {
    public static HttpResponse execute(Request request) throws IOException {
        switch (request.method) {
            case GET:
                return get(request);
            case POST:
                return post(request);
            default:
                throw new IllegalStateException("the method" + request.method.name() + "doesn't support");
        }
    }

    private static HttpResponse post(Request request) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(request.url);
        addHeader(post, request.headers);
        if (request.entity == null) {
            throw new IllegalStateException("you forget to set post content to the HttpPost");
        } else {
            post.setEntity(request.entity);
        }
        HttpResponse response = client.execute(post);
        return response;
    }

    private static HttpResponse get(Request request) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(request.url);
        addHeader(get, request.headers);
        HttpResponse response = client.execute(get);
        return response;
    }

    public static void addHeader(HttpUriRequest request, Map<String, String> headers) {
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

}
