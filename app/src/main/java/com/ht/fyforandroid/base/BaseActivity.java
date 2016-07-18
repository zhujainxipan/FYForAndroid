package com.ht.fyforandroid.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.ht.fyforandroid.R;
import com.ht.fyforandroid.util.ActivityManager;
import com.ht.fyforandroid.dialog.LoadingDialog;

import butterknife.ButterKnife;

/**
 * Created by niehongtao on 16/5/16.
 */
public abstract class BaseActivity extends FragmentActivity {

    public LoadingDialog mLoadingDialog;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 移除系统的标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (getLayoutId() != 0) {
            View contentView = getLayoutInflater().inflate(getLayoutId(), null);
            setContentView(contentView);
        }
        mLoadingDialog = new LoadingDialog(this);
        // 使用注解绑定控件
        ButterKnife.inject(this);
        init(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getActivityManager().removeActivity(this);
        ButterKnife.reset(this);
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    /**
     * 子类重写改方法，设置activity的布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);
}
