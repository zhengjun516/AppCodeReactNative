package com.appcode.jsbundle;

import android.os.Environment;

import static android.content.Context.MODE_PRIVATE;

public class JSBundleConstant  {

	public static final String DIR_ASSETS = "assets://";
	public static final String DIR_BUNDLES = "bundles";
	public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	public static final String BUNDLES_PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	public static final String BUNDLES_PATH_DATA = JSBundleSdk.getApplication().getDir(DIR_BUNDLES,MODE_PRIVATE).getAbsolutePath();
}
