package com.appcode.jsbundle;

import android.app.Application;
import android.content.Intent;

import com.appcode.react.AppCodeReactActivity;
import com.facebook.react.JSBundlePreloadActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;

import java.util.Map;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class JSBundleSdk {

	private  static Application sApplication;

	public static void init(Application application){
		sApplication = application;
	}

	public static void initAssetsJSBundle(GetReactPackageCallback getReactPackageCallback){
		JSBundleFileAssetsManager jsBundleAssetsManager = Singleton.get(JSBundleFileAssetsManager.class);
		jsBundleAssetsManager.setGetReactPackageCallback(getReactPackageCallback);
		jsBundleAssetsManager.init("");
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

	public static void addJSBundle(JSBundle jsBundle){
		if(JSBundleManager.getInstance().hasJSBundle(jsBundle)){
			throw new RuntimeException("组件："+jsBundle.getPackageName()+" 已经存在,不能重复添加");
		}
		JSBundleManager.getInstance().addJSBundle(jsBundle);
	}

	public static JSBundle getJSBundler(JSIntent jsIntent){
		return JSBundleManager.getInstance().getJSBundle(jsIntent.getPackageName());
	}

	public static JSBundle removeJSBundler(String mainComponentName){
		return JSBundleManager.getInstance().deleteJSBundle(mainComponentName);
	}



	public static void startJSBundle(JSIntent jsIntent){
		JSBundle jsBundle = getJSBundler(jsIntent);
		if(jsBundle != null){
			JSBundleManager.getInstance().addJSBundleToStackTop(jsBundle);
			Intent intent;
			if(jsBundle.isSimpleJSBundle()){
				intent = new Intent(sApplication, AppCodeReactActivity.class);
			}else{
				intent = new Intent(sApplication, JSBundlePreloadActivity.class);
			}
			//intent.putExtra(JSBundle.MAIN_COMPONENT_NAME,jsBundle.getDefaultMainComponentName());
			intent.putExtra(JSIntent.KEY_JS_INTENT,jsIntent);
			intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
			sApplication.startActivity(intent);
		}
	}

}
