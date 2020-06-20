package com.appcode.jsapp;

import android.content.Context;
import android.text.TextUtils;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class JSBridge {

	private ReactNativeHost mReactNativeHost;

	public JSBridge(ReactNativeHost reactNativeHost) {
		mReactNativeHost = reactNativeHost;
	}

	public CatalystInstance getCatalystInstance(){
		ReactInstanceManager manager = mReactNativeHost.getReactInstanceManager();
		if(manager == null){
			return null;
		}
		ReactContext reactContext = manager.getCurrentReactContext();
		if(reactContext == null){
			return null;
		}
		return  reactContext.getCatalystInstance();
	}

	public void loadScriptFile(JSApp jsBundle, boolean loadSynchronously){
		if(!TextUtils.isEmpty(jsBundle.getJSBundleFile())){
			loadScriptFromFile(jsBundle.getJSBundleFile(),jsBundle.getJSBundleFile(),loadSynchronously);
		}else{
			loadScriptFromAsset(JSAppSdk.getApplication(),jsBundle.getBundleAssetName(),loadSynchronously);
		}
	}

	private void loadScriptFromAsset(Context context,String assetName,boolean loadSynchronously){
		if(TextUtils.isEmpty(assetName)){
			throw new RuntimeException("assetName is null");
		}
		String source = assetName;
		if(!assetName.startsWith("assets://")){
			source = "assets://"+assetName;
		}
		if(getCatalystInstance() != null){
			getCatalystInstance().loadScriptFromAssets(context.getAssets(),source,loadSynchronously);
		}
	}

	private   void loadScriptFromFile(String fileName, String sourceUrl,boolean loadSynchronously) {
		if(getCatalystInstance() != null){
			getCatalystInstance().loadScriptFromFile(fileName, sourceUrl,loadSynchronously);
		}

	}

	public  void setJsBundleAssetPath(ReactContext reactContext, String bundleAssetPath){
		reactContext
				.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
				.emit("sm-bundle-changed", bundleAssetPath);
	}

}
