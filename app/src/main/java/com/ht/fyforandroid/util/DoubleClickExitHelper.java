package com.ht.fyforandroid.util;

import android.support.v4.app.FragmentActivity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * Created by niehongtao on 16/5/21.
 * 双击退出帮助类
 */
public class DoubleClickExitHelper {

    private final FragmentActivity mActivity;

    private boolean isOnKeyBacking;
    private Handler mHandler;
    private Toast mBackToast;

    public DoubleClickExitHelper(FragmentActivity activity) {
        mActivity = activity;
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Activity onKeyDown事件
     * */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if(isOnKeyBacking) {
            mHandler.removeCallbacks(onBackTimeRunnable);
            if(mBackToast != null){
                mBackToast.cancel();
            }
            // 退出
            AppManager.getAppManager().appExit(mActivity);
            return true;
        } else {
            isOnKeyBacking = true;
            if(mBackToast == null) {
                mBackToast = Toast.makeText(mActivity, "再次点击退出应用", 2000);
            }
            mBackToast.show();
            mHandler.postDelayed(onBackTimeRunnable, 2000);
            return true;
        }
    }

    private Runnable onBackTimeRunnable = new Runnable() {

        @Override
        public void run() {
            isOnKeyBacking = false;
            if(mBackToast != null){
                mBackToast.cancel();
            }
        }
    };
}
