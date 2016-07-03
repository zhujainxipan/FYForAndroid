package com.ht.fyforandroid.net.threecache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class ImageFileCache {
    private static final String CACHDIR = "wantToGoImgCache";
    private static final String WHOLESALE_CONV = ".cach";

    private static final int MB = 1024*1024;
    private static final int CACHE_SIZE = 10;
    private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;

    public ImageFileCache() {
        //清理文件缓存
        removeCache(getDirectory());
    }

    /** 从缓存中获取图片 **/
    public Bitmap getImage(final String url) {
        File file = new File(getDirectory(),MD5Util.md5(url));
        if (file.exists()) {
            //Log.d("ImageFileCache","绝对路径："+file.getAbsolutePath());
            Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (bmp == null) {
                file.delete();
            } else {
                updateFileTime(file.getAbsolutePath());
                return bmp;
            }
        }else{
            //Log.d("ImageFileCache","文件不存在");
        }
        return null;
    }

    /** 将图片存入文件缓存 **/
    public void saveBitmap(Bitmap bm, String url) {
        if (bm == null) {
            return;
        }
        //判断sdcard上的空间
        if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
            //SD空间不足
            return;
        }
        //进行映射
        String filename = MD5Util.md5(url);
        //获取缓存目录
        File dirFile = getDirectory();
        //缓存目录不存在，建立缓存目录
        if (!dirFile.exists())
            dirFile.mkdirs();
        //新建文件对象
        File file = new File(dirFile,filename);
        try {
            //在缓存文件下创建文件
            file.createNewFile();

            OutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            Log.w("ImageFileCache", "FileNotFoundException");
        } catch (IOException e) {
            Log.w("ImageFileCache", "IOException");
        }
    }

    /**
     * 计算存储目录下的文件大小，
     * 当文件总大小大于规定的CACHE_SIZE或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定
     * 那么删除40%最近没有被使用的文件
     */
    private boolean removeCache(File dirFile) {
        File[] files = dirFile.listFiles();
        if (files == null) {
            return true;
        }
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return false;
        }

        int dirSize = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(WHOLESALE_CONV)) {
                dirSize += files[i].length();
            }
        }

        if (dirSize > CACHE_SIZE * MB || FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
            int removeFactor = (int) ((0.4 * files.length) + 1);
            Arrays.sort(files, new FileLastModifSort());
            for (int i = 0; i < removeFactor; i++) {
                if (files[i].getName().contains(WHOLESALE_CONV)) {
                    files[i].delete();
                }
            }
        }

        if (freeSpaceOnSd() <= CACHE_SIZE) {
            return false;
        }

        return true;
    }

    /** 修改文件的最后修改时间 **/
    public void updateFileTime(String path) {
        File file = new File(path);
        long newModifiedTime = System.currentTimeMillis();
        file.setLastModified(newModifiedTime);
    }

    /** 计算sdcard上的剩余空间 **/
    private int freeSpaceOnSd() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdFreeMB = ((double)stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
        return (int) sdFreeMB;
    }


    /** 获得缓存目录 **/
    private File getDirectory() {
        File dir = new File(getSDPathFile(),CACHDIR);
        return dir;
    }

    /** 取SD卡路径 **/
    private File getSDPathFile() {
        File storageDirectory = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);  //判断sd卡是否存在
        if (sdCardExist) {
            storageDirectory = Environment.getExternalStorageDirectory();  //获取根目录
        }
        return storageDirectory;
    }

    /**
     * 根据文件的最后修改时间进行排序
     */
    private class FileLastModifSort implements Comparator<File> {
        public int compare(File arg0, File arg1) {
            if (arg0.lastModified() > arg1.lastModified()) {
                return 1;
            } else if (arg0.lastModified() == arg1.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }

}