package com.appcode.jsbundle;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class JSBUtil {

	public static String getAppName(){
		return JSBundleSdk.getApplication().getResources().getString(getPackageInfo().applicationInfo.labelRes);
	}

	public static PackageInfo getPackageInfo(){
		try {
			PackageInfo packageInfo =JSBundleSdk.getApplication().getPackageManager().getPackageInfo(JSBundleSdk.getApplication().getPackageName(), 0);
			return packageInfo;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
