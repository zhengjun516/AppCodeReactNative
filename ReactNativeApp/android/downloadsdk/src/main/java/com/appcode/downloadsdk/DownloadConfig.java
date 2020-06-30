package com.appcode.downloadsdk;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * 下载器的配置类
 */
public class DownloadConfig {

    public static final int MAX_THREAD_NUMBER = 4;
    static final int DEFAULT_THREAD_NUMBER = 1;

    int mDownloadThreadNumber = DEFAULT_THREAD_NUMBER;
    String mDownloadRootPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), DownloadSdk.getAppName() + "/download/").getAbsolutePath();

    private DownloadConfig(Builder builder) {
        if (builder.downloadThreadNumber > MAX_THREAD_NUMBER) {
            this.mDownloadThreadNumber = MAX_THREAD_NUMBER;
        } else {
            this.mDownloadThreadNumber = builder.downloadThreadNumber;
        }

        if (!TextUtils.isEmpty(builder.downloadRootPath)) {
            this.mDownloadRootPath = builder.downloadRootPath;
        }
    }

    public static final class Builder {

        int downloadThreadNumber;
        String downloadRootPath;

        public Builder() {

        }

        public Builder setMaxDowanloadThread(int threadNumber) {
            downloadThreadNumber = threadNumber;
            return this;
        }

        public Builder setDownloadRootPath(String rootPath) {
            downloadRootPath = rootPath;
            return this;
        }

        public DownloadConfig build() {
            return new DownloadConfig(this);
        }
    }


}
