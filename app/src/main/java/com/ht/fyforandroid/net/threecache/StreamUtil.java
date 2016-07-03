package com.ht.fyforandroid.net.threecache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Android Studio
 * Project:WantToGo
 * Author:gudao[FR]
 * Email:lizhenmeng90@163.com
 * Date:2015/6/23
 */
public class StreamUtil {

    public static byte[] readStream(InputStream inputStream) throws IOException {
        byte[] ret = null;
        if (inputStream != null) {
            byte[] buf = new byte[128];
            int len;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            while(true){
                len = inputStream.read(buf);
                if (len!=-1){
                    bout.write(buf,0,len);
                }else{
                    break;
                }
            }
            ret = bout.toByteArray();
            bout.close();
        }
        return  ret;
    }
}
