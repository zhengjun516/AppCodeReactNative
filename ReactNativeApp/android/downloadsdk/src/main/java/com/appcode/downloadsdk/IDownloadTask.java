package com.appcode.downloadsdk;


import com.appcode.downloadsdk.model.ProcessCall;

import okhttp3.Call;

public interface IDownloadTask {
    void cancel();
    void pause();
    void resume();
    ProcessCall getProcessCall();
    Call getCall();
    String getDownloadUrl();
}
