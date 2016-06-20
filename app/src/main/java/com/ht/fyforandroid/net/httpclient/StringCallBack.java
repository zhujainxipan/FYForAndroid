package com.ht.fyforandroid.net.httpclient;

import com.ht.fyforandroid.util.FileUtil;
import com.ht.fyforandroid.util.TextUtil;

import org.apache.commons.io.IOUtils;

/**
 * Created by niehongtao on 16/6/18.
 */
public class StringCallBack extends AbstractCallback{

    @Override
    protected Object bindData(String content) {
        if (TextUtil.isValidate(path)) {
            return FileUtil.readFromFile(path);
        }
        return content;
    }

    @Override
    public void onFailure(Exception result) {

    }

    @Override
    public void onSuccess(Object result) {

    }
}
