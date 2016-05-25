package com.ht.fyforandroid.util;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by niehongtao on 16/5/25.
 * 键盘帮助类
 * 一个简单的观察者模式，SoftKeyboardStateListener是观察者，SoftKeyboardStateHelper是被观察者
 */
public class SoftKeyboardStateHelper implements
        ViewTreeObserver.OnGlobalLayoutListener {

    /**
     * 观察者
     */
    public interface SoftKeyboardStateListener {
        void onSoftKeyboardOpened();
        void onSoftKeyboardClosed();
    }

    private final List<SoftKeyboardStateListener> listeners = new LinkedList<SoftKeyboardStateListener>();
    private final View activityRootView;


    public SoftKeyboardStateHelper(View activityRootView) {
        this.activityRootView = activityRootView;
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        final Rect r = new Rect();
        // r will be populated with the coordinates of your view that area still
        // visible.
        activityRootView.getWindowVisibleDisplayFrame(r);

        final int heightDiff = activityRootView.getRootView().getHeight()
                - (r.bottom - r.top);
        if (heightDiff > 100) { // if more than 100
            // pixels, its probably
            // a keyboard...
            notifyOnSoftKeyboardOpened();
        } else if (heightDiff < 100) {
            notifyOnSoftKeyboardClosed();
        }
    }


    public void addSoftKeyboardStateListener(SoftKeyboardStateListener listener) {
        listeners.add(listener);
    }

    public void removeSoftKeyboardStateListener(
            SoftKeyboardStateListener listener) {
        listeners.remove(listener);
    }

    private void notifyOnSoftKeyboardOpened() {
        for (SoftKeyboardStateListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardOpened();
            }
        }
    }

    private void notifyOnSoftKeyboardClosed() {
        for (SoftKeyboardStateListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardClosed();
            }
        }
    }
}
