package com.ht.fyforandroid.net.threadpoolnet;


import java.util.ArrayList;
import java.util.List;

/**
 * 一个单例，你可以调用它来创建一个request，并将其添加到requestList中，
 * 然后放到DefaultThreadPool的一个线程池中去执行这个request
 */
public class RequestManager {
    private static RequestManager instance = new RequestManager();
    // 异步请求列表
    private ArrayList<HttpRequest> requestList = new ArrayList<HttpRequest>();

    private RequestManager() {
    }

    public static RequestManager getInstance() {
        if (instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }

    /**
     * 添加Request到列表
     */
    private void addRequest(final HttpRequest request) {
        requestList.add(request);
    }

    /**
     * 取消网络请求
     */
    public void cancelRequest() {
        if ((requestList != null) && (requestList.size() > 0)) {
            for (final HttpRequest request : requestList) {
                if (request.getRequest() != null) {
                    try {
                        request.getRequest().abort();
                        requestList.remove(request.getRequest());
                    } catch (final UnsupportedOperationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 无参数调用
     */
    public void createRequest(final URLData urlData,
                              final RequestCallback requestCallback) {
        this.createRequest(urlData, null, requestCallback);
    }

    /**
     * 有参数调用
     */
    public void createRequest(final URLData urlData,
                              final List<RequestParameter> params,
                              final RequestCallback requestCallback) {
        final HttpRequest request = new HttpRequest(urlData, params,
                requestCallback);
        addRequest(request);
        DefaultThreadPool.getInstance().execute(request);
    }
}
