package com.ht.fyforandroid.base;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.ht.fyforandroid.R;
import com.ht.fyforandroid.util.AppManager;

import butterknife.ButterKnife;

/**
 * Created by niehongtao on 16/5/16.
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            View contentView = getLayoutInflater().inflate(getLayoutId(), null);
            setContentView(contentView);
        }

        // 使用注解绑定控件
        ButterKnife.inject(this);
        init(savedInstanceState);
        initView();
        initData();
        AppManager.getAppManager().addActivity(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
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
