package com.ht.fyforandroid;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.util.Stack;

/**
 * Created by niehongtao on 16/5/18.
 */
public class BaseApplication extends Application {
    public static Context context;
    public static int sWidthPix;



    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        sWidthPix = getResources().getDisplayMetrics().widthPixels;

        // 配置imageloader
        initImageLoader();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .diskCacheFileCount(300)
                .imageDownloader(new BaseImageDownloader(context))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheExtraOptions(sWidthPix / 3, sWidthPix / 3, null)
                .build();
        ImageLoader.getInstance().init(config);
    }


    public static Context getContext() {
        return context;
    }
}
