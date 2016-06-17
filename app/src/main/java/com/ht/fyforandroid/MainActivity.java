package com.ht.fyforandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ht.fyforandroid.base.BaseActivity;
import com.ht.fyforandroid.widget.filter.DropDownButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by niehongtao on 16/6/16.
 */
public class MainActivity extends BaseActivity {
    @InjectView(R.id.btn_close)
    Button mBtnClose;
    @InjectView(R.id.chooseType)
    DropDownButton mChooseType;
    @InjectView(R.id.chooseLabel)
    DropDownButton mChooseLabel;
    @InjectView(R.id.chooseOrder)
    DropDownButton mChooseOrder;
    @InjectView(R.id.tabs)
    LinearLayout mTabs;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.mLoadingDialog.hideLoading();
    }

    @Override
    protected void initView() {
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initData() {

    }

}
