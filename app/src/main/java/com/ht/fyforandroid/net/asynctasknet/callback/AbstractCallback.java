package com.ht.fyforandroid.net.asynctasknet.callback;

import com.ht.fyforandroid.net.asynctasknet.IProgressListener;
import com.ht.fyforandroid.util.TextUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * Created by niehongtao on 16/6/18.
 */
public abstract class AbstractCallback implements ICallBack{
    public String path;
    private static final int IO_BUFFER_SIZE = 4 * 1024;

    // 做解析
    @Override
    public Object handle(HttpResponse response, IProgressListener iProgressListener) {
        // file,json,xml,image,string
        try {

            HttpEntity entity = response.getEntity();
            switch (response.getStatusLine().getStatusCode()) {
                // 返回时200的时候，我就去解析数据
                case HttpStatus.SC_OK:
                    // 文件，把服务器的返回直接写到文件里面,一般涉及文件的下载这里会有这一步吧，或者说加文件缓存的话
                    if (TextUtil.isValidate(path)) {
                        FileOutputStream fos = new FileOutputStream(path);
                        InputStream in = null;
                        if (entity.getContentEncoding() != null) {
                            String encoding = entity.getContentEncoding().getValue();
                            if (encoding != null && "gzip".equalsIgnoreCase(encoding)) {
                                in = new GZIPInputStream(entity.getContent());
                            } else if (encoding != null && "deflate".equalsIgnoreCase(encoding)) {
                                in = new InflaterInputStream(entity.getContent());
                            }
                        } else {
                            in = entity.getContent();
                        }
                        byte[] b = new byte[IO_BUFFER_SIZE];
                        int read;
                        long curPos = 0;
//                        long length = entity.getContentLength();
                        while ((read = in.read(b)) != -1) {
                            // update progress
                            curPos += read;
                            iProgressListener.onProgressUpdate((int) (curPos / 1024), (int) (entity.getContentLength()));
                            fos.write(b, 0, read);
                        }

                        fos.flush();
                        fos.close();
                        in.close();
                        return bindData(path);
                    } else {
                        return bindData(EntityUtils.toString(entity));
                    }
                default:
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected Object bindData(String content) {
        return content;
    }

    public AbstractCallback setPath(String path) {
        this.path = path;
        return this;
    }



}
