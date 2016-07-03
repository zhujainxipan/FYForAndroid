package com.ht.fyforandroid.net.threecache;

import android.app.Application;

/**
 * Created by annuo on 2015/6/16.
 */
public class MyApplication extends Application{
    private static ImageFileCache fileCache;
    private static ImageMemoryCache memoryCache;
    @Override
    public void onCreate() {
        super.onCreate();

        fileCache = new ImageFileCache();
        memoryCache = new ImageMemoryCache(getApplicationContext());
    }

    public static ImageFileCache getFileCache() {
        return fileCache;
    }

    public static ImageMemoryCache getMemoryCache() {
        return memoryCache;
    }

    public void exit() {
        System.exit(0);
    }
}
