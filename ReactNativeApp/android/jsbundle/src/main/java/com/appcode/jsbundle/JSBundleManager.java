package com.appcode.jsbundle;

import com.appcode.react.AppCodeReactNativeHost;
import com.facebook.react.JSBundleReactNativeHost;
import com.facebook.react.ReactNativeHost;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

public class JSBundleManager {
	public static JSBundleManager instance;

	private Map<String,JSBundle> mStandardJSBundleMap = new HashMap<>();
	private Map<String,JSBundle> mMultipleJSBundleMap = new HashMap<>();

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

	public Map<String, JSBundle> getStandardJSBundleMap() {
		return mStandardJSBundleMap;
	}

	public Map<String, JSBundle> getMultipleJSBundleMap() {
		return mMultipleJSBundleMap;
	}

	public boolean hasStandardJSBundle(JSBundle jsBundle){
		return hasStandardJSBundle(jsBundle.getDefaultMainComponentName());
	}
	public boolean hasMultipleJSBundle(JSBundle jsBundle){
		return hasMultipleJSBundle(jsBundle.getDefaultMainComponentName());
	}

	public boolean hasStandardJSBundle(String mainComonentname){
		return mStandardJSBundleMap.containsKey(mainComonentname);
	}

	public boolean hasMultipleJSBundle(String mainComonentname){
		return mMultipleJSBundleMap.containsKey(mainComonentname);
	}

	public void addJSBundle(JSBundle jsBundle){
		ReactNativeHost reactNativeHost;
		if(jsBundle.isMultipleJSBundle()){
			reactNativeHost = new JSBundleReactNativeHost(jsBundle,JSBundleSdk.getApplication());
		}else{
			reactNativeHost = new AppCodeReactNativeHost(jsBundle,JSBundleSdk.getApplication());
		}
		jsBundle.setReactNativeHost(reactNativeHost);

		if(jsBundle.isMultipleJSBundle()){
			mMultipleJSBundleMap.put(jsBundle.getDefaultMainComponentName(),jsBundle);
		}else{
			mStandardJSBundleMap.put(jsBundle.getDefaultMainComponentName(),jsBundle);
		}
	}

	public JSBundle getJSBundleFromStandard(String mainComonentname){
		return mStandardJSBundleMap.get(mainComonentname);
	}

	public JSBundle getJSBundleFromMultiple(String mainComonentname){
		return mMultipleJSBundleMap.get(mainComonentname);
	}

	public JSBundle deleteJSBundleFromStandard(JSBundle jsBundle){
		return deleteJSBundleFromStandard(jsBundle.getDefaultMainComponentName());
	}
	public JSBundle deleteJSBundleFromStandard(String mainComonentname){
		return mStandardJSBundleMap.remove(mainComonentname);
	}

	public JSBundle deleteJSBundleFromMultiple(JSBundle jsBundle){
		return deleteJSBundleFromMultiple(jsBundle.getDefaultMainComponentName());
	}

	public JSBundle deleteJSBundleFromMultiple(String mainComonentname){
		return mMultipleJSBundleMap.remove(mainComonentname);
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
