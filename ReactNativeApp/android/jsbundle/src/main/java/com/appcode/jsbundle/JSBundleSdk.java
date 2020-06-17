package com.appcode.jsbundle;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import com.appcode.react.AppCodeReactNativeHost;
import com.facebook.react.MultipleJSBundleActivity;
import com.facebook.react.MultipleReactNativeHost;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;

import java.util.HashMap;
import java.util.Map;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class JSBundleSdk {

	private  static Application sApplication;

	private static Map<String,JSBundle> jsBundleMap = new HashMap<>();

	private static String mMainComponentName;

	private static Map<String,ReactNativeHost> appCodeReactNativeHostMap = new HashMap<>();

	public static void init(Application application){
		sApplication = application;
	}

	public static void newAppCodeReactNativeHost(JSBundle jsBundle){
		MultipleReactNativeHost appCodeReactNativeHost = new MultipleReactNativeHost(jsBundle,sApplication);
		appCodeReactNativeHostMap.put(jsBundle.getMainComponentName(),appCodeReactNativeHost);
	}

	public static void initReactContext(String mainComponentName){
		ReactNativeHost appCodeReactNativeHost = appCodeReactNativeHostMap.get(mainComponentName);
		if(appCodeReactNativeHost != null){
		 	ReactInstanceManager manager = appCodeReactNativeHost.getReactInstanceManager();
		 	if(!manager.hasStartedCreatingInitialContext()){
				manager.createReactContextInBackground();
			}
		}
	}

	public static ReactNativeHost getAppCodeReactNativeHost(String mainComponentName){
		return appCodeReactNativeHostMap.get(mainComponentName);
	}

	public static Application getApplication() {
		return sApplication;
	}

	public static void addJSBundle(JSBundle jsBundle){
		jsBundleMap.put(jsBundle.getMainComponentName(),jsBundle);
	}

	public static JSBundle getJSBundler(String mainComponentName){
		return jsBundleMap.get(mainComponentName);
	}

	public static JSBundle removeJSBundler(String mainComponentName){
		return jsBundleMap.remove(mainComponentName);
	}

	public static JSBundle getCurrentBundle(){
		if(!TextUtils.isEmpty(mMainComponentName)){
			return getJSBundler(mMainComponentName);
		}
		return null;
	}


	public static void startJSBundle(JSBundle jsBundle){
		if(jsBundle == null){
			throw new RuntimeException("jsBundle is null");
		}
		addJSBundle(jsBundle);
		startJSBundle(jsBundle.getMainComponentName());
	}

	public static void startJSBundle(String mainComponentName){
		mMainComponentName = mainComponentName;
		JSBundle jsBundle = getJSBundler(mainComponentName);
		if(jsBundle != null){
			Intent intent = new Intent(sApplication, MultipleJSBundleActivity.class);
			intent.putExtra(JSBundle.MAIN_COMPONENT_NAME,jsBundle.getMainComponentName());
			intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
			sApplication.startActivity(intent);
		}
	}


}
