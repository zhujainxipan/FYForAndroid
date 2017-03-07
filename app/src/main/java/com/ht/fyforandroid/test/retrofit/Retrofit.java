package com.ht.fyforandroid.test.retrofit;

import retrofit2.GsonConverterFactory;

/**
 * Created by niehongtao on 17/3/7.
 */
public class Retrofit {
    private IBeanService service;

    /**
     * 获取Retrofit实例
     * @return
     */
    public static Retrofit getRetrofit(){
        return new Retrofit();
    }

    private Retrofit() {
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://api.map.baidu.com/telematics/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(IBeanService.class);
    }

    /**
     * 获取IBeanService实例
     * @return
     */
    public IBeanService getService(){
        return service;
    }
}