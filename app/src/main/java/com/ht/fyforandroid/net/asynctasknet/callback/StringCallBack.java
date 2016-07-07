package com.ht.fyforandroid.net.asynctasknet.callback;

import com.ht.fyforandroid.util.FileUtils;
import com.ht.fyforandroid.util.TextUtil;

/**
 * Created by niehongtao on 16/6/18.
 */
public abstract class StringCallBack extends AbstractCallback{

    @Override
    protected Object bindData(String content) {
        if (TextUtil.isValidate(path)) {
            return FileUtils.readFile(path);
        }
        return content;
    }
}
