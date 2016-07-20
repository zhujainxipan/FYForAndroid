package com.ht.fyforandroid.base;


import com.ht.fyforandroid.util.MVPUtils;

/**
 * Created by niehongtao on 16/7/20.
 * MVP模式 P基类
 */
public abstract class BasePresenter< T extends BaseView, E extends BaseModel> {
    public E mModel;
    public T mView;


    public void attachView(T v) {
        this.mView = v;
        this.mModel = MVPUtils.getT(this, 1);
    }


    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }

}
