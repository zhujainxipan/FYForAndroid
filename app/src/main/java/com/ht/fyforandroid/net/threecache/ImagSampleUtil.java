package com.ht.fyforandroid.net.threecache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImagSampleUtil {

    /**
     * 对从网络上得到的图片进行二次采样
     * @return
     */
    public static Bitmap getAfterBitmap(byte[] data, BitmapFactory.Options opts, int toW, int toH) {
        //2. 进行图片尺寸的缩放，使用到 inSampleSize，还要获取 bitmap
        Bitmap ret = null;
        opts.inJustDecodeBounds = false;
        // 设置图片的缩小比例
        // inSampleSize 必须 >= 1 同时建议是 2 的次方的值 这样解码和缩小的效率是最快的
        if(toW==0 || toH==0){
            opts.inSampleSize = calcSampleSize(opts.outWidth,opts.outHeight,opts.outWidth,opts.outHeight);
        }else{
            opts.inSampleSize = calcSampleSize(opts.outWidth,opts.outHeight,toW,toH);
        }

        //calcSampleSize(picW, picH, 400, 200)
        // 当手机内存不足时，通过 inPurgeable = true 就会自动释放 bitmap的像素数据
        // 如果释放之后，再次需要bitmap的时候，系统会自动解码像素的数据，这个数据必须存在。
        opts.inPurgeable = true;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;

        // 再次使用解码设置来解析图像。
        ret = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        return ret;
    }



    /**
     * 通过原始的图片尺寸与需要缩放到的尺寸进行计算，
     * 生成 BitmapFactory.Options 当中设置图片采样率 inSampleSize 的数值
     * 谷歌官方提供的采样算法
     * @param picW
     * @param picH
     * @param toW
     * @param toH
     * @return
     */
    public static int calcSampleSize(int picW, int picH, int toW, int toH) {
        int ret = 1;

        int halfW = picW;
        int halfH = picH;

        while (halfH > toH && halfW > toW) {
            ret *= 2;
            halfH /= 2;
            halfW /= 2;
        }

        return ret;
    }

}
