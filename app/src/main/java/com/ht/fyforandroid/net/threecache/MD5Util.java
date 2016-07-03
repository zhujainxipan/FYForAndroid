package com.ht.fyforandroid.net.threecache;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件缓存的工具类，包含网址到文件名称的映射，因为图片名字都是网址
 * 文件缓存重要的是进行网址与文件名称的映射，因为网址的内容有一部分是不可以
 * 在文件名中使用的，因此必须要进行映射
 * 网址的映射主要采用md5或者sha1进行内容的转化，这样就确保图像网址一一对应。
 */
/**
 * Created by Android Studio
 * Project:WantToGo
 * Author:gudao[FR]
 * Email:lizhenmeng90@163.com
 * Date:2015/6/23
 */
public final class MD5Util {

    private MD5Util() {

    }

    /**
     * 将网址映射成一个特定的字符串信息，确保唯一
     * @param url
     * @return
     */
    public static String md5(String url) {
        String ret = null;
        if (url != null) {
            //针对数据进行算法处理，形成一个唯一的内容，每一个数据生成的内容都不同
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("MD5");
                //生成了url对应的唯一的信息
                byte[] data = digest.digest(url.getBytes());
                ret = toHex(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }


    /**
     * 将字节数组转化成其对应的16进制的数字对应的字符串
     * 为何这么用呢？
     * java md5加密时，为什么要手动将128位整数的每个字节转为16进制，然后用字符串表示呢
     * java中md5加密时，会用到MessageDigest类，该类返回一个128位的整数；网上有很多例子，
     * 都是将这个128位的整数转为16进制，再转成字符串；
     * 加密串太长的话，传输，使用都不方便，十六进制是比较通用的加密表示方法
     * @param data
     * @return
     */
    public static String toHex(byte[] data) {
        String ret = null;
        if (data != null) {
            StringBuilder sb = new StringBuilder();
            for (byte b : data) {
                int h, l;
                //低四位
                l = b & 0x0f;
                //高四位
                h = (b >> 4) & 0x0f;
                sb.append(Integer.toHexString(h));
                sb.append(Integer.toHexString(l));
            }
            ret = sb.toString();
        }
        return ret;
    }
}
