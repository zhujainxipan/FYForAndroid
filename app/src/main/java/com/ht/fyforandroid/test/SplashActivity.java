package com.ht.fyforandroid.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ht.fyforandroid.R;
import com.ht.fyforandroid.base.BaseActivity;
import com.ht.fyforandroid.base.WebViewActivity;
import com.ht.fyforandroid.test.mvptest.view.WeahterActivity;
import com.ht.fyforandroid.util.DoubleClickExitHelper;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;


/**
 * 未使用MVP重构
 */
public class SplashActivity extends BaseActivity {
    @InjectView(R.id.tv)
    TextView mTv;
    @InjectView(R.id.btn_enter)
    Button mBtnEnter;
    @InjectView(R.id.btn_imageloader)
    Button mBtnImageLoader;
    @InjectView(R.id.iv_test)
    ImageView mIvTest;
    @InjectView(R.id.btn_picasso)
    Button mBtnPicasso;
    @InjectView(R.id.iv_picasso_test)
    ImageView mIvPicassoTest;
    @InjectView(R.id.btn_glide)
    Button mBtnGlide;
    @InjectView(R.id.iv_glide_test)
    ImageView mIvGlideTest;
    @InjectView(R.id.btn_webview)
    Button mBtnWebview;
    @InjectView(R.id.btn_mvp)
    Button mBtnMvp;
    private DoubleClickExitHelper mDoubleClickExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mDoubleClickExit = new DoubleClickExitHelper(this);


        mTv.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.super.mLoadingDialog.hideLoading();
            }
        }, 5000);

        mBtnWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.startActivity(SplashActivity.this, "http://www.baidu.com/", "百度一下");
            }
        });

        mBtnGlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(SplashActivity.this)
                        .load("http://www.baidu.com/img/bdlogo.png")
                        .into(mIvGlideTest);
            }
        });

        mBtnPicasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(SplashActivity.this).load("http://www.baidu.com/img/bdlogo.png").into(mIvPicassoTest);
            }
        });
        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        mBtnImageLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoaderHelper.getInstance().loadImageWithListener(mIvTest,
                        "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png",
                        new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String s, View view) {

                            }

                            @Override
                            public void onLoadingFailed(String s, View view, FailReason failReason) {

                            }

                            @Override
                            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                Log.d("wwww", "加载成功");
                            }

                            @Override
                            public void onLoadingCancelled(String s, View view) {

                            }
                        });
            }
        });


        mBtnMvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, WeahterActivity.class);
                startActivity(intent);
            }
        });
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
