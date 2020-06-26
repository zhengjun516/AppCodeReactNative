package com.appcode.jsbundle;

import android.text.TextUtils;

import com.facebook.react.ReactNativeHost;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class JSBundle implements Serializable {

	public static final String MAIN_COMPONENT_NAME = "mainComponentName";

	public static final int JS_BUNDLE_TYPE_STANDARD = 1;
	public static final int JS_BUNDLE_TYPE_MULTIPLE = 2;

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

	private JSBundleInfo mJSBundleInfo;

	private JSBundleInfo mBaseJSBundleInfo;

	private GetReactPackageCallback mGetReactPackageCallback;

	private ReactNativeHost mReactNativeHost;

	private int mJSBundleType;

	private boolean mIsPreload;


	public JSBundle(JSBundleInfo jsBundleInfo) {
		this(jsBundleInfo,false);
	}

	public JSBundle(JSBundleInfo jsBundleInfo,boolean isPreload) {
		this(jsBundleInfo,null,false);
	}

	public JSBundle(JSBundleInfo jsBundleInfo, JSBundleInfo mBaseJSBundleInfo) {
		this(jsBundleInfo,mBaseJSBundleInfo,false);
	}

	public JSBundle(JSBundleInfo mJSBundleInfo, JSBundleInfo mBaseJSBundleInfo,boolean isPreload) {
		this.mJSBundleInfo = mJSBundleInfo;
		this.mBaseJSBundleInfo = mBaseJSBundleInfo;
		this.mIsPreload = isPreload;

		if(mBaseJSBundleInfo == null){
			mJSBundleType = JS_BUNDLE_TYPE_STANDARD;
		}else{
			mJSBundleType = JS_BUNDLE_TYPE_MULTIPLE;
		}
	}

	public JSBundle(String mainComponentName, String jSBundleFile, String jSBundleAssetName) {
		this(mainComponentName,false,jSBundleFile,jSBundleAssetName);
	}

	public JSBundle(List<String> mainComponentNames,String defaultMainComponentName, String jSBundleFile, String jSBundleAssetName) {
		this(mainComponentNames,defaultMainComponentName,false,jSBundleFile,jSBundleAssetName,null,null);
	}

	public JSBundle(String mainComponentName,boolean isPreload, String jSBundleFile, String jSBundleAssetName) {
		this(Arrays.asList(mainComponentName),mainComponentName,isPreload,jSBundleFile,jSBundleAssetName);
	}

	public JSBundle(List<String> mainComponentNames,String defaultMainComponentName,boolean isPreload, String jSBundleFile, String jSBundleAssetName) {
		this(mainComponentNames,defaultMainComponentName,isPreload,jSBundleFile,jSBundleAssetName,null,null);
	}

	public JSBundle(String mainComponentName, String jSBundleFile, String jSBundleAssetName,String commonJSBundleFile,String commonJSBundleAssetName) {
          this(Arrays.asList(mainComponentName),mainComponentName,false,jSBundleFile,jSBundleAssetName,commonJSBundleFile,commonJSBundleAssetName);
	}

	public JSBundle(String mainComponentName, boolean isPreload,String jSBundleFile, String jSBundleAssetName,String commonJSBundleFile,String commonJSBundleAssetName) {
		this(Arrays.asList(mainComponentName),mainComponentName,isPreload,jSBundleFile,jSBundleAssetName,commonJSBundleFile,commonJSBundleAssetName);
	}

	public JSBundle(List<String> mainComponentNames, String defaultMainComponentName,boolean isPreload,String jSBundleFile, String jSBundleAssetName,String commonJSBundleFile,String commonJSBundleAssetName) {
		this.mMainComponentNames = mainComponentNames;
		this.mDefaultMainComponentName = defaultMainComponentName;
		this.mIsPreload = isPreload;
		this.mJSBundleFile = TextUtils.isEmpty(jSBundleFile)?null:jSBundleFile;
		this.mJSBundleAssetName = TextUtils.isEmpty(jSBundleAssetName)?null:jSBundleAssetName;
		this.mCommonJSBundleFile = TextUtils.isEmpty(commonJSBundleFile)?null:commonJSBundleFile;
		this.mCommonJSBundleAssetName = TextUtils.isEmpty(commonJSBundleAssetName)?null:commonJSBundleAssetName;
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
		return mJSBundleInfo.getJsBundleFile();
	}

	public void setDefaultMainComponentName(String defaultMainComponentName) {
		this.mDefaultMainComponentName = defaultMainComponentName;
	}

	public String getJSBundleFile() {
		if(mJSBundleInfo != null){
			if(mJSBundleInfo.getJsBundleLocationType() == JSBundleInfo.BUNDLE_LOCATION_SDCARD){
				return mJSBundleInfo.getJsBundleFile();
			}
		}
		return null;
	}


	public String getBundleAssetName() {
		if(mJSBundleInfo != null){
			if(mJSBundleInfo.getJsBundleLocationType() == JSBundleInfo.BUNDLE_LOCATION_ASSETS){
				return mJSBundleInfo.getJsBundleFile();
			}
		}
		return null;
	}

	public void setBundleAssetName(String mBundleAssetName) {
		this.mJSBundleAssetName = mBundleAssetName;
	}

	public String getCommonJSBundleFile() {
		if(mBaseJSBundleInfo != null){
			if(mBaseJSBundleInfo.getJsBundleLocationType() == JSBundleInfo.BUNDLE_LOCATION_SDCARD){
				return mBaseJSBundleInfo.getJsBundleFile();
			}
		}
		return null;
	}


	public String getCommonJSBundleAssetName() {
		if(mBaseJSBundleInfo != null){
			if(mBaseJSBundleInfo.getJsBundleLocationType() == JSBundleInfo.BUNDLE_LOCATION_ASSETS){
				return mBaseJSBundleInfo.getJsBundleFile();
			}
		}
		return null;
	}

	public GetReactPackageCallback getGetReactPackageCallback() {
		return mGetReactPackageCallback;
	}

	public void setGetReactPackageCallback(GetReactPackageCallback mGetReactPackageCallback) {
		this.mGetReactPackageCallback = mGetReactPackageCallback;
	}
}
