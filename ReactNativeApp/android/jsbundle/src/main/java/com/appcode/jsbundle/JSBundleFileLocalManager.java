package com.appcode.jsbundle;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class JSBundleFileLocalManager extends JSBundleFileBaseManager{
	public static final String TAG = JSBundleFileLocalManager.class.getSimpleName();

	private Context mContext;

	public JSBundleFileLocalManager(){
		super(JSBundleConstant.BUNDLES_PATH_SDCARD);
		mContext = JSBundleSdk.getApplication();
	}

	@Override
	public void init(String bundlesDir) {
		if(JSBundleSdk.isEnableCopyFromAssets()){
			copyAssetsBundlesToSdcard();
		}
		super.init(bundlesDir);
	}

	public void copyAssetsBundlesToSdcard(){
		JSFileUtil.copyAssets(JSBundleConstant.DIR_BUNDLES,JSBundleConstant.BUNDLES_PATH_SDCARD);
	}

	@Override
	public String[] getChildDirs(String parentDir,String bundlesDir) {
		File file = new File(parentDir==null?bundlesDir:parentDir+File.separator+bundlesDir);
		if(!file.isDirectory()){
			return null;
		}
		return file.list();
	}

	@Override
	public InputStream getInputStream(String jsBundleFile) throws IOException {
		return new FileInputStream(jsBundleFile);
	}
}
