package com.ht.fyforandroid.base;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.ht.fyforandroid.R;
import com.ht.fyforandroid.widget.SwipeBackLayout;

/**
 * Created by niehongtao on 16/7/7.
 */
public abstract class SwipBackActivity extends BaseActivity {
    protected SwipeBackLayout mSwipBack;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mSwipBack = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.base_swip_layout, null);
        mSwipBack.attachToActivity(this);
    }
}
