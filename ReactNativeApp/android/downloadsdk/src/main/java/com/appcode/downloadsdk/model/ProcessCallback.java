package com.appcode.downloadsdk.model;

public interface ProcessCallback {
    void onProcess();
    void onCancel();
    void onFailed();
    void onComplete();
}
