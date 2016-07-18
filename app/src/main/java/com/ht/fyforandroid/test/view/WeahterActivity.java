package com.ht.fyforandroid.test.view;

import android.os.Bundle;
import android.widget.TextView;

import com.ht.fyforandroid.R;
import com.ht.fyforandroid.base.BaseActivity;
import com.ht.fyforandroid.dialog.LoadingDialog;
import com.ht.fyforandroid.test.bean.WeatherEntity;
import com.ht.fyforandroid.test.presenter.WeatherPresenter;
import com.ht.fyforandroid.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by niehongtao on 16/7/18.
 */
public class WeahterActivity extends BaseActivity implements WeatherView {

    @InjectView(R.id.weather_tv)
    TextView mWeatherTv;
    private WeatherPresenter mWeatherPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weather;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mWeatherPresenter = new WeatherPresenter();
        mWeatherPresenter.attachView(this);
        mWeatherPresenter.fetchList();
    }

    @Override
    public void onFetchDataSuccess(WeatherEntity entity) {
        mWeatherTv.setText(entity.getDate());
    }

    @Override
    public void onShowLoading() {
        super.mLoadingDialog.showLoading(LoadingDialog.NETWORK_LOADING);
    }

    @Override
    public void onHideLoading() {
        super.mLoadingDialog.hideLoading();
    }

    @Override
    public void onFetchDataError(String msg) {
        ToastUtils.longShow(msg);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWeatherPresenter.detachView();
    }
}
