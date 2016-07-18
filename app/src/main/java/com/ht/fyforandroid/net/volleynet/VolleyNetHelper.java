package com.ht.fyforandroid.net.volleynet;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ht.fyforandroid.application.BaseApplication;
import com.ht.fyforandroid.net.base.BaseRequest;
import com.ht.fyforandroid.net.base.BaseResponse;

/**
 * Created by niehongtao on 16/7/15.
 * 封装第三方库，降低第三方库和应用的耦合
 */
public class VolleyNetHelper {
    private RequestQueue mRequestQueue;
    private static VolleyNetHelper mInstance;

    private VolleyNetHelper() {
        mRequestQueue = new Volley().newRequestQueue(BaseApplication.getContext());
    }

    public static VolleyNetHelper getInstance() {
        if (mInstance == null) {
            mInstance = new VolleyNetHelper();
        }
        return mInstance;
    }


    public void doGet(final BaseRequest baseRequest, final BaseResponse baseResponse) {
        StringRequest request = new StringRequest(Request.Method.GET, baseRequest.getUrl(), new Response.Listener<String>() {

            @Override
            public void onResponse(String arg0) {
                baseResponse.doParase(arg0);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                baseResponse.onError(arg0.toString());
            }
        });
        mRequestQueue.add(request);
    }


    public void doPost(BaseRequest baseRequest, final BaseResponse baseResponse) {
        StringRequest request = new StringRequest(Request.Method.POST, baseRequest.getUrl(), new Response.Listener<String>() {

            @Override
            public void onResponse(String arg0) {
                baseResponse.doParase(arg0);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                baseResponse.onError(arg0.toString());
            }
        });
        mRequestQueue.add(request);
    }

}
