package com.appcode.downloadsdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.appcode.downloadsdk.model.RepoManager;
import com.appcode.downloadsdk.model.bean.DownloadData;
import com.appcode.downloadsdk.net.NetworkManager;
import com.appcode.downloadsdk.utils.FileUtil;

import java.io.File;

public class DownloadManager implements IDownloader {


    private static Context mContext;
    private static DownloadConfig mDownloadConfig;
    private static boolean mInit;

    private NetworkManager mNetWorkManager;
    private UIHandler mUIHandler;
    private RepoManager mRepoManager;



    /**
     * 下载器有几部分组成：
     * 1 存储部分：提供查询，存储，管理本地数据的功能
     * 2 网络部分：提供联网，下载功能
     * 3 Downloader, 事件转发
     */
    private static final class InstanceHolder {
        private static final DownloadManager INSTANCE = new DownloadManager();
    }

    private DownloadManager() {
        mUIHandler = new UIHandler();
        mRepoManager = new RepoManager(mContext, mDownloadConfig.getDownloadRootPath());
        mNetWorkManager = new NetworkManager(mUIHandler, mRepoManager);
    }

    public static DownloadManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static void init(Context context){
        init(context,new DownloadConfig.Builder().build());
    }

    public static void init(Context context, DownloadConfig config) {
        mContext = context;
        mDownloadConfig = config;
        mInit = true;
        File dlDir = new File(config.getDownloadRootPath());
        if (!FileUtil.isDirExists(dlDir)) {
            dlDir.mkdirs();
        }
    }

    @Override
    public IDownloadTask download(String url, IDownloadCallback callback) {
        checkInit();
        DownloadData data = mRepoManager.getData(url);
        return mNetWorkManager.enqueue(false, data, callback);
    }

    @Override
    public boolean isDownloaded(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return mRepoManager.isDownload(url);
    }

    public String getLocalUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        DownloadData data = mRepoManager.getData(url);
        if (data != null) {
            return data.getLocalUrl();
        }
        return null;
    }

    private void checkInit() {
        if (!mInit) {
            throw new RuntimeException("You Need Init Downloader Firstly!!!");
        }
    }

    public class UIHandler extends Handler {

        public class MessageData {
            private int mProgress;
            private DownloadData mData;
            private IDownloadCallback mCallback;
            private String mError;

            public MessageData(IDownloadCallback callback) {
                this.mCallback = callback;
            }

            public MessageData(int progress, IDownloadCallback callback) {
                this.mProgress = progress;
                this.mCallback = callback;
            }

            public MessageData(DownloadData data, IDownloadCallback callback) {
                this.mData = data;
                this.mCallback = callback;
            }

            public MessageData(String error, IDownloadCallback callback) {
                this.mError = error;
                this.mCallback = callback;
            }

        }

        public UIHandler() {
            super(Looper.getMainLooper());
        }

        public static final int MESSAGE_BEFORE = 0x0;
        public static final int MESSAGE_PROCESS = 0x1;
        public static final int MESSAGE_COMPLETE = 0x2;
        public static final int MESSAGE_CANCEL = 0x3;
        public static final int MESSAGE_FAILED = 0x4;
        public static final int MESSAGE_PAUSE = 0x5;
        public static final int MESSAGE_RESUME = 0x6;

        @Override
        public void handleMessage(Message msg) {
            MessageData messageData = (MessageData) msg.obj;
            if (messageData == null || messageData.mCallback == null) {
                return;
            }
            switch (msg.what) {
                case MESSAGE_BEFORE:
                    messageData.mCallback.onBefore();
                    break;
                case MESSAGE_PROCESS:
                    messageData.mCallback.onProgress(messageData.mProgress);
                    break;
                case MESSAGE_COMPLETE:
                    messageData.mCallback.onComplete(messageData.mData);
                    break;
                case MESSAGE_CANCEL:
                    messageData.mCallback.onCanceled();
                    break;
                case MESSAGE_FAILED:
                    messageData.mCallback.onFailed(messageData.mError);
                    break;
                case MESSAGE_PAUSE:
                    messageData.mCallback.onPause();
                    break;
                case MESSAGE_RESUME:
                    messageData.mCallback.onResume();
                    break;
                default:
                    break;
            }
        }

        public void sendBeforeMsg(IDownloadCallback callback) {
            Message msg = Message.obtain();
            msg.what = MESSAGE_BEFORE;
            msg.obj = new MessageData(callback);
            sendMessage(msg);
        }

        public void sendProcessMsg(int progress, IDownloadCallback callback) {
            Message msg = Message.obtain();
            msg.what = MESSAGE_PROCESS;
            msg.obj = new MessageData(progress, callback);
            sendMessage(msg);
        }

        public void sendCompleteMsg(DownloadData data, IDownloadCallback callback) {
            Message msg = Message.obtain();
            msg.what = MESSAGE_COMPLETE;
            msg.obj = new MessageData(data, callback);
            sendMessage(msg);
        }

        public void sendCancelMsg(IDownloadCallback callback) {
            Message msg = Message.obtain();
            msg.what = MESSAGE_CANCEL;
            msg.obj = new MessageData(callback);
            sendMessage(msg);
        }

        public void sendFailedMsg(String error, IDownloadCallback callback) {
            Message msg = Message.obtain();
            msg.what = MESSAGE_FAILED;
            msg.obj = new MessageData(error, callback);
            sendMessage(msg);
        }

        public void sendPauseMsg(IDownloadCallback callback) {
            Message msg = Message.obtain();
            msg.what = MESSAGE_PAUSE;
            msg.obj = new MessageData(callback);
            sendMessage(msg);
        }

        public void sendResumeMsg(IDownloadCallback callback) {
            Message msg = Message.obtain();
            msg.what = MESSAGE_RESUME;
            msg.obj = new MessageData(callback);
            sendMessage(msg);
        }
    }

    public static class DefaultDownloadCallback implements IDownloadCallback {

        @Override
        public void onBefore() {

        }

        @Override
        public void onProgress(int process) {

        }

        @Override
        public void onComplete(DownloadData data) {

        }

        @Override
        public void onFailed(String error) {

        }

        @Override
        public void onCanceled() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onResume() {

        }
    }

}
