package com.ht.fyforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ht.fyforandroid.base.BaseActivity;
import com.ht.fyforandroid.net.httpclient.HttpClientUtil;
import com.ht.fyforandroid.net.httpclient.ICallBack;
import com.ht.fyforandroid.net.httpclient.Request;
import com.ht.fyforandroid.net.httpclient.StringCallBack;
import com.ht.fyforandroid.net.httpclient.UrlHelper;
import com.ht.fyforandroid.util.DoubleClickExitHelper;

import org.apache.http.HttpResponse;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActivity extends BaseActivity {
    @InjectView(R.id.tv)
    TextView mTv;
    @InjectView(R.id.btn_enter)
    Button mBtnEnter;
    @InjectView(R.id.btn_test_httpclient)
    Button mBtnTestHttpclient;
    @InjectView(R.id.tv_result)
    TextView mTvResult;
    private DoubleClickExitHelper mDoubleClickExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mDoubleClickExit = new DoubleClickExitHelper(this);
    }

    @Override
    protected void initView() {
        mTv.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.super.mLoadingDialog.hideLoading();
            }
        }, 5000);

        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mBtnTestHttpclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestString();
            }
        });
    }

    private void requestString() {
        final Request request = new Request(UrlHelper.TEST_STRING, Request.RequestMethod.GET);
        request.setCallback(new StringCallBack() {
            @Override
            public void onFailure(Exception result) {
                result.printStackTrace();
            }

            @Override
            public void onSuccess(Object result) {
                mTvResult.setText((String) result);
            }
        });
        request.execute();
    }

    @Override
    protected void initData() {

    }

    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否退出应用
            return mDoubleClickExit.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

}
