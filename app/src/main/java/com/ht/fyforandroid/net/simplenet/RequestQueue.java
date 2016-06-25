package com.ht.fyforandroid.net.simplenet;

import com.ht.fyforandroid.net.simplenet.request.Request;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by niehongtao on 16/6/25.
 */
public final class RequestQueue {
    private BlockingQueue<Request<?>> mRequestQueue = new PriorityBlockingQueue<Request<?>>();

    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    public static int DEFALUT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;

    private int mDispatcherNmus = DEFALUT_CORE_NUMS;

    private NetWorkExecutor[] mDispatchers = null;

    private HttpStack mHttpStack;

    protected RequestQueue (int coreNums, HttpStack httpStack) {
        mDispatcherNmus = coreNums;

    }



}
