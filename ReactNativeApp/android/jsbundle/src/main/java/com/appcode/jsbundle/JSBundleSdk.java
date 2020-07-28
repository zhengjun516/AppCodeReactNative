package com.appcode.jsbundle;

import android.app.Application;
import android.content.Intent;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;

import java.util.Map;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class JSBundleSdk {

	private  static Application sApplication;
	private  static boolean sIsDebug;

	private  static JSIntent sJsIntent;
	private  static GetReactPackageCallback sGetReactPackageCallback;

	public static void init(Application application){
		sApplication = application;
	}

	public static void setDebug(boolean isDebug){
		sIsDebug = isDebug;
	}


	public static boolean isDebug(){
		return sIsDebug;
	}

	public static void initAssetsJSBundle(GetReactPackageCallback getReactPackageCallback){
		JSBundleFileBaseManager jsBundleFileBaseManager = Singleton.get(JSBundleFileAssetsManager.class);
		jsBundleFileBaseManager.setGetReactPackageCallback(getReactPackageCallback);
		jsBundleFileBaseManager.init("");
	}

	public static void initSDCardJSBundle(GetReactPackageCallback getReactPackageCallback){
		sGetReactPackageCallback = getReactPackageCallback;
		JSBundleFileBaseManager jsBundleFileBaseManager = Singleton.get(JSBundleFileLocalManager.class);
		jsBundleFileBaseManager.setGetReactPackageCallback(getReactPackageCallback);
		jsBundleFileBaseManager.init("");
		int bundleCount = JSBundleManager.getInstance().getJSBundleMap().size();
		if(bundleCount <= 0){
			initAssetsJSBundle(getReactPackageCallback);
		}
	}

	public static void initAllReactContext(){
		Map<String,JSBundle> standardJSBundleMapBundleMap = JSBundleManager.getInstance().getJSBundleMap();
		for(JSBundle jsBundle:standardJSBundleMapBundleMap.values()){
			initReactContext(jsBundle);
		}

		Map<String,JSBundle> multipleJSBundleMap = JSBundleManager.getInstance().getJSBundleMap();
		for(JSBundle jsBundle:multipleJSBundleMap.values()){
			initReactContext(jsBundle);
		}
	}

	public static void initReactContext(JSBundle jsBundle) {
		if (jsBundle.isIsPreload()) {
			ReactNativeHost appCodeReactNativeHost = jsBundle.getReactNativeHost();
			if (appCodeReactNativeHost != null) {
				ReactInstanceManager manager = appCodeReactNativeHost.getReactInstanceManager();
				if (!manager.hasStartedCreatingInitialContext()) {
					manager.createReactContextInBackground();
				}
			}
		}
	}

	public static Application getApplication() {
		return sApplication;
	}

	public static GetReactPackageCallback getGetReactPackageCallback() {
		return sGetReactPackageCallback;
	}

	public static void addJSBundle(JSBundle jsBundle){
		if(JSBundleManager.getInstance().hasJSBundle(jsBundle)){
			throw new RuntimeException("组件："+jsBundle.getBundleDir()+" 已经存在,不能重复添加");
		}
		JSBundleManager.getInstance().addJSBundle(jsBundle);
	}

	public static JSBundle getJSBundler(JSIntent jsIntent){
		return JSBundleManager.getInstance().getJSBundle(jsIntent.getPackageName());
	}

	public static JSBundle removeJSBundler(String mainComponentName){
		return JSBundleManager.getInstance().deleteJSBundle(mainComponentName);
	}

	public static JSIntent getJsIntent() {
		return sJsIntent;
	}

	public static void startJSBundle(JSIntent jsIntent){
		sJsIntent = jsIntent;
		JSBundle jsBundle = getJSBundler(jsIntent);
		if(jsBundle != null){
			JSBundleManager.getInstance().addJSBundleToStackTop(jsBundle);
			Intent	intent = new Intent(sApplication, JSBundlePreloadActivity.class);
			intent.putExtra(JSIntent.KEY_JS_INTENT,jsIntent);
			intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
			sApplication.startActivity(intent);
		}
	}

}
