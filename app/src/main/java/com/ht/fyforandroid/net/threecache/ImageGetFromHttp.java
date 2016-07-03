package com.ht.fyforandroid.net.threecache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ImageGetFromHttp {
    private static final String LOG_TAG = "ImageGetFromHttp";

    public static Bitmap downloadBitmap(String url,int toW,int toH) {
        Bitmap ret = null;
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("connection", "keep-alive");
            conn.connect();
            //获取输入流
            InputStream inputStream = conn.getInputStream();
            //读取图片数据到字节数组，便于后续的文件缓存操作，我们缓存的是二进制文件，而不是图片文件
            byte[] data = StreamUtil.readStream(inputStream);

            //对图片进行二次采样
            //进行图片的解码
            //1.进行图片的二次采样， 第一次获取图片尺寸，第二次缩放加载图片
            // 指定图片解码的时候，采用的参数
            BitmapFactory.Options opts = new BitmapFactory.Options();
            // 仅获取图片的宽高，图像的像素信息全都不加载
            // 不会占用太多内存
            opts.inJustDecodeBounds = true;
            // 注意，使用Options来设置解码的方式
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    data,
                    0,
                    data.length,
                    opts
            );
            int picW = opts.outWidth;
            int picH = opts.outHeight;
            //Log.d("testCaiYangbefore", picW + " " + picH);
            ret = ImagSampleUtil.getAfterBitmap(data, opts, toW, toH);
            //Log.d("testCaiYangafter", ret.getWidth() + " " + ret.getHeight());
            inputStream.close();
            //断开网络连接
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            return ret;
        }


}



