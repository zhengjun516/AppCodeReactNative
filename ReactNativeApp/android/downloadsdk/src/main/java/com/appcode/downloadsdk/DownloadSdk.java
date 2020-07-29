package com.appcode.downloadsdk;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class DownloadSdk {
	private static Application app;

	public static void init(Application application){
		app = application;
	}

	public static void setDownloadConfig(DownloadConfig downloadConfig){
		if(downloadConfig == null){
			DownloadManager.init(app);
		}else{
			DownloadManager.init(app,downloadConfig);
		}
	}

	public static Context getAppContext(){
		return app;
	}

	public static DownloadManager getDownloadManager(){
		return DownloadManager.getInstance();
	}

	public static String getAppName(){
		return app.getResources().getString(getPackageInfo().applicationInfo.labelRes);
	}

	public static PackageInfo getPackageInfo(){
		try {
			PackageInfo packageInfo =app.getPackageManager().getPackageInfo(app.getPackageName(), 0);
			return packageInfo;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
