package com.ht.fyforandroid.base;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by niehongtao on 16/7/15.
 */
public abstract class AbsBaseAdapter<T> extends BaseAdapter {
    //上下文引用
    protected final Context context;
    //需要显示的数据
    protected final List<T> data;

    public AbsBaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (data != null) {
            ret = data.size();
        }
        return ret;
    }

    @Override
    public T getItem(int position) {
        T ret = null;
        if (data != null) {
            ret = data.get(position);
        }
        return ret;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
