package com.ht.fyforandroid.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.ht.fyforandroid.R;

/**
 * Created by niehongtao on 16/7/7.
 * 继承ReplaceActivity 调用replaceFragment(new NewFragment) 使用Fragment代替Activity使用
 */
public abstract class ReplaceActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_replace;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    public void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, false);
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        if (fragment == null || fragment == getSupportFragmentManager().findFragmentById(R.id.container))
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
