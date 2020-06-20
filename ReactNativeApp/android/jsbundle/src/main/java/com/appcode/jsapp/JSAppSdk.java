package com.appcode.jsapp;

import android.app.Application;
import android.content.Intent;

import com.appcode.react.AppCodeReactActivity;
import com.appcode.react.AppCodeReactNativeHost;
import com.facebook.react.JSAppPreloadActivity;
import com.facebook.react.JSAppReactNativeHost;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;

import java.util.Map;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class JSAppSdk {

	public static String TAG = JSAppSdk.class.getSimpleName();

	private  static Application sApplication;

	public static void init(Application application){
		JSAppLog.d(TAG,"init()");
		sApplication = application;
	}

	public static void initAllReactContext(){
		JSAppLog.d(TAG,"initAllReactContext()");

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
		JSAppLog.d(TAG,"initReactContext()");
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

	public static void addJSApp(JSApp jsApp){
		JSAppLog.d(TAG,"addJSApp():jsApp="+jsApp.toString());
		if(jsApp.isMultipleJSBundle()){
			if(JSAppManager.getInstance().hasMultipleJSBundle(jsApp)){
				throw new RuntimeException("复合组件："+jsApp.getDefaultMainComponentName()+"已经存在,不能重复添加");
			}
		}else{
			if(JSAppManager.getInstance().hasStandardJSBundle(jsApp)){
				throw new RuntimeException("独立组件："+jsApp.getDefaultMainComponentName()+" 已经存在,不能重复添加");
			}
		}

		ReactNativeHost reactNativeHost;
		if(jsApp.isMultipleJSBundle()){
			reactNativeHost = new JSAppReactNativeHost(jsApp,new ApplicationProxy(sApplication));
		}else{
			reactNativeHost = new AppCodeReactNativeHost(jsApp,new ApplicationProxy(sApplication));
		}
		jsApp.setReactNativeHost(reactNativeHost);
		JSAppManager.getInstance().addJSBundle(jsApp);
	}

	public static JSApp getJSApp(String mainComponentName, boolean isMultiple){
		if(isMultiple){
			return JSAppManager.getInstance().getJSBundleFromMultiple(mainComponentName);
		}else{
			return JSAppManager.getInstance().getJSBundleFromStandard(mainComponentName);
		}
	}

	public static JSApp removeJSApp(String mainComponentName){
		return JSAppManager.getInstance().deleteJSBundleFromMultiple(mainComponentName);
	}


	public static void startJSApp(JSApp jsBundle){
		if(jsBundle == null){
			throw new RuntimeException("jsBundle is null");
		}
		JSAppLog.d(TAG,"startJSApp():jsBundle="+jsBundle.toString());

		if(jsBundle.isMultipleJSBundle()){
			if(!JSAppManager.getInstance().hasMultipleJSBundle(jsBundle)){
				addJSApp(jsBundle);
			}
		}else{
			if(!JSAppManager.getInstance().hasStandardJSBundle(jsBundle)){
				addJSApp(jsBundle);
			}
		}
		startJSApp(jsBundle.getDefaultMainComponentName(),jsBundle.isMultipleJSBundle());
	}

	public static void startJSApp(String mainComponentName, boolean isMultiple){
		JSApp jsBundle = getJSApp(mainComponentName,isMultiple);
		if(jsBundle != null){
			JSAppManager.getInstance().addJSBundleToStackTop(jsBundle);
			Intent intent;
			if(jsBundle.isMultipleJSBundle()){
				intent = new Intent(sApplication, JSAppPreloadActivity.class);
			}else{
				intent = new Intent(sApplication, AppCodeReactActivity.class);
			}
			intent.putExtra(JSApp.MAIN_COMPONENT_NAME,jsBundle.getDefaultMainComponentName());
			intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
			sApplication.startActivity(intent);
		}
	}

}
