package com.ht.fyforandroid.net.img;

import android.widget.ImageView;

/**
 * Created by niehongtao on 16/7/21.
 */
public class PicassoLoaderProduct implements ImageLoaderProduct {
    private static PicassoLoaderProduct instance = new PicassoLoaderProduct();

    public static PicassoLoaderProduct getInstance() {
        if (instance == null) {
            instance = new PicassoLoaderProduct();
        }
        return instance;
    }

    private PicassoLoaderProduct() {
    }


    @Override
    public void loadImageFromNet(ImageView imageView, String url) {

    }

    @Override
    public void loadImageFromFile(ImageView imageView, String uri) {

    }

}
