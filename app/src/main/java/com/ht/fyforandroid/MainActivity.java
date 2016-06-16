package com.ht.fyforandroid;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.ht.fyforandroid.base.BaseActivity;
import com.ht.fyforandroid.util.DoubleClickExitHelper;
import com.ht.fyforandroid.widget.EmptyLayout;

import butterknife.InjectView;

public class MainActivity extends BaseActivity {
    @InjectView(R.id.tv)
    TextView mTv;
    private DoubleClickExitHelper mDoubleClickExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
                MainActivity.super.mLoadingDialog.showLoading(EmptyLayout.NETWORK_ERROR);
            }
        }, 5000);
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
