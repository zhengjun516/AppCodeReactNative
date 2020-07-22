package com.appcode.downloadsdk;

import com.appcode.downloadsdk.model.bean.DownloadData;

public interface IDownloadCallback {
    void onBefore();
    void onProgress(int progress);
    void onComplete(DownloadData data);
    void onFailed(String error);
    void onCanceled();
    void onPause();
    void onResume();
}
