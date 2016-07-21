package com.ht.fyforandroid.net.glide;

import android.widget.ImageView;

/**
 * Created by niehongtao on 16/7/21.
 */
public class GlideHelper {
    private static GlideHelper instance = new GlideHelper();

    public static GlideHelper getInstance() {
        if (instance == null) {
            instance = new GlideHelper();
        }
        return instance;
    }

    protected GlideHelper() {
    }

    /**
     * 从网络加载图片
     * @param imageView
     * @param url
     */
    public void loadImageFromNet(ImageView imageView, String url) {

    }


    /**
     * 从本地加载图片
     * @param imageView
     * @param uri
     */
    public void loadImageFromLoc(ImageView imageView, String uri) {

    }


}
