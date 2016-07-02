package com.ht.fyforandroid.net.threadpoolnet;

import java.util.List;

/**
 * 一个单例，你可以调用它来创建一个request，
 * 然后放到DefaultThreadPool的一个线程池中去执行这个request
 */
public class RequestManager {
    private static RequestManager instance = new RequestManager();

    private RequestManager() {
    }

    public static RequestManager getInstance() {
        if (instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }

    /**
     * 取消网络请求
     */
    // 应该是是直接调用DefaultThreadPool里的方法吧，removeTaskFromQueue
    public void cancelRequest() {
        DefaultThreadPool.getInstance().removeAllTask();
    }

    /**
     * 无参数调用
     */
    public void createRequest(final Request request,
                              final RequestCallback requestCallback) {
        this.createRequest(request, null, requestCallback);
    }

    /**
     * 有参数调用
     */
    public void createRequest(final Request request,
                              final List<RequestParameter> params,
                              final RequestCallback requestCallback) {
        final NetworkExecutor executor = new NetworkExecutor(request, params,
                requestCallback);
        DefaultThreadPool.getInstance().execute(executor);
    }
}
