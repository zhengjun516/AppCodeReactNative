package com.appcode.jsbundle;

import android.text.TextUtils;

import java.io.Serializable;

public class JSBundle implements Serializable {

	public static final String MAIN_COMPONENT_NAME = "mainComponentName";

	/**
	* 入口组件名
	*/
	private String mMainComponentName;
	/**
	 *bundle文件
	 */
	private String mJSBundleFile;
	/**
	 * bundle在asset中的默认文件
	 */
	private String mBundleAssetName;

	/**
	 * sd卡中的公共基础库
	 */
	private String mCommonJSBundleFile;

	/**
	 * 默认公共基础库
	 */
	private String mCommonJSBundleAssetName;

	private GetReactPackageCallback mGetReactPackageCallback;

	public JSBundle(String mainComponentName, String jSBundleFile, String bundleAssetName,GetReactPackageCallback getReactPackageCallback) {
		this.mMainComponentName = mainComponentName;
		this.mJSBundleFile = jSBundleFile;
		this.mBundleAssetName = bundleAssetName;
		this.mCommonJSBundleFile = "";
		this.mGetReactPackageCallback = getReactPackageCallback;
	}

	public JSBundle(String mainComponentName, String jSBundleFile, String bundleAssetName,String commonJSBundleFile,String commonJSBundleAssetName,GetReactPackageCallback getReactPackageCallback) {
		this.mMainComponentName = mainComponentName;
		this.mJSBundleFile = jSBundleFile;
		this.mBundleAssetName = bundleAssetName;
		this.mCommonJSBundleFile = commonJSBundleFile;
		this.mCommonJSBundleAssetName = commonJSBundleAssetName;
		this.mGetReactPackageCallback = getReactPackageCallback;
	}


	public boolean isSupportCommonJSBundle(){
		if(TextUtils.isEmpty(mCommonJSBundleFile)){
			return false;
		}
		return true;
	}

	public boolean isSupportCoomonJSBundleAsset(){
		if(TextUtils.isEmpty(mCommonJSBundleAssetName)){
			return false;
		}
		return true;
	}

	public String getMainComponentName() {
		return mMainComponentName;
	}

	public void setMainComponentName(String mMainComponentName) {
		this.mMainComponentName = mMainComponentName;
	}

	public String getJSBundleFile() {
		return mJSBundleFile;
	}

	public void setJSBundleFile(String mJSBundleFile) {
		this.mJSBundleFile = mJSBundleFile;
	}

	public String getBundleAssetName() {
		return mBundleAssetName;
	}

	public void setBundleAssetName(String mBundleAssetName) {
		this.mBundleAssetName = mBundleAssetName;
	}

	public String getCommonJSBundleFile() {
		return mCommonJSBundleFile;
	}

	public void setCommonJSBundleFile(String commonJSBundleFile) {
		this.mCommonJSBundleFile = commonJSBundleFile;
	}

	public String getCommonJSBundleAssetName() {
		return mCommonJSBundleAssetName;
	}

	public void setCommonJSBundleAssetName(String commonJSBundleAssetName) {
		this.mCommonJSBundleAssetName = commonJSBundleAssetName;
	}

	public GetReactPackageCallback getGetReactPackageCallback() {
		return mGetReactPackageCallback;
	}

	public void setGetReactPackageCallback(GetReactPackageCallback mGetReactPackageCallback) {
		this.mGetReactPackageCallback = mGetReactPackageCallback;
	}
}
