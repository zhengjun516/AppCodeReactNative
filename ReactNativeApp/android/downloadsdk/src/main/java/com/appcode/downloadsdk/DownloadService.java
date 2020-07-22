package com.appcode.downloadsdk;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class DownloadService extends IntentService {
	public DownloadService(String name) {
		super(name);
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {

	}


}
