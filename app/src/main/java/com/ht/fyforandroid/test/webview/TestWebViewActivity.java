package com.ht.fyforandroid.test.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ht.fyforandroid.R;
import com.ht.fyforandroid.base.BaseActivity;

import butterknife.InjectView;

/**
 * Created by niehongtao on 16/7/7.
 * 进行仿微信加载WebView显示进度条,直接调用start()方法进行跳转.
 */
public class TestWebViewActivity extends BaseActivity {
    @InjectView(R.id.webview)
    WebView mWebview;
    private String mUrl;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        TestWebViewActivity.super.mLoadingDialog.hideLoading();
        handleIntent();
        mWebview.loadUrl(this.mUrl);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (uri.getScheme().equals("myscheme")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });

    }

    private void handleIntent() {
        mUrl = getIntent().getStringExtra("url");
    }


    public static void startActivity(Context context, String url) {
        if (TextUtils.isEmpty(url)) throw new IllegalArgumentException("url must not be empty");
        Intent intent = new Intent(context, TestWebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebview != null) {
            mWebview.destroy();
        }
    }

    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (mWebview.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            mWebview.goBack();   //goBack()表示返回webView的上一页面
            return true;
        }
        return false;
    }
}
