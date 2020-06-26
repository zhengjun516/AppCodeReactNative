package com.appcode.jsbundle;

import java.io.Serializable;
import java.util.List;

public class JSBundleInfo implements Serializable {

	public static final int BUNDLE_LOCATION_UNKOWN = -1;
	public static final int BUNDLE_LOCATION_ASSETS = 1;
	public static final int BUNDLE_LOCATION_SDCARD = 2;
	public static final int BUNDLE_LOCATION_NETWORD = 3;

	public List<String> mainComponentNames;
	public String mJsBundleFile;
	public boolean isBaseBundle;
	public int jsBundleLocationType;

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
		return mJsBundleFile;
	}

	public void setJsBundleFile(String mJsBundleFile) {
		this.mJsBundleFile = mJsBundleFile;
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
