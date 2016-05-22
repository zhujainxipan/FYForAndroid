package com.ht.fyforandroid.base;

import android.view.View;

/**
 * Created by niehongtao on 16/5/22.
 */
public class BaseViewPagerFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected int initView(View view) {
        return 0;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isOpenEventBus() {
        return false;
    }
}
