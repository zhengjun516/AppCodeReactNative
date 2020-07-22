package com.appcode.jsbundle;

import android.content.res.AssetManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class JSBundleFileAssetsManager extends JSBundleFileBaseManager {
	public static final String TAG = JSBundleFileAssetsManager.class.getSimpleName();

	private AssetManager mAssetManager;


	public JSBundleFileAssetsManager(){
		super(JSBundleConstant.DIR_BUNDLES);
		mAssetManager = JSBundleSdk.getApplication().getAssets();
	}

	@Override
	public String[] getChildDirs(String parentDir,String bundlesDir) throws IOException {
		return mAssetManager.list(parentDir==null?bundlesDir:parentDir+File.separator+bundlesDir);
	}

	@Override
	public InputStream getInputStream(String jsBundleFile)throws IOException{
		return mAssetManager.open(jsBundleFile);
	}

}
