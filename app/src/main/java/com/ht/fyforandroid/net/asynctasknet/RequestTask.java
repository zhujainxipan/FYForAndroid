package com.ht.fyforandroid.net.asynctasknet;

import android.os.AsyncTask;

import com.ht.fyforandroid.net.asynctasknet.http.HttpClientUtil;

import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * Created by niehongtao on 16/6/18.
 */
public class RequestTask extends AsyncTask<Object, Integer, Object> {
    private Request mRequest;

    public RequestTask(Request request) {
        mRequest = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object... params) {
        try {
            // 如果需要在请求前先看本地和内存中有无缓存，可以放在onPresRequest里去做
            Object object1 = mRequest.mCallback.onPresRequest();
            if (object1 != null) {
                return object1;
            }
            // 具体的网络请求执行
            HttpResponse response = HttpClientUtil.execute(mRequest);
            Object object = mRequest.mCallback.handle(response, new IProgressListener() {
                @Override
                public void onProgressUpdate(int curPos, int contentLength) {
                    // 进度更新
                    publishProgress(curPos, contentLength);
                }
            });
            // 得到结果之后可以对数据进行预处理：比如重新排序、比如保存到数据库或文件中，这些都可以写在onPreHandle
            return mRequest.mCallback.onPreHandle(object);
        } catch (IOException e) {
            e.printStackTrace();
            return e;
        }
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        // 进度更新
        mRequest.mCallback.onProgressUpdate(values[0], values[1]);
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if (result instanceof Exception) {
            mRequest.mCallback.onFailure((Exception) result);
        } else {
            mRequest.mCallback.onSuccess(result);
        }
    }


}
