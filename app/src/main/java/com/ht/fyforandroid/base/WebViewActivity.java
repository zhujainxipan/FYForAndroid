package com.ht.fyforandroid.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ht.fyforandroid.R;
import com.ht.fyforandroid.util.WebViewUtil;

import butterknife.InjectView;

/**
 * Created by niehongtao on 16/7/7.
 * 进行仿微信加载WebView显示进度条,直接调用start()方法进行跳转.
 */
public class WebViewActivity extends BaseActivity {
    @InjectView(R.id.webview_pb)
    ProgressBar mWebviewPb;
    @InjectView(R.id.webview)
    WebView mWebview;
    private String mUrl;
    private String mTitle;
    private static final int PROGRESS_RATIO = 1000;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        WebViewActivity.super.mLoadingDialog.hideLoading();
        handleIntent();
        this.enableJavascript();
        this.enableCaching();
        this.enableCustomClients();
        this.enableAdjust();
        this.zoomedOut();
        this.mWebview.loadUrl(this.mUrl);
    }

    private void handleIntent() {
        mUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");
    }

    @Override
    protected void initView() {
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void enableJavascript() {
        this.mWebview.getSettings().setJavaScriptEnabled(true);
        this.mWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    private void enableCaching() {
        this.mWebview.getSettings().setAppCachePath(getFilesDir() + getPackageName() + "/cache");
        this.mWebview.getSettings().setAppCacheEnabled(true);
        this.mWebview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    private void enableAdjust() {
        this.mWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.mWebview.getSettings().setLoadWithOverviewMode(true);
    }

    private void zoomedOut() {
        this.mWebview.getSettings().setLoadWithOverviewMode(true);
        this.mWebview.getSettings().setUseWideViewPort(true);
        this.mWebview.getSettings().setSupportZoom(true);
    }

    private void enableCustomClients() {
        this.mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            /**
             * @param view The WebView that is initiating the callback.
             * @param url  The url of the page.
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("www.vmovier.com")) {
                    WebViewUtil.injectCss(WebViewActivity.this, WebViewActivity.this.mWebview, "vmovier.css");
                } else if (url.contains("video.weibo.com")) {
                    WebViewUtil.injectCss(WebViewActivity.this, WebViewActivity.this.mWebview, "weibo.css");
                } else if (url.contains("m.miaopai.com")) {
                    WebViewUtil.injectCss(WebViewActivity.this, WebViewActivity.this.mWebview, "miaopai.css");
                }
            }
        });
        this.mWebview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                WebViewActivity.this.mWebviewPb.setProgress(progress);
                setProgress(progress * PROGRESS_RATIO);
                if (progress >= 80) {
                    WebViewActivity.this.mWebviewPb.setVisibility(View.GONE);
                } else {
                    WebViewActivity.this.mWebviewPb.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context, String url, String title, boolean isShow) {
        if (TextUtils.isEmpty(url)) throw new IllegalArgumentException("url must not be empty");
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String url, String title) {
        startActivity(context, url, title, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mWebview != null) {
            this.mWebview.destroy();
        }
    }
}
