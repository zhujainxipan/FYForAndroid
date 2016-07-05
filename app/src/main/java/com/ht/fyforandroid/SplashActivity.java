package com.ht.fyforandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ht.fyforandroid.base.BaseActivity;
import com.ht.fyforandroid.net.ImageLoaderHelper;
import com.ht.fyforandroid.net.asynctasknet.Request;
import com.ht.fyforandroid.net.asynctasknet.callback.JsonCallBack;
import com.ht.fyforandroid.net.asynctasknet.callback.StringCallBack;
import com.ht.fyforandroid.net.asynctasknet.http.UrlHelper;
import com.ht.fyforandroid.net.simplenet.core.RequestQueue;
import com.ht.fyforandroid.net.simplenet.core.SimpleNet;
import com.ht.fyforandroid.net.simplenet.requests.StringRequest;
import com.ht.fyforandroid.net.threadpoolnet.RequestManager;
import com.ht.fyforandroid.net.threadpoolnet.request.RequestCallback;
import com.ht.fyforandroid.util.DoubleClickExitHelper;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.io.File;

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
    @InjectView(R.id.btn_test_json)
    Button mBtnTestJson;
    @InjectView(R.id.tv_progress)
    TextView mTvProgress;
    @InjectView(R.id.btn_test_simplenet)
    Button mSimpleButton;
    @InjectView(R.id.btn_imageloader)
    Button mBtnImageLoader;
    @InjectView(R.id.iv_test)
    ImageView mIvTest;
    @InjectView(R.id.btn_test_threadpool)
    Button mThreadPoolButton;
    @InjectView(R.id.btn_picasso)
    Button mBtnPicasso;
    @InjectView(R.id.iv_picasso_test)
    ImageView mIvPicassoTest;
    @InjectView(R.id.btn_glide)
    Button mBtnGlide;
    @InjectView(R.id.iv_glide_test)
    ImageView mIvGlideTest;
    private DoubleClickExitHelper mDoubleClickExit;
    // 1、构建请求队列
    RequestQueue mQueue = SimpleNet.newRequestQueue();

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

        mThreadPoolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestCallback callback = new RequestCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        mTvResult.setText(response.toString());
                    }

                    @Override
                    public void onFail(String errorMessage) {

                    }
                };
                com.ht.fyforandroid.net.threadpoolnet.request.StringRequest request = new com.ht.fyforandroid.net.threadpoolnet.request.StringRequest(
                        "http://www.baidu.com",
                        com.ht.fyforandroid.net.threadpoolnet.request.Request.RequestMethod.GET,
                        callback,
                        null
                );
                RequestManager.getInstance().executeRequest(request);
            }
        });

        mBtnTestHttpclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestString();
            }
        });

        mBtnTestJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestJson();
            }
        });

        mSimpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStringRequest();
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
    }

    /**
     * 发送GET请求,返回的是String类型的数据, 同理还有{@see JsonRequest}、{@see MultipartRequest}
     */
    private void sendStringRequest() {
        StringRequest request = new StringRequest(com.ht.fyforandroid.net.simplenet.base.Request.HttpMethod.GET, "http://www.baidu.com",
                new com.ht.fyforandroid.net.simplenet.base.Request.RequestListener<String>() {

                    @Override
                    public void onComplete(int stCode, String response, String errMsg) {
                        mTvResult.setText(Html.fromHtml(response));
                    }
                });

        mQueue.addRequest(request);
    }


    private void requestJson() {
        final Request request = new Request(UrlHelper.TEST_JSON, Request.RequestMethod.GET);
        request.setCallback(new JsonCallBack() {
                                @Override
                                public void onFailure(Exception result) {
                                    result.printStackTrace();
                                }

                                @Override
                                public void onSuccess(Object result) {
                                    mTvResult.setText((String) result);
                                }

                                @Override
                                public void onProgressUpdate(int curPos, int contentLength) {

                                }

                                @Override
                                public Object onPreHandle(Object object) {
                                    return object;
                                }

                                @Override
                                public Object onPresRequest() {
                                    return null;
                                }
                            }


        );
        request.execute();
    }

    private void requestString() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ht_training_http.txt";
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

            @Override
            public void onProgressUpdate(int curPos, int contentLength) {
                mTvProgress.setText(curPos + "xxxxx" + contentLength);
            }

            @Override
            public Object onPreHandle(Object object) {
                // 比如需要把数据顺序再重新排列一下，或者插入到数据库中等等
                return object;
            }

            @Override
            public Object onPresRequest() {
                return null;
            }
        }.setPath(path));
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
