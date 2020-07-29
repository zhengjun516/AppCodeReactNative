package com.appcode.downloadsdk.model;

import android.content.Context;
import android.text.TextUtils;

import com.appcode.downloadsdk.DownloadManager;
import com.appcode.downloadsdk.IDownloadCallback;
import com.appcode.downloadsdk.model.bean.DataState;
import com.appcode.downloadsdk.model.bean.DownloadData;
import com.appcode.downloadsdk.database.DownloadDB;
import com.appcode.downloadsdk.utils.MD5Util;

import java.io.File;

import okhttp3.Response;

public class RepoManager {

    private String mRootPath;
    private final DownloadDB mDownloadDB;
    private final ProcessDispatcher mProcessDispatcher;

    public RepoManager(Context context, String rootPath) {
        mDownloadDB = new DownloadDB(context);
        mRootPath = rootPath;
        if (!rootPath.endsWith("/")) {
            mRootPath = rootPath + "/";
        }
        checkDir();
        mProcessDispatcher = new ProcessDispatcher();
    }

    private void checkDir() {
        File file = new File(mRootPath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public DownloadData getData(String url) {
        checkDir();
        DownloadData data = mDownloadDB.query(url);
        if (checkFileIsValid(data)) {
            return data;
        } else {
            if (data != null) {
                mDownloadDB.delete(data.getDownloadUrl());
                deleteFile(data.getLocalUrl());
            }
            data = new DownloadData();
            data.setDownloadUrl(url);
            String fileSuffix = getFileSuffix(url);
            String fileMD5Name = MD5Util.getMD5(url);
            data.setLocalUrl(mRootPath + fileMD5Name + "." + fileSuffix);
            mDownloadDB.insert(data);
            return data;
        }
    }

    public boolean isDownload(String url) {
        checkDir();
        DownloadData data = mDownloadDB.query(url);
        return checkFileIsCompelte(data);
    }

    private void deleteFile(String localUrl) {
        if (TextUtils.isEmpty(localUrl)) {
            return;
        }
        File file = new File(localUrl);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    /**
     * TODO : 没有校验文件大小，文件MD5等信息
     * @param data
     * @return
     */
    private boolean checkFileIsValid(DownloadData data) {
        if (data == null || data.getState() == DataState.INTERUPT) {
            return false;
        }
        File file = new File(data.getLocalUrl());
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    private boolean checkFileIsCompelte(DownloadData data) {
        if (data == null || data.getState() == DataState.INTERUPT || data.getState() == DataState.PART) {
            return false;
        }
        File file = new File(data.getLocalUrl());
        if (!file.exists()) {
            return false;
        }
        return true;
    }



    public ProcessCall enqueue(Response response, DownloadData data, DownloadManager.UIHandler handler, IDownloadCallback callback) {
        checkDir();
        ProcessCall call = new ProcessCall(mProcessDispatcher, mDownloadDB, response, data, handler, callback);
        call.enqueue();
        return call;
    }

    private String getFileSuffix(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        String[] split = url.split("\\.");
        if (split.length > 1) {
            return split[split.length -1];
        }
        return "";
    }
}
