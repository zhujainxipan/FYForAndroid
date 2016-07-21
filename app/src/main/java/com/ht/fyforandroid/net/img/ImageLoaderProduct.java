package com.ht.fyforandroid.net.img;

import android.widget.ImageView;

/**
 * Created by niehongtao on 16/7/21.
 */
public interface ImageLoaderProduct {
    /**
     * 从网络加载图片
     *
     * @param imageView
     * @param url
     */
    void loadImageFromNet(ImageView imageView, String url);


    /**
     * 从本地加载图片
     *
     * @param imageView
     * @param uri
     */
    void loadImageFromFile(ImageView imageView, String uri);
}
