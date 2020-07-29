package com.appcode.downloadsdk.net;

import com.appcode.downloadsdk.DownloadTask;
import com.appcode.downloadsdk.DownloadManager;
import com.appcode.downloadsdk.IDownloadCallback;
import com.appcode.downloadsdk.IDownloadTask;
import com.appcode.downloadsdk.model.ProcessCall;
import com.appcode.downloadsdk.model.RepoManager;
import com.appcode.downloadsdk.model.bean.DownloadData;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkManager implements INetworkManager {

    private final OkHttpClient mClient;
    private final DownloadInterceptor mInterceptor;
    private final DownloadManager.UIHandler mUIHandler;
    private final RepoManager mRepoManager;

    public NetworkManager(DownloadManager.UIHandler uiHandler, RepoManager repoManager) {
        mUIHandler = uiHandler;
        mRepoManager = repoManager;

        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "Download dispatcher");
                thread.setDaemon(false);
                return thread;
            }
        });


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES));
        builder.dispatcher(new Dispatcher(executor));

        mInterceptor = new DownloadInterceptor(uiHandler);
        builder.addNetworkInterceptor(mInterceptor);

        mClient = builder.build();
    }

    public IDownloadTask enqueue(boolean isResume, final DownloadData data, final IDownloadCallback callback) {
        mUIHandler.sendBeforeMsg(callback);

        String url = data.getDownloadUrl();
        long offset = data.getOffset();

        Request.Builder builder = new Request.Builder();
        final Request request = builder.url(url).header("RANGE", "bytes=" + offset + "-").build();
        Call call = mClient.newCall(request);

        mInterceptor.addCallback(url, callback);
        mInterceptor.addOffset(url, offset);

        final DownloadTask downloadTask = new DownloadTask(this, data.getDownloadUrl(), callback);
        if (isResume) {
            mUIHandler.sendResumeMsg(callback);
        }

        downloadTask.setCall(call,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mUIHandler.sendFailedMsg(e.getMessage(), callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ProcessCall processCall = handleResponse(response, data, callback);
                downloadTask.setProcessCall(processCall);
            }
        });
        return downloadTask;
    }

    private ProcessCall handleResponse(Response response, DownloadData data, IDownloadCallback callback) {
        // TODO: 需要任务队列与下载任务队列匹配
        return mRepoManager.enqueue(response, data, mUIHandler, callback);
    }

    public RepoManager getRepoManager() {
        return mRepoManager;
    }
}
