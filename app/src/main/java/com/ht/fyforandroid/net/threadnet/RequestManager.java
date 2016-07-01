package com.ht.fyforandroid.net.threadnet;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


public class RequestManager implements Executor{
    private static RequestManager instance = new RequestManager();
    // 异步请求列表
    ArrayList<HttpRequest> requestList = new ArrayList<HttpRequest>();

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
    public void addRequest(final HttpRequest request) {
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
    public HttpRequest createRequest(final URLData urlData,
                                     final RequestCallback requestCallback) {
        return createRequest(urlData, null, requestCallback);
    }

    /**
     * 有参数调用
     */
    public HttpRequest createRequest(final URLData urlData,
                                     final List<RequestParameter> params,
                                     final RequestCallback requestCallback) {
        final HttpRequest request = new HttpRequest(urlData, params,
                requestCallback);

        addRequest(request);
        return request;
    }


    /**
     * 执行网络请求
     * @param request
     */
    @Override
    public void execute(Runnable request) {
        DefaultThreadPool.getInstance().execute(request);
    }
}
