package com.ht.fyforandroid.net.img;

import android.widget.ImageView;

import com.ht.fyforandroid.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by niehongtao on 16/7/21.
 */
public class UILoaderProduct implements ImageLoaderProduct {

    private static UILoaderProduct instance = new UILoaderProduct();

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


    public static UILoaderProduct getInstance() {
        if (instance == null) {
            instance = new UILoaderProduct();
        }
        return instance;
    }

    private UILoaderProduct() {
    }

    /**
     * 从网络加载图片
     *
     * @param imageView
     * @param url
     * @param displayImageOptions
     * @param animate
     */
    public void loadImageFromNet(ImageView imageView, String url, DisplayImageOptions displayImageOptions, SimpleImageLoadingListener animate) {
        imageLoader.displayImage(url, imageView, displayImageOptions, animate);
    }


    /**
     * 从网络加载图片
     *
     * @param imageView
     * @param url
     */
    public void loadImageFromNet(ImageView imageView, String url) {
        imageLoader.displayImage(url, imageView, optionsRounded);
    }

    /**
     * 从网络加载图片
     *
     * @param imageView
     * @param url
     * @param displayImageOptions
     */
    public void loadImageFromNet(ImageView imageView, String url, DisplayImageOptions displayImageOptions) {
        imageLoader.displayImage(url, imageView, displayImageOptions);
    }


    /**
     * 从网络加载图片
     *
     * @param imageView
     * @param url
     * @param listener
     */
    public void loadImageFromNet(ImageView imageView, String url, ImageLoadingListener listener) {
        imageLoader.displayImage(url, imageView, optionsRounded, listener);
    }


    /**
     * 从内存卡中异步加载本地图片
     *
     * @param uri
     * @param imageView
     */
    public void loadImageFromFile(String uri, ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        imageLoader.displayImage("file://" + uri, imageView);
    }


    /**
     * 从assets文件夹中异步加载图片
     *
     * @param imageName 图片名称，带后缀的，例如：1.png
     * @param imageView
     */
    public void loadImageFromAssets(String imageName, ImageView imageView) {
        // String imageUri = "assets://image.png"; // from assets
        imageLoader.displayImage("assets://" + imageName,
                imageView);
    }


    @Override
    public void loadImageFromFile(ImageView imageView, String uri) {

    }
}
