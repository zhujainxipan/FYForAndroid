package com.ht.fyforandroid.net.httpclient;

import com.ht.fyforandroid.util.FileUtil;
import com.ht.fyforandroid.util.TextUtil;

import org.apache.commons.io.IOUtils;

/**
 * Created by niehongtao on 16/6/18.
 */
public abstract class StringCallBack extends AbstractCallback{

    @Override
    protected Object bindData(String content) {
        if (TextUtil.isValidate(path)) {
            return FileUtil.readFromFile(path);
        }
        return content;
    }
}
