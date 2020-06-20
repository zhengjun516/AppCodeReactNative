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


public class JSAppSdk {

	private  static Application sApplication;

	public static void init(Application application){
		sApplication = application;
	}

	public static void initAllReactContext(){
		Map<String, JSApp> standardJSBundleMapBundleMap = JSAppManager.getInstance().getStandardJSBundleMap();
		for(JSApp jsBundle:standardJSBundleMapBundleMap.values()){
			initReactContext(jsBundle);
		}

		Map<String, JSApp> multipleJSBundleMap = JSAppManager.getInstance().getMultipleJSBundleMap();
		for(JSApp jsBundle:multipleJSBundleMap.values()){
			initReactContext(jsBundle);
		}
	}

	public static void initReactContext(JSApp jsBundle) {
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

	public static void addJSBundle(JSApp jsBundle){
		if(jsBundle.isMultipleJSBundle()){
			if(JSAppManager.getInstance().hasMultipleJSBundle(jsBundle)){
				throw new RuntimeException("复合组件："+jsBundle.getDefaultMainComponentName()+"已经存在,不能重复添加");
			}
		}else{
			if(JSAppManager.getInstance().hasStandardJSBundle(jsBundle)){
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
		JSAppManager.getInstance().addJSBundle(jsBundle);
	}

	public static JSApp getJSBundler(String mainComponentName, boolean isMultiple){
		if(isMultiple){
			return JSAppManager.getInstance().getJSBundleFromMultiple(mainComponentName);
		}else{
			return JSAppManager.getInstance().getJSBundleFromStandard(mainComponentName);
		}
	}

	public static JSApp removeJSBundler(String mainComponentName){
		return JSAppManager.getInstance().deleteJSBundleFromMultiple(mainComponentName);
	}


	public static void startJSBundle(JSApp jsBundle){
		if(jsBundle == null){
			throw new RuntimeException("jsBundle is null");
		}
		if(jsBundle.isMultipleJSBundle()){
			if(!JSAppManager.getInstance().hasMultipleJSBundle(jsBundle)){
				addJSBundle(jsBundle);
			}
		}else{
			if(!JSAppManager.getInstance().hasStandardJSBundle(jsBundle)){
				addJSBundle(jsBundle);
			}
		}
		startJSBundle(jsBundle.getDefaultMainComponentName(),jsBundle.isMultipleJSBundle());
	}

	public static void startJSBundle(String mainComponentName,boolean isMultiple){
		JSApp jsBundle = getJSBundler(mainComponentName,isMultiple);
		if(jsBundle != null){
			JSAppManager.getInstance().addJSBundleToStackTop(jsBundle);
			Intent intent;
			if(jsBundle.isMultipleJSBundle()){
				intent = new Intent(sApplication, JSBundlePreloadActivity.class);
			}else{
				intent = new Intent(sApplication, AppCodeReactActivity.class);
			}
			intent.putExtra(JSApp.MAIN_COMPONENT_NAME,jsBundle.getDefaultMainComponentName());
			intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
			sApplication.startActivity(intent);
		}
	}

}
