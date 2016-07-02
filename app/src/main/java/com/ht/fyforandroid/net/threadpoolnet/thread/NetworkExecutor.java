package com.ht.fyforandroid.net.threadpoolnet.thread;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import android.os.Handler;

import com.ht.fyforandroid.net.simplenet.base.Response;
import com.ht.fyforandroid.net.threadpoolnet.Utils;
import com.ht.fyforandroid.net.threadpoolnet.request.Request;
import com.ht.fyforandroid.net.threadpoolnet.request.RequestParameter;

/**
 * NetworkExecutor是发起网络请求的地方，它实现了Runnable，从而让DefaultThreadPool
 * 可以分配新的线程来执行它，所以，所有的请求逻辑都在run方法中
 */
public class NetworkExecutor implements Runnable {
    private HttpUriRequest mHttpRequest = null;
    private Request mRequest = null;
    private String url = null;
    private DefaultHttpClient httpClient;

    protected Handler handler;

    public NetworkExecutor(final Request request) {
        mRequest = request;
        url = mRequest.getUrl();
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }
        handler = new Handler();
    }

    /**
     * 获取HttpUriRequest请求
     *
     * @return
     */
    public HttpUriRequest getHttpRequest() {
        return mHttpRequest;
    }

    @Override
    public void run() {
        try {
            List<RequestParameter> params = mRequest.getParams();

            if (mRequest.getMethod() == Request.RequestMethod.GET) {
                // 添加参数
                final StringBuffer paramBuffer = new StringBuffer();
                if ((params != null) && (params.size() > 0)) {
                    for (final RequestParameter p : params) {
                        if (paramBuffer.length() == 0) {
                            paramBuffer.append(p.getName() + "="
                                    + Utils.UrlEncodeUnicode(p.getValue()));
                        } else {
                            paramBuffer.append("&" + p.getName() + "="
                                    + Utils.UrlEncodeUnicode(p.getValue()));
                        }
                    }

                    String newUrl = url + "?" + paramBuffer.toString();
                    mHttpRequest = new HttpGet(newUrl);
                } else {
                    mHttpRequest = new HttpGet(url);
                }
            } else if (mRequest.getMethod() == Request.RequestMethod.POST) {
                mHttpRequest = new HttpPost(url);
                // 添加参数
                if ((params != null) && (params.size() > 0)) {
                    final List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
                    for (final RequestParameter p : params) {
                        list.add(new BasicNameValuePair(p.getName(), p
                                .getValue()));
                    }

                    ((HttpPost) mHttpRequest).setEntity(new UrlEncodedFormEntity(
                            list, HTTP.UTF_8));
                }
            } else {
                return;
            }

            mHttpRequest.getParams().setParameter(
                    CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
            mHttpRequest.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                    30000);

            // 发送请求
            HttpResponse response = httpClient.execute(mHttpRequest);
            // 构建Response
            final Response rawResponse = new Response(response.getStatusLine());
            // 设置Entity
            rawResponse.setEntity(response.getEntity());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mRequest.deliveryResponse(rawResponse);
                }
            });

        } catch (final IllegalArgumentException e) {
            handleNetworkError("网络异常");
        } catch (final UnsupportedEncodingException e) {
            handleNetworkError("网络异常");
        } catch (final IOException e) {
            handleNetworkError("网络异常");
        }
    }

    /**
     * 回调的操作是在主线程中，所以这里就不得不使用Handler来把回调分发到UI线程
     *
     * @param errorMsg
     */
    public void handleNetworkError(final String errorMsg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mRequest.getCallback().onFail(errorMsg);
            }
        });
    }
}