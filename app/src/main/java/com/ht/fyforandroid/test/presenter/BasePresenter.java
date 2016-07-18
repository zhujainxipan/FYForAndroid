package com.ht.fyforandroid.test.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by niehongtao on 16/7/18.
 */
public abstract class BasePresenter<T> {

    protected Reference<T> mViewRef;

    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

}
