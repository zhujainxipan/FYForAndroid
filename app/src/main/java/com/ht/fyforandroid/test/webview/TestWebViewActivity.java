package com.ht.fyforandroid.test.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ht.fyforandroid.R;
import com.ht.fyforandroid.base.BaseActivity;
import com.ht.fyforandroid.test.js.MyObject;

import butterknife.InjectView;

/**
 * Created by niehongtao on 16/7/7.
 * 进行仿微信加载WebView显示进度条,直接调用start()方法进行跳转.
 */
public class TestWebViewActivity extends BaseActivity {
    @InjectView(R.id.webview)
    WebView mWebview;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        TestWebViewActivity.super.mLoadingDialog.hideLoading();
        mWebview.loadUrl("file:///android_asset/test.html");
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (uri.getScheme().equals("xl")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });

        WebSettings webSettings = mWebview.getSettings();
        //①设置WebView允许调用js
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        //②将object对象暴露给Js,调用addjavascriptInterface
        mWebview.addJavascriptInterface(new MyObject(TestWebViewActivity.this), "myObj");
    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TestWebViewActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebview != null) {
            mWebview.destroy();
        }
    }

}
