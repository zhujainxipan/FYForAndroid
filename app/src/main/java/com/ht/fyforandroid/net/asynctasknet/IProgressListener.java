package com.ht.fyforandroid.net.asynctasknet;

/**
 * Created by niehongtao on 16/6/21.
 */
public interface IProgressListener {
    void onProgressUpdate(int curPos, int contentLength);
}
