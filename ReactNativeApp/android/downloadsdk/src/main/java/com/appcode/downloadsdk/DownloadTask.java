package com.appcode.downloadsdk;

import android.util.Log;

import com.appcode.downloadsdk.model.ProcessCall;
import com.appcode.downloadsdk.model.bean.DownloadData;
import com.appcode.downloadsdk.net.NetworkManager;

import okhttp3.Call;

public class DownloadTask implements IDownloadTask {

    private  Call mCall;
    private final IDownloadCallback mCallback;
    private final NetworkManager mNetWorkManager;
    private String mDownloadUrl;
    private ProcessCall mProcessCall;

    private IDownloadTask mResumeTask;

    public DownloadTask(NetworkManager networkManager, String url, IDownloadCallback callback) {
        mDownloadUrl = url;
        mCallback = callback;
        mNetWorkManager = networkManager;
    }

    @Override
    public void cancel() {
        Call call = getRealCall();
        if (call != null) {
            call.cancel();
        }
        ProcessCall processCall = getRealProcessCall();
        if (processCall != null) {
            processCall.cancel();
        }
    }

    @Override
    public void pause() {
        Call call = getRealCall();
        if (call != null) {
            call.cancel();
        }
        ProcessCall processCall = getRealProcessCall();
        if (processCall != null) {
            processCall.pause();
        }
    }

    @Override
    public void resume() {
        DownloadData data = mNetWorkManager.getRepoManager().getData(mDownloadUrl);
        mResumeTask = mNetWorkManager.enqueue(true, data, mCallback);
    }

    private Call getRealCall() {
        if (mResumeTask != null) {
            return mResumeTask.getCall();
        }
        return mCall;
    }

    private ProcessCall getRealProcessCall() {
        if (mResumeTask != null) {
            return mResumeTask.getProcessCall();
        }
        return mProcessCall;
    }

    public void setProcessCall(ProcessCall processCall) {
        mProcessCall = processCall;
    }

    public void setCall(Call call) {
        mCall = call;
    }

    @Override
    public ProcessCall getProcessCall() {
        return mProcessCall;
    }

    @Override
    public Call getCall() {
        return mCall;
    }

    @Override
    public String getDownloadUrl() {
        return mDownloadUrl;
    }

}
