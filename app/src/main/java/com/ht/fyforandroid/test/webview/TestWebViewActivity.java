package com.ht.fyforandroid.test.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ht.fyforandroid.R;
import com.ht.fyforandroid.base.BaseActivity;

import butterknife.InjectView;

/**
 * Created by niehongtao on 16/7/7.
 * 进行仿微信加载WebView显示进度条,直接调用start()方法进行跳转.
 */
public class TestWebViewActivity extends BaseActivity {
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
        TestWebViewActivity.super.mLoadingDialog.hideLoading();
        handleIntent();
        this.mWebview.loadUrl(this.mUrl);
//        enableCustomClients();
    }

    private void handleIntent() {
        mUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");
    }


    private void enableCustomClients() {
        this.mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            /**
             * @param view The WebView that is initiating the callback.
             * @param url  The url of the page.
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        this.mWebview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

            }
        });
    }


    public static void startActivity(Context context, String url, String title, boolean isShow) {
        if (TextUtils.isEmpty(url)) throw new IllegalArgumentException("url must not be empty");
        Intent intent = new Intent(context, TestWebViewActivity.class);
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

    public boolean onKeyDown(int keyCoder,KeyEvent event){
        if(mWebview.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            mWebview.goBack();   //goBack()表示返回webView的上一页面
            return true;
        }
        return false;
    }
}
