package com.appcode.jsbundle;

import android.text.TextUtils;

import com.facebook.react.ReactNativeHost;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class JSApp implements Serializable {

	public static final String MAIN_COMPONENT_NAME = "mainComponentName";

	public static final int JS_BUNDLE_TYPE_STANDARD = 1;
	public static final int JS_BUNDLE_TYPE_MULTIPLE = 2;

	private File mAppFilePath;

	/**小程序名*/
	private String mAppName;
	private String mPackageName;
	/**
	* 入口组件名
	*/
	private List<String> mMainComponentNames;

	private String mDefaultMainComponentName;
	/**
	 *bundle文件
	 */
	private String mJSBundleFile;
	/**
	 * bundle在asset中的默认文件
	 */
	private String mJSBundleAssetName;

	/**
	 * sd卡中的公共基础库
	 */
	private String mCommonJSBundleFile;

	/**
	 * 默认公共基础库
	 */
	private String mCommonJSBundleAssetName;

	private GetReactPackageCallback mGetReactPackageCallback;

	private ReactNativeHost mReactNativeHost;

	private int mJSBundleType;

	private boolean mIsPreload;

	public JSApp(File appFilePath){
		mAppFilePath = appFilePath;
	}

	public JSApp(String appName, String packageName, String mainComponentName, String jSBundleFile, String jSBundleAssetName, GetReactPackageCallback getReactPackageCallback) {
		this(appName,packageName,mainComponentName,false,jSBundleFile,jSBundleAssetName,getReactPackageCallback);
	}

	public JSApp(String appName, String packageName, List<String> mainComponentNames, String defaultMainComponentName, String jSBundleFile, String jSBundleAssetName, GetReactPackageCallback getReactPackageCallback) {
		this(appName,packageName,mainComponentNames,defaultMainComponentName,false,jSBundleFile,jSBundleAssetName,null,null,getReactPackageCallback);
	}

	public JSApp(String appName, String packageName, String mainComponentName, boolean isPreload, String jSBundleFile, String jSBundleAssetName, GetReactPackageCallback getReactPackageCallback) {
		this(appName,packageName,Arrays.asList(mainComponentName),mainComponentName,isPreload,jSBundleFile,jSBundleAssetName,getReactPackageCallback);
	}

	public JSApp(String appName, String packageName, List<String> mainComponentNames, String defaultMainComponentName, boolean isPreload, String jSBundleFile, String jSBundleAssetName, GetReactPackageCallback getReactPackageCallback) {
		this(appName,packageName,mainComponentNames,defaultMainComponentName,isPreload,jSBundleFile,jSBundleAssetName,null,null,getReactPackageCallback);
	}

	public JSApp(String appName, String packageName, String mainComponentName, String jSBundleFile, String jSBundleAssetName, String commonJSBundleFile, String commonJSBundleAssetName, GetReactPackageCallback getReactPackageCallback) {
          this(appName,packageName,Arrays.asList(mainComponentName),mainComponentName,false,jSBundleFile,jSBundleAssetName,commonJSBundleFile,commonJSBundleAssetName,getReactPackageCallback);
	}

	public JSApp(String appName, String packageName, String mainComponentName, boolean isPreload, String jSBundleFile, String jSBundleAssetName, String commonJSBundleFile, String commonJSBundleAssetName, GetReactPackageCallback getReactPackageCallback) {
		this(appName,packageName,Arrays.asList(mainComponentName),mainComponentName,isPreload,jSBundleFile,jSBundleAssetName,commonJSBundleFile,commonJSBundleAssetName,getReactPackageCallback);
	}

	public JSApp(String appName, String packageName, List<String> mainComponentNames, String defaultMainComponentName, boolean isPreload, String jSBundleFile, String jSBundleAssetName, String commonJSBundleFile, String commonJSBundleAssetName, GetReactPackageCallback getReactPackageCallback) {
		this.mAppName = appName;
		this.mPackageName = packageName;
		this.mMainComponentNames = mainComponentNames;
		this.mDefaultMainComponentName = defaultMainComponentName;
		this.mIsPreload = isPreload;
		this.mJSBundleFile = TextUtils.isEmpty(jSBundleFile)?null:jSBundleFile;
		this.mJSBundleAssetName = TextUtils.isEmpty(jSBundleAssetName)?null:jSBundleAssetName;
		this.mCommonJSBundleFile = TextUtils.isEmpty(commonJSBundleFile)?null:commonJSBundleFile;
		this.mCommonJSBundleAssetName = TextUtils.isEmpty(commonJSBundleAssetName)?null:commonJSBundleAssetName;
		this.mGetReactPackageCallback = getReactPackageCallback;
		if(TextUtils.isEmpty(commonJSBundleFile) && TextUtils.isEmpty(commonJSBundleAssetName)){
			mJSBundleType = JS_BUNDLE_TYPE_STANDARD;
		}else{
			mJSBundleType = JS_BUNDLE_TYPE_MULTIPLE;
		}
	}

	public ReactNativeHost getReactNativeHost() {
		return mReactNativeHost;
	}

	public void setReactNativeHost(ReactNativeHost mReactNativeHost) {
		this.mReactNativeHost = mReactNativeHost;
	}

	public boolean isIsPreload() {
		return mIsPreload;
	}

	public void setIsPreload(boolean mIsPreload) {
		this.mIsPreload = mIsPreload;
	}

	public boolean isMultipleJSBundle(){
		return mJSBundleType == JS_BUNDLE_TYPE_MULTIPLE;
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

	public String getDefaultMainComponentName() {
		return mDefaultMainComponentName;
	}

	public void setDefaultMainComponentName(String defaultMainComponentName) {
		this.mDefaultMainComponentName = defaultMainComponentName;
	}

	public String getJSBundleFile() {
		return mJSBundleFile;
	}

	public void setJSBundleFile(String mJSBundleFile) {
		this.mJSBundleFile = mJSBundleFile;
	}

	public String getBundleAssetName() {
		return mJSBundleAssetName;
	}

	public void setBundleAssetName(String mBundleAssetName) {
		this.mJSBundleAssetName = mBundleAssetName;
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
