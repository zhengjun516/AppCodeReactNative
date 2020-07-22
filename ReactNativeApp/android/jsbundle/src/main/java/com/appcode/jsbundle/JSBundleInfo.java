package com.appcode.jsbundle;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class JSBundleInfo implements Serializable {

	public static final String MODULE_NAME = "moduleName";
	public static final String VERSION = "version";
	public static final String BUNDLE_DIR = "bundleDir";
	public static final String BUNDLE_FILE = "bundleFile";
	public static final String MAIN_COMPONENT = "mainComponent";
	public static final String IS_BASE_BUNDLE = "isBase";
	public static final String IS_PRELOAD= "isPreload";
	public static final String MD5 = "md5";

	public static final int BUNDLE_TYPE_SINGLE = 1;
	public static final int BUNDLE_TYPE_MULTIPLE = 2;

	public static final int BUNDLE_LOCATION_UNKOWN = -1;
	public static final int BUNDLE_LOCATION_ASSETS = 1;
	public static final int BUNDLE_LOCATION_SDCARD = 2;
	public static final int BUNDLE_LOCATION_NETWORD = 3;

	public String mBundleParentPath;
	public int mVersion;
	public String mJsBundleFile;
	public String mBundleDir;
	public boolean isBaseBundle;
	public boolean isPreload;
	public String mMD5;

	public List<String> mainComponentNames;


	public int jsBundleLocationType;
	public JSBundleInfo(String bundleParentPath,String mBundleDir,String mJsBundleFile,String mainComponent,int version,boolean isBaseBundle,boolean isPreload, String mMD5) {
		this.mBundleParentPath= bundleParentPath;
		this.mBundleDir = mBundleDir;
		this.mJsBundleFile = mJsBundleFile;
		this.mainComponentNames = Arrays.asList(mainComponent);
		this.mVersion = version;
		this.isBaseBundle = isBaseBundle;
		this.isPreload = isPreload;
		this.mMD5 = mMD5;

		if(mBundleParentPath.startsWith("/")){
			jsBundleLocationType = BUNDLE_LOCATION_SDCARD;
		}else if(mBundleParentPath.startsWith("http://")|| mBundleParentPath.startsWith("https://")){
			jsBundleLocationType = BUNDLE_LOCATION_NETWORD;
		}else{
			jsBundleLocationType = BUNDLE_LOCATION_ASSETS;
		}
	}

	public JSBundleInfo(String jsBundleFile) {
		this(null,jsBundleFile);
	}

	public JSBundleInfo(List<String> mainComponentNames, String jsBundleFile) {
		this.mainComponentNames = mainComponentNames;
		this.mJsBundleFile = jsBundleFile;

		if(mJsBundleFile.startsWith("/")){
			jsBundleLocationType = BUNDLE_LOCATION_SDCARD;
		}else if(mJsBundleFile.startsWith("http://")|| mJsBundleFile.startsWith("https://")){
			jsBundleLocationType = BUNDLE_LOCATION_NETWORD;
		}else{
			jsBundleLocationType = BUNDLE_LOCATION_ASSETS;
		}

		if(mainComponentNames == null || mainComponentNames.isEmpty()){
			isBaseBundle = true;
		}else{
			isBaseBundle = false;
		}
	}

	public List<String> getMainComponentNames() {
		return mainComponentNames;
	}

	public void setMainComponentNames(List<String> mainComponentNames) {
		this.mainComponentNames = mainComponentNames;
	}

	public String getJsBundleFile() {
		return mBundleParentPath+File.separator+mBundleDir+ File.separator+mJsBundleFile;
	}

	public boolean isBaseBundle() {
		return isBaseBundle;
	}

	public void setBaseBundle(boolean baseBundle) {
		isBaseBundle = baseBundle;
	}

	public int getJsBundleLocationType() {
		return jsBundleLocationType;
	}

	public void setJsBundleLocationType(int jsBundleLocationType) {
		this.jsBundleLocationType = jsBundleLocationType;
	}
}
