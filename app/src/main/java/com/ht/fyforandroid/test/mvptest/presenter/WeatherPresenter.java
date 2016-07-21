package com.ht.fyforandroid.test.mvptest.presenter;

import com.ht.fyforandroid.base.BaseModel;
import com.ht.fyforandroid.base.BasePresenter;
import com.ht.fyforandroid.net.base.BaseRequest;
import com.ht.fyforandroid.net.base.BaseResponse;
import com.ht.fyforandroid.net.json.VolleyNetHelper;
import com.ht.fyforandroid.test.mvptest.bean.WeatherEntity;
import com.ht.fyforandroid.test.mvptest.view.WeatherView;

import java.util.Map;

/**
 * Created by niehongtao on 16/7/18.
 * 如果没有Model，第二个参数写成BaseModel即可
 */
public class WeatherPresenter extends BasePresenter<WeatherView, BaseModel> {

    public void fetchList() {
        mView.onShowLoading();
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
                mView.onHideLoading();
                mView.onFetchDataSuccess(o);
            }

            @Override
            public void onError(String msg) {
                mView.onHideLoading();
                mView.onFetchDataError(msg);
            }

            @Override
            public Class<WeatherEntity> getResponseClass() {
                return WeatherEntity.class;
            }
        });


    }


}
