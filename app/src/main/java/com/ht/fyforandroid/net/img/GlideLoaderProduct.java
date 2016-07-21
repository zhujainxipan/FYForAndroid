package com.ht.fyforandroid.net.img;

import android.widget.ImageView;

/**
 * Created by niehongtao on 16/7/21.
 */
public class GlideLoaderProduct implements ImageLoaderProduct {

    private static GlideLoaderProduct instance = new GlideLoaderProduct();

    public static GlideLoaderProduct getInstance() {
        if (instance == null) {
            instance = new GlideLoaderProduct();
        }
        return instance;
    }

    private GlideLoaderProduct() {
    }


    @Override
    public void loadImageFromNet(ImageView imageView, String url) {

    }

    @Override
    public void loadImageFromFile(ImageView imageView, String uri) {

    }


    /**
     * 加载gif动画
     *
     * @param imageView
     * @param uri
     */
    public void loadGif(ImageView imageView, String uri) {
//        Glide.with(BaseApplication.getContext()).
    }


}
