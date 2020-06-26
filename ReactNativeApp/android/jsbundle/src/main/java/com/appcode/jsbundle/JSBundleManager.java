package com.appcode.jsbundle;

import com.appcode.react.AppCodeReactNativeHost;
import com.facebook.react.JSBundleReactNativeHost;
import com.facebook.react.ReactNativeHost;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

public class JSBundleManager {
	public static JSBundleManager instance;

	private Map<String,JSBundle> mJSBundleMap = new HashMap<>();

	private LinkedBlockingDeque<JSBundle> mJsBundlelinkedBlockingDeque = new LinkedBlockingDeque<>();

	private JSBundleManager(){

	}
	public static JSBundleManager getInstance(){
		if(instance == null){
			synchronized (JSBundleManager.class){
				if(instance == null){
					instance = new JSBundleManager();
				}
			}
		}
		return instance;
	}

	public Map<String, JSBundle> getJSBundleMap() {
		return mJSBundleMap;
	}


	public boolean hasJSBundle(JSBundle jsBundle){
		return hasJSBundle(jsBundle.getPackageName());
	}


	public boolean hasJSBundle(String mainComonentname){
		return mJSBundleMap.containsKey(mainComonentname);
	}

	public void addJSBundle(JSBundle jsBundle){
		ReactNativeHost reactNativeHost;
		if(jsBundle.isSimpleJSBundle()){
			reactNativeHost = new AppCodeReactNativeHost(jsBundle,JSBundleSdk.getApplication());
		}else{
			reactNativeHost = new JSBundleReactNativeHost(jsBundle,JSBundleSdk.getApplication());
		}
		jsBundle.setReactNativeHost(reactNativeHost);

		mJSBundleMap.put(jsBundle.getPackageName(),jsBundle);

	}

	public JSBundle getJSBundle(String mainComonentname){
		return mJSBundleMap.get(mainComonentname);
	}


	public JSBundle deleteJSBundle(JSBundle jsBundle){
		return deleteJSBundle(jsBundle.getPackageName());
	}

	public JSBundle deleteJSBundle(String mainComonentname){
		return mJSBundleMap.remove(mainComonentname);
	}

	public void addJSBundleToStackTop(JSBundle jsBundle){
		mJsBundlelinkedBlockingDeque.offer(jsBundle);
	}

	public JSBundle getStackTopJSBundle(){
		return mJsBundlelinkedBlockingDeque.peekFirst();
	}

	public  void destroyStackTopJSbundle(String mainComponentName){
		mJsBundlelinkedBlockingDeque.pollFirst();
	}



}
