package com.ht.fyforandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ht.fyforandroid.R;
import com.ht.fyforandroid.util.TDeviceUtils;

/**
 * Created by niehongtao on 16/5/26.
 */
public class EmptyLayout extends LinearLayout {
    private Context mContext;
    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int NODATA_ENABLE_CLICK = 5;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private TextView mTextview;
    private boolean clickEnable = true;
    private android.view.View.OnClickListener listener;


    public EmptyLayout(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        View.inflate(mContext, R.layout.view_empty_layout, this);
        mImageView = (ImageView) findViewById(R.id.img_error_layout);
        mProgressBar = (ProgressBar) findViewById(R.id.animProgress);
        mTextview = (TextView) findViewById(R.id.tv_error_layout);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickEnable && listener != null) {
                    listener.onClick(v);
                }
            }
        });
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickEnable && listener != null) {
                    listener.onClick(v);
                }
            }
        });
    }


    /**
     * 供外界调用，来设置布局的点击事件
     * @param listener
     */
    public void setOnLayoutClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void dismiss() {
        setVisibility(View.GONE);
    }


    /**
     * 供外界调用，来设置空页面的布局状态
     * @param i
     */
    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
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
                clickEnable = true;
                break;
            // 没有网络，加载中
            case NETWORK_LOADING:
                mProgressBar.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.GONE);
                mTextview.setText(R.string.error_view_loading);
                clickEnable = false;
                break;
            case NODATA:
                mImageView.setBackgroundResource(R.mipmap.page_icon_empty);
                mImageView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mTextview.setText(R.string.error_view_no_data);
                clickEnable = true;
                break;
            case HIDE_LAYOUT:
                setVisibility(View.GONE);
                break;
            case NODATA_ENABLE_CLICK:
                mImageView.setBackgroundResource(R.mipmap.page_icon_empty);
                mImageView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mTextview.setText(R.string.error_view_no_data);
                clickEnable = true;
                break;
            default:
                break;
        }
    }
}
