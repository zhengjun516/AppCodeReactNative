package com.appcode.downloadsdk.net;


import com.appcode.downloadsdk.DLog;
import com.appcode.downloadsdk.Downloader;
import com.appcode.downloadsdk.IDownloadCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadInterceptor implements Interceptor {
    private static final String TAG = "DownloadInterceptor";

    private final Downloader.UIHandler mUIHandler;
    private Map<String, IDownloadCallback> mCallbacks;
    private Map<String, Long> mOffsets;

    public DownloadInterceptor(Downloader.UIHandler uiHandler) {
        mCallbacks = new HashMap<>();
        mOffsets = new HashMap<>();
        mUIHandler = uiHandler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        DLog.d(TAG,request.url().toString());

        Response response = chain.proceed(request);
        IDownloadCallback iDownloadCallback =  getCallback(request.url());
        if(iDownloadCallback == null){
            if(request.tag() instanceof  Request){
                Request request1 = (Request) request.tag();
                DLog.d(TAG,request1.url().toString());
                iDownloadCallback = getCallback(request1.url());
            }
        }

        return response.newBuilder().body(new DownloadResponseBody(response, getOffset(request.url()), mUIHandler, iDownloadCallback)).build();
    }

    public void addCallback(String url, IDownloadCallback callback) {
        mCallbacks.put(url, callback);
    }

    public void addOffset(String url, long offset) {
        mOffsets.put(url, offset);
    }

    private IDownloadCallback getCallback(HttpUrl url) {
        if (url == null) {
            return null;
        }
        return mCallbacks.get(url.toString());
    }

    private long getOffset(HttpUrl url) {
        if (url == null || !mOffsets.containsKey(url.toString())) {
            return 0;
        }
        return mOffsets.get(url.toString());
    }
}
