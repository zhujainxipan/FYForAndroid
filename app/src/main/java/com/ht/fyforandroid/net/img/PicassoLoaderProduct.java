package com.ht.fyforandroid.net.img;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ht.fyforandroid.application.BaseApplication;

import java.io.File;

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
        Glide.with(BaseApplication.getContext()).load(url).into(imageView);
    }

    @Override
    public void loadImageFromFile(ImageView imageView, String uri) {
        Glide.with(BaseApplication.getContext()).load(new File(uri)).into(imageView);
    }

}
