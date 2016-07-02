package com.ht.fyforandroid.net;

import android.widget.ImageView;

import com.ht.fyforandroid.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by niehongtao on 16/6/29.
 */
public class ImageLoaderHelper {
    private static ImageLoaderHelper instance = new ImageLoaderHelper();

    //两像素圆角
    private DisplayImageOptions optionsRounded = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_launcher)
            .showImageForEmptyUri(R.mipmap.ic_launcher)
            .showImageOnFail(R.mipmap.ic_launcher)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .displayer(new RoundedBitmapDisplayer(2))
            .build();

    private ImageLoader imageLoader = ImageLoader.getInstance();


    public static ImageLoaderHelper getInstance() {
        if (instance == null) {
            instance = new ImageLoaderHelper();
        }
        return instance;
    }

    protected ImageLoaderHelper() {
    }

    public void loadImage(ImageView imageView, String url, DisplayImageOptions displayImageOptions, SimpleImageLoadingListener animate) {
        imageLoader.displayImage(url, imageView, displayImageOptions, animate);
    }

    public void loadImageFromUrl(ImageView imageView, String url) {
        imageLoader.displayImage(url, imageView, optionsRounded);
    }

    public void loadImageFromUrl(ImageView imageView, String url, DisplayImageOptions displayImageOptions) {
        imageLoader.displayImage(url, imageView, displayImageOptions);
    }

    public void loadImageWithListener(ImageView imageView, String url, ImageLoadingListener listener) {
        imageLoader.displayImage(url, imageView, optionsRounded, listener);
    }

}
