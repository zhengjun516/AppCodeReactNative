package com.appcode.jsbundle;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

public class JSAppManager {
	public static JSAppManager instance;

	private Map<String, JSApp> mStandardJSBundleMap = new HashMap<>();
	private Map<String, JSApp> mMultipleJSBundleMap = new HashMap<>();

	private LinkedBlockingDeque<JSApp> mJsBundlelinkedBlockingDeque = new LinkedBlockingDeque<>();

	private JSAppManager(){

	}
	public static JSAppManager getInstance(){
		if(instance == null){
			synchronized (JSAppManager.class){
				if(instance == null){
					instance = new JSAppManager();
				}
			}
		}
		return instance;
	}

	public Map<String, JSApp> getStandardJSBundleMap() {
		return mStandardJSBundleMap;
	}

	public Map<String, JSApp> getMultipleJSBundleMap() {
		return mMultipleJSBundleMap;
	}

	public boolean hasStandardJSBundle(JSApp jsBundle){
		return hasStandardJSBundle(jsBundle.getDefaultMainComponentName());
	}
	public boolean hasMultipleJSBundle(JSApp jsBundle){
		return hasMultipleJSBundle(jsBundle.getDefaultMainComponentName());
	}

	public boolean hasStandardJSBundle(String mainComonentname){
		return mStandardJSBundleMap.containsKey(mainComonentname);
	}

	public boolean hasMultipleJSBundle(String mainComonentname){
		return mMultipleJSBundleMap.containsKey(mainComonentname);
	}

	public void addJSBundle(JSApp jsBundle){
		if(jsBundle.isMultipleJSBundle()){
			mMultipleJSBundleMap.put(jsBundle.getDefaultMainComponentName(),jsBundle);
		}else{
			mStandardJSBundleMap.put(jsBundle.getDefaultMainComponentName(),jsBundle);
		}
	}

	public JSApp getJSBundleFromStandard(String mainComonentname){
		return mStandardJSBundleMap.get(mainComonentname);
	}

	public JSApp getJSBundleFromMultiple(String mainComonentname){
		return mMultipleJSBundleMap.get(mainComonentname);
	}

	public JSApp deleteJSBundleFromStandard(JSApp jsBundle){
		return deleteJSBundleFromStandard(jsBundle.getDefaultMainComponentName());
	}
	public JSApp deleteJSBundleFromStandard(String mainComonentname){
		return mStandardJSBundleMap.remove(mainComonentname);
	}

	public JSApp deleteJSBundleFromMultiple(JSApp jsBundle){
		return deleteJSBundleFromMultiple(jsBundle.getDefaultMainComponentName());
	}

	public JSApp deleteJSBundleFromMultiple(String mainComonentname){
		return mMultipleJSBundleMap.remove(mainComonentname);
	}

	public void addJSBundleToStackTop(JSApp jsBundle){
		mJsBundlelinkedBlockingDeque.offer(jsBundle);
	}

	public JSApp getStackTopJSBundle(){
		return mJsBundlelinkedBlockingDeque.peekFirst();
	}

	public  void destroyStackTopJSbundle(String mainComponentName){
		mJsBundlelinkedBlockingDeque.pollFirst();
	}



}
