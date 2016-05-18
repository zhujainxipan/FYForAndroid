package com.ht.fyforandroid.base;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

import com.ht.fyforandroid.AppManager;

import butterknife.ButterKnife;

/**
 * Created by niehongtao on 16/5/16.
 */
public abstract class BaseActivity extends FragmentActivity {


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        // 使用注解绑定控件
        ButterKnife.inject(this);
        init(savedInstanceState);
        initView();
        initData();
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        ButterKnife.reset(this);
    }

    /**
     * 子类重写改方法，设置activity的布局
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract void initData();
}
