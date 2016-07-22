package com.ht.fyforandroid.net.img;

import android.widget.ImageView;

import com.ht.fyforandroid.application.BaseApplication;
import com.squareup.picasso.Picasso;

import java.io.File;

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
        Picasso.with(BaseApplication.getContext()).load(url).into(imageView);

    }

    @Override
    public void loadImageFromFile(ImageView imageView, String uri) {
        Picasso.with(BaseApplication.getContext()).load(new File(uri)).into(imageView);
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
