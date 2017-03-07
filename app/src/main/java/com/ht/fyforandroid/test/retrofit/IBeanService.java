package com.ht.fyforandroid.test.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by niehongtao on 17/3/7.
 */
public interface IBeanService {
    @GET("weather")
    Call<WeatherEntity> getMenuById(@Query("location") String location,
                                    @Query("output") String output,
                                    @Query("ak") String ak);
}