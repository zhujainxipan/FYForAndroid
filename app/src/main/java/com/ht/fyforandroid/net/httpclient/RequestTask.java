package com.ht.fyforandroid.net.httpclient;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * Created by niehongtao on 16/6/18.
 */
public abstract class RequestTask extends AsyncTask<Object, Integer, Object> {
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
            HttpResponse response = HttpClientUtil.execute(mRequest);
            return mRequest.mCallback.handle(response);
        } catch (IOException e) {
            e.printStackTrace();
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if (result instanceof Exception) {
            mRequest.mCallback.onFaulure((Exception) result);
        } else {
            mRequest.mCallback.onSuccess(result);
        }
    }


}
