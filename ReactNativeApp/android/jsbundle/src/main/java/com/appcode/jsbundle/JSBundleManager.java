package com.appcode.jsbundle;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

public class JSBundleManager {
	public static JSBundleManager instance;

	private Map<String,JSBundle> businessJSBundleMap = new HashMap<>();

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

	public void addJSBundle(JSBundle jsBundle){
		businessJSBundleMap.put(jsBundle.getMainComponentName(),jsBundle);
	}

	public JSBundle getJSBundle(String mainComonentname){
		return businessJSBundleMap.get(mainComonentname);
	}

	public JSBundle deleteJSBundle(String mainComonentname){
		return businessJSBundleMap.remove(mainComonentname);
	}
	public void addJSBundleToStackTop(JSBundle jsBundle){
		mJsBundlelinkedBlockingDeque.offer(jsBundle);
	}

	public JSBundle getCurrentJSBundle(){
		return mJsBundlelinkedBlockingDeque.peekFirst();
	}

	public  void stopJSbundle(String mainComponentName){
		mJsBundlelinkedBlockingDeque.pollFirst();
	}

	public JSBundle deleteJSBundle(JSBundle jsBundle){
		return businessJSBundleMap.remove(jsBundle.getMainComponentName());
	}

}
