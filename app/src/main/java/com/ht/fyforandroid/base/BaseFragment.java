package com.ht.fyforandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ht.fyforandroid.util.MVPUtils;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by niehongtao on 16/5/16.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {
    public T mPresenter;


    @Nullable
    @Override
    final public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }


    @Override
    final public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        // MVP设计
        mPresenter = MVPUtils.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        init();

        if (isOpenEventBus()) {
            openEventBus();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void openEventBus() {
        EventBus.getDefault().register(this);
    }

    /**
     * 设置fragment的布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void init();

    protected abstract boolean isOpenEventBus();

}
