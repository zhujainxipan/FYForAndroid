package com.ht.fyforandroid.net.threecache;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class CacheImageAsyncTask extends AsyncTask<String, Integer, Bitmap> {
    private ImageView imageView;
    private ImageFileCache fileCache;
    private ImageMemoryCache memoryCache;
    private String imgType;
    private String url;
    private int toW = 0;
    private int toH = 0;

    public CacheImageAsyncTask(ImageView imageView) {
        this.imageView = imageView;
        fileCache = MyApplication.getFileCache();
        memoryCache = MyApplication.getMemoryCache();
    }


    public CacheImageAsyncTask(ImageView imageView,int toW,int toH) {
        this.imageView = imageView;
        fileCache = MyApplication.getFileCache();
        memoryCache = MyApplication.getMemoryCache();
        this.toH = toH;
        this.toW = toW;
    }

    /**
     * 加载图片给特定的imageview
     *
     * @param imageView
     */
    public CacheImageAsyncTask(ImageView imageView, String imgType) {
        this.imageView = imageView;
        fileCache = MyApplication.getFileCache();
        memoryCache = MyApplication.getMemoryCache();
        this.imgType = imgType;
    }


    public Bitmap getBitmap(String url,int toW,int toH) {

        // 从内存缓存中获取图片
        Bitmap result = memoryCache.getBitmapFromCache(url);
        if (result == null) {
            // 文件缓存中获取
            result = fileCache.getImage(url);
            if (result == null) {
                // 从网络获取
                result = ImageGetFromHttp.downloadBitmap(url,toW,toH);
                if (result != null) {
                    fileCache.saveBitmap(result, url);
                    memoryCache.addBitmapToCache(url, result);
                }
            } else {
                // 添加到内存缓存
                memoryCache.addBitmapToCache(url, result);
            }
        }
        return result;
    }

    public Bitmap getBitmap(String url) {
        return getBitmap(url,0,0);
    }

    protected Bitmap doInBackground(String... params) {
        url = params[0];
        if(toH!=0 && toW!=0){
            return getBitmap(url,toW,toH);
        }else{
            return getBitmap(url);
        }

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            if (imageView != null) {
                if (imgType != null && imgType.equals("avatar")) {
                    bitmap = ImageUtil.getRoundBitmap(bitmap);
                }
                Object tag = imageView.getTag();
                if (tag != null && tag instanceof String) {
                    String s = (String) tag;
                    if (s.equals(url)) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();

    }
}