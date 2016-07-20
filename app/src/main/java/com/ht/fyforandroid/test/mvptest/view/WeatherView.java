package com.ht.fyforandroid.test.mvptest.view;

import com.ht.fyforandroid.base.BaseView;
import com.ht.fyforandroid.test.mvptest.bean.WeatherEntity;

/**
 * Created by niehongtao on 16/7/18.
 */
public interface WeatherView extends BaseView{

    void onFetchDataSuccess(WeatherEntity entity);

    void onShowLoading();

    void onHideLoading();

    void onFetchDataError(String msg);
}
