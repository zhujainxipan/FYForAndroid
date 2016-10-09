package com.ht.fyforandroid.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ht.fyforandroid.R;
import com.ht.fyforandroid.base.BaseActivity;
import com.ht.fyforandroid.test.scheme.Test1Activity;
import com.ht.fyforandroid.test.webview.TestWebViewActivity;
import com.ht.fyforandroid.net.img.ImageLoaderFactory;
import com.ht.fyforandroid.test.mvptest.view.WeahterActivity;
import com.ht.fyforandroid.util.DoubleClickExitHelper;

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
                TestWebViewActivity.startActivity(SplashActivity.this);
//                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("xl://goods:8888/goodsDetail?goodsId=10011002"));
//                startActivity(intent);
            }
        });

        mBtnGlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoaderFactory.createImageLoader(ImageLoaderFactory.TPYE_GLIDE).loadImageFromNet(mIvGlideTest, "http://www.baidu.com/img/bdlogo.png");
            }
        });

        mBtnPicasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoaderFactory.createImageLoader(ImageLoaderFactory.TPYE_PICASSO).loadImageFromNet(mIvPicassoTest, "http://www.baidu.com/img/bdlogo.png");
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
                ImageLoaderFactory.createImageLoader(ImageLoaderFactory.TPYE_UNIVERSAL_IMAGE_LOADER).
                        loadImageFromNet(
                                mIvTest,
                                "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png");
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
