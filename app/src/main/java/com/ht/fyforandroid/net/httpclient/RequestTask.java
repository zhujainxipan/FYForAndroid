package com.ht.fyforandroid.net.httpclient;

import android.os.AsyncTask;

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
            Object object1 = mRequest.mCallback.onPresRequest();
            if (object1 != null) {
                return object1;
            }
            HttpResponse response = HttpClientUtil.execute(mRequest);
            Object object = mRequest.mCallback.handle(response, new IProgressListener() {
                @Override
                public void onProgressUpdate(int curPos, int contentLength) {
                    publishProgress(curPos, contentLength);
                }
            });
            return mRequest.mCallback.onPreHandle(object);
        } catch (IOException e) {
            e.printStackTrace();
            return e;
        }
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
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
