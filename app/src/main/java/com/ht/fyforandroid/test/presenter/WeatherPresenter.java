package com.ht.fyforandroid.test.presenter;

import com.ht.fyforandroid.net.base.BaseRequest;
import com.ht.fyforandroid.net.base.BaseResponse;
import com.ht.fyforandroid.net.volleynet.VolleyNetHelper;
import com.ht.fyforandroid.test.bean.WeatherEntity;
import com.ht.fyforandroid.test.view.WeatherView;

import java.util.Map;

/**
 * Created by niehongtao on 16/7/18.
 */
public class WeatherPresenter extends BasePresenter<WeatherView> {

    public void fetchList() {
        mViewRef.get().onShowLoading();
        VolleyNetHelper.getInstance().doPost(new BaseRequest() {
            @Override
            public String getMobileApi() {
                return "http://api.map.baidu.com/telematics/v3/weather?location=guangzhou&output=json&ak=B95329fb7fdda1e32ba3e3a245193146";
            }

            @Override
            public Map<String, String> getParams() {
                return null;
            }
        }, new BaseResponse<WeatherEntity>() {
            @Override
            public void onSuccess(WeatherEntity o) {
                mViewRef.get().onHideLoading();
                mViewRef.get().onFetchDataSuccess(o);
            }

            @Override
            public void onError(String msg) {
                mViewRef.get().onHideLoading();
                mViewRef.get().onFetchDataError(msg);
            }

            @Override
            public Class<WeatherEntity> getResponseClass() {
                return WeatherEntity.class;
            }
        });


    }


}
