package com.ht.fyforandroid.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ht.fyforandroid.R;
import com.ht.fyforandroid.util.TDeviceUtils;

/**
 * Created by niehongtao on 16/6/16.
 */
public class LoadingDialog extends Dialog {
    private FragmentActivity mActivity;
    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int NODATA_ENABLE_CLICK = 5;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private TextView mTextview;


    public LoadingDialog(FragmentActivity activity) {
        super(activity, R.style.FloadNormalDialogStyle);
        this.mActivity = activity;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.view_empty_layout, null);
        setContentView(view);
        mImageView = (ImageView) findViewById(R.id.img_error_layout);
        mProgressBar = (ProgressBar) findViewById(R.id.animProgress);
        mTextview = (TextView) findViewById(R.id.tv_error_layout);
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                hideLoading();
                return false;
            }
        });
        setCancelable(false);
    }

    /**
     * 供外界调用，来设置空页面的布局状态
     * @param i
     */
    public void showLoading(int i) {
        if (null == mActivity || mActivity.isFinishing()) {
            return;
        }
        switch (i) {
            // 没有网络
            case NETWORK_ERROR:
                if (TDeviceUtils.checkNet()) {
                    mTextview.setText(R.string.error_view_load_error_click_to_refresh);
                    mImageView.setBackgroundResource(R.mipmap.pagefailed_bg);
                } else {
                    mTextview.setText(R.string.error_view_network_error_click_to_refresh);
                    mImageView.setBackgroundResource(R.mipmap.page_icon_network);
                }
                mImageView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                break;
            // 没有网络，加载中
            case NETWORK_LOADING:
                mProgressBar.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.GONE);
                mTextview.setText(R.string.error_view_loading);
                break;
            case NODATA:
                mImageView.setBackgroundResource(R.mipmap.page_icon_empty);
                mImageView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mTextview.setText(R.string.error_view_no_data);
                break;
            case HIDE_LAYOUT:
                setAllVisibility(View.GONE);
                break;
            case NODATA_ENABLE_CLICK:
                mImageView.setBackgroundResource(R.mipmap.page_icon_empty);
                mImageView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mTextview.setText(R.string.error_view_no_data);
                break;
            default:
                break;
        }
        show();
    }

    public void hideLoading() {
        if (isShowing()) {
            dismiss();
        }
    }

    public void setAllVisibility(int visiableState) {
        mImageView.setVisibility(visiableState);
        mProgressBar.setVisibility(visiableState);
        mTextview.setVisibility(visiableState);
    }
}
