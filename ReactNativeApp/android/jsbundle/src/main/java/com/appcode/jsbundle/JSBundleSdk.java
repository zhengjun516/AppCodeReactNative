package com.appcode.jsbundle;

import android.app.Application;
import android.content.Intent;

import com.appcode.react.AppCodeReactActivity;
import com.appcode.react.AppCodeReactNativeHost;
import com.facebook.react.JSBundlePreloadActivity;
import com.facebook.react.JSBundleReactNativeHost;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;

import java.util.Map;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class JSBundleSdk {

	private  static Application sApplication;

	public static void init(Application application){
		sApplication = application;
	}

	public static void initAllReactContext(){
		Map<String,JSBundle> standardJSBundleMapBundleMap = JSBundleManager.getInstance().getStandardJSBundleMap();
		for(JSBundle jsBundle:standardJSBundleMapBundleMap.values()){
			initReactContext(jsBundle);
		}

		Map<String,JSBundle> multipleJSBundleMap = JSBundleManager.getInstance().getMultipleJSBundleMap();
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

	public static void addJSBundle(JSBundle jsBundle){
		if(jsBundle.isMultipleJSBundle()){
			if(JSBundleManager.getInstance().hasMultipleJSBundle(jsBundle)){
				throw new RuntimeException("复合组件："+jsBundle.getDefaultMainComponentName()+"已经存在,不能重复添加");
			}
		}else{
			if(JSBundleManager.getInstance().hasStandardJSBundle(jsBundle)){
				throw new RuntimeException("独立组件："+jsBundle.getDefaultMainComponentName()+" 已经存在,不能重复添加");
			}
		}

		ReactNativeHost reactNativeHost;
		if(jsBundle.isMultipleJSBundle()){
			reactNativeHost = new JSBundleReactNativeHost(jsBundle,sApplication);
		}else{
			reactNativeHost = new AppCodeReactNativeHost(jsBundle,sApplication);
		}
		jsBundle.setReactNativeHost(reactNativeHost);
		JSBundleManager.getInstance().addJSBundle(jsBundle);
	}

	public static JSBundle getJSBundler(String mainComponentName,boolean isMultiple){
		if(isMultiple){
			return JSBundleManager.getInstance().getJSBundleFromMultiple(mainComponentName);
		}else{
			return JSBundleManager.getInstance().getJSBundleFromStandard(mainComponentName);
		}
	}

	public static JSBundle removeJSBundler(String mainComponentName){
		return JSBundleManager.getInstance().deleteJSBundleFromMultiple(mainComponentName);
	}


	public static void startJSBundle(JSBundle jsBundle){
		if(jsBundle == null){
			throw new RuntimeException("jsBundle is null");
		}
		if(jsBundle.isMultipleJSBundle()){
			if(!JSBundleManager.getInstance().hasMultipleJSBundle(jsBundle)){
				addJSBundle(jsBundle);
			}
		}else{
			if(!JSBundleManager.getInstance().hasStandardJSBundle(jsBundle)){
				addJSBundle(jsBundle);
			}
		}
		startJSBundle(jsBundle.getDefaultMainComponentName(),jsBundle.isMultipleJSBundle());
	}

	public static void startJSBundle(String mainComponentName,boolean isMultiple){
		JSBundle jsBundle = getJSBundler(mainComponentName,isMultiple);
		if(jsBundle != null){
			JSBundleManager.getInstance().addJSBundleToStackTop(jsBundle);
			Intent intent;
			if(jsBundle.isMultipleJSBundle()){
				intent = new Intent(sApplication, JSBundlePreloadActivity.class);
			}else{
				intent = new Intent(sApplication, AppCodeReactActivity.class);
			}
			intent.putExtra(JSBundle.MAIN_COMPONENT_NAME,jsBundle.getDefaultMainComponentName());
			intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
			sApplication.startActivity(intent);
		}
	}

}
