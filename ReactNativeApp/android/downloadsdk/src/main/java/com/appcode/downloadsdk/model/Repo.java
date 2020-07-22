package com.appcode.downloadsdk.model;

import com.appcode.downloadsdk.model.bean.DownloadData;

import java.util.List;

public interface Repo {
    boolean insert(DownloadData data);
    boolean delete(String url);
    boolean modify(DownloadData data);
    DownloadData query(String url);
    List<DownloadData> queryAll();
}
