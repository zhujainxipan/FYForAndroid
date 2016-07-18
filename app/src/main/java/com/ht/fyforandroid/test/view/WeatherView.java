package com.ht.fyforandroid.test.view;

import com.ht.fyforandroid.test.bean.WeatherEntity;

/**
 * Created by niehongtao on 16/7/18.
 */
public interface WeatherView {

    void onFetchDataSuccess(WeatherEntity entity);

    void onShowLoading();

    void onHideLoading();

    void onFetchDataError(String msg);
}
