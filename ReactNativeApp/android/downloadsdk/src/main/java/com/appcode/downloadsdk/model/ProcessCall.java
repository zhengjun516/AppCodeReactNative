package com.appcode.downloadsdk.model;

import android.text.TextUtils;

import com.appcode.downloadsdk.DownloadManager;
import com.appcode.downloadsdk.IDownloadCallback;
import com.appcode.downloadsdk.internal.NamedRunnable;
import com.appcode.downloadsdk.model.bean.DataState;
import com.appcode.downloadsdk.model.bean.DownloadData;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

public class ProcessCall {

    private final ProcessDispatcher mDispatcher;
    private final DownloadData mData;
    private final Response mResponse;
    private final IDownloadCallback mCallback;
    private final DownloadManager.UIHandler mUIHandler;
    private final Repo mDBRepo;
    private boolean mCanceled;
    private boolean mPaused;
    private ProcessTask mProcessTask;
    private boolean mIsSupportRange = false;

    public ProcessCall(ProcessDispatcher dispatcher, Repo repo, Response response, DownloadData data, DownloadManager.UIHandler uiHandler, IDownloadCallback callback) {
        mDispatcher = dispatcher;
        mData = data;
        mResponse = response;
        mCallback = callback;
        mUIHandler = uiHandler;
        mDBRepo = repo;
    }

    public void enqueue() {
        mProcessTask = new ProcessTask(this, mData);
        mDispatcher.enqueue(mProcessTask);
    }

    public void cancel() {
        mCanceled = true;
    }

    private void process() {
        if(!mResponse.isSuccessful()){
            mUIHandler.sendFailedMsg(mResponse.code()+"",mCallback);
        }

        String contentRange = mResponse.header("Content-Range");
        if (TextUtils.isEmpty(contentRange)) {
            mIsSupportRange = false;
            mData.setOffset(0);
            mDBRepo.delete(mData.getDownloadUrl());
        } else {
            mIsSupportRange = true;
        }
        if (isCanceled()) {
            mData.setOffset(0);
            mDBRepo.delete(mData.getDownloadUrl());
            mUIHandler.sendCancelMsg(mCallback);
            return;
        }
        if (isPaused()) {
            mDBRepo.modify(mData);
            mUIHandler.sendPauseMsg(mCallback);
            return;
        }
        if (mData.getState() == DataState.COMPLETE && mCallback != null ||
                (mResponse.code() == 416 && mCallback != null)) {
            mUIHandler.sendProcessMsg(100, mCallback);
            mUIHandler.sendCompleteMsg(mData, mCallback);
        }

        if (!mResponse.isSuccessful()) {
            return;
        }

        long length = mResponse.body().contentLength();
        long originOffset = mData.getOffset();
        InputStream inputStream = null;
        RandomAccessFile randomAccessFile = null;
        BufferedInputStream bis = null;
        try {
            if (isCanceled()) {
                mData.setOffset(0);
                mDBRepo.delete(mData.getDownloadUrl());
                mUIHandler.sendCancelMsg(mCallback);
                return;
            }
            if (isPaused()) {
                mDBRepo.modify(mData);
                mUIHandler.sendPauseMsg(mCallback);
                return;
            }
            inputStream = mResponse.body().byteStream();
            String localUrl = mData.getLocalUrl();
            File file = new File(localUrl);
            bis = new BufferedInputStream(inputStream);
            randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(mData.getOffset());
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = bis.read(buffer)) != -1) {
                if (isCanceled()) {
                    mData.setOffset(0);
                    mDBRepo.delete(mData.getDownloadUrl());
                    mUIHandler.sendCancelMsg(mCallback);
                    return;
                }
                if (isPaused()) {
                    mDBRepo.modify(mData);
                    mUIHandler.sendPauseMsg(mCallback);
                    return;
                }
                randomAccessFile.write(buffer, 0, len);
                mData.setOffset(randomAccessFile.getFilePointer());
                mDBRepo.modify(mData);
            }
            if (isCanceled()) {
                mData.setOffset(0);
                mDBRepo.delete(mData.getDownloadUrl());
                mUIHandler.sendCancelMsg(mCallback);
            } else if (isPaused()){
                mDBRepo.modify(mData);
                mUIHandler.sendPauseMsg(mCallback);
            } else {
                mData.setOffset(originOffset + length);
                mData.setState(DataState.COMPLETE);
                mUIHandler.sendCompleteMsg(mData, mCallback);
            }
            mDispatcher.finished(mProcessTask);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            mData.setState(DataState.INTERUPT);
            mUIHandler.sendFailedMsg(e.getMessage(), mCallback);
        } catch (IOException e) {
            e.printStackTrace();
            mData.setState(DataState.INTERUPT);
            mUIHandler.sendFailedMsg(e.getMessage(), mCallback);
        } finally {
            mDBRepo.modify(mData);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mResponse.close();
        }
    }

    private boolean isCanceled() {
        return mCanceled;
    }

    public void pause() {
        mPaused = true;
    }

    private boolean isPaused() {
        return mPaused;
    }

    public class ProcessTask extends NamedRunnable {

        private final ProcessCall mCall;

        public ProcessTask(ProcessCall call, DownloadData data) {
            super("ProcessData : %s", data.getDownloadUrl());
            mCall = call;
        }

        @Override
        protected void execute() {
            mCall.process();
        }
    }

}
