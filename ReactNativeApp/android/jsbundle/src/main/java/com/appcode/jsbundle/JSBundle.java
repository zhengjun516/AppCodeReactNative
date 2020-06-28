package com.appcode.jsbundle;

import com.facebook.react.ReactNativeHost;
import java.io.Serializable;

public class JSBundle implements Serializable {

	public static final String MAIN_COMPONENT_NAME = "mainComponentName";

	public static final int JS_BUNDLE_TYPE_SIMPLE = 1;
	public static final int JS_BUNDLE_TYPE_MULTIPLE = 2;

	/**
	* 入口组件名
	*/

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
			mJSBundleType = JS_BUNDLE_TYPE_SIMPLE;
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

	public boolean isSimpleJSBundle(){
		return mJSBundleType == JS_BUNDLE_TYPE_SIMPLE;
	}



	public String getPackageName() {
		if(mJSBundleInfo.getJsBundleLocationType() == JSBundleInfo.BUNDLE_LOCATION_ASSETS){
			return mJSBundleInfo.getJsBundleFile().replace(JSBundleConstant.DIR_BUNDLES+"/","");
		}else if(mJSBundleInfo.getJsBundleLocationType() == JSBundleInfo.BUNDLE_LOCATION_SDCARD){
			String jsBundleFilePath = mJSBundleInfo.getJsBundleFile().substring(mJSBundleInfo.getJsBundleFile().indexOf(JSBundleConstant.DIR_BUNDLES));
			return jsBundleFilePath.replace(JSBundleConstant.DIR_BUNDLES+"/","");
		}
		return null;
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
