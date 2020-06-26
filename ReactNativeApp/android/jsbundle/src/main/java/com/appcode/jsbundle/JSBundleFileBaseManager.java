package com.appcode.jsbundle;

import android.content.res.AssetManager;
import android.text.TextUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class JSBundleFileBaseManager {
	public static final String TAG = JSBundleFileBaseManager.class.getSimpleName();


	protected String mBundlesDir;
	protected List<JSBundle> jsBundles;
	protected GetReactPackageCallback getReactPackageCallback;

	public JSBundleFileBaseManager(String bundlesDir){
		mBundlesDir = bundlesDir;
		jsBundles = new ArrayList<>();
	}

	public void init(String bundlesDir){
		try {
			if(!TextUtils.isEmpty(bundlesDir)){
				mBundlesDir = bundlesDir;
			}

			String[] bundleDirs = getChildDirs(null,mBundlesDir);
			for(String bundleDir : bundleDirs){
				parseBundleDir(bundleDir);
			}
		}catch (IOException e){
			JSBLog.e(TAG,e);
		}
	}

	protected void parseBundleDir(String bundleDir) throws IOException {

		String[] bundleFiles = getChildDirs(mBundlesDir,bundleDir);
		int i = 0;
		for(String bundleFile:bundleFiles){
			if(bundleFile.endsWith(".bundle")){
				i++;
			}
		}
		if(i <= 0){
			return;
		}
		if(i == 1){
			for(String bundleFile:bundleFiles){
				if(bundleFile.endsWith(".bundle")){
					JSBundleInfo jsBundleInfo = readAssetsJSBundle(JSBundleConstant.DIR_BUNDLES + File.separator+bundleDir+File.separator+bundleFile);
					if(jsBundleInfo != null){
						JSBundle jsBundle = new JSBundle(jsBundleInfo);
						jsBundle.setGetReactPackageCallback(getReactPackageCallback);
						JSBundleManager.getInstance().addJSBundle(jsBundle);
					}
				}
			}
		}else{
			JSBundle jsBundle;
			JSBundleInfo baseJSBundleInfo = null;
			Map<String,JSBundleInfo> jsBundleInfoHashMap = new HashMap<>();

			for(String bundleFile:bundleFiles){
				if(bundleFile.endsWith(".bundle")){
					JSBundleInfo jsBundleInfo = readAssetsJSBundle(JSBundleConstant.DIR_BUNDLES + File.separator+bundleDir+File.separator+bundleFile);
					if(jsBundleInfo.isBaseBundle()){
						baseJSBundleInfo = jsBundleInfo;
					}else{
						jsBundleInfoHashMap.put(jsBundleInfo.getJsBundleFile(),jsBundleInfo);
					}
				}
			}
			for(JSBundleInfo jsBundleInfo:jsBundleInfoHashMap.values()){
				jsBundle = new JSBundle(jsBundleInfo,baseJSBundleInfo);
				jsBundle.setGetReactPackageCallback(getReactPackageCallback);
				JSBundleManager.getInstance().addJSBundle(jsBundle);
			}
		}
	}

	public JSBundleInfo readAssetsJSBundle(String jsBundleFile) throws IOException {
		List<String> jsBundleComponentList = new ArrayList<>();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getInputStream(jsBundleFile)));
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			if(line.contains("exports={name:\"")){
				jsBundleComponentList.add(line);
			}
		}
		if(jsBundleComponentList.size() <= 0){
			return new JSBundleInfo(null,jsBundleFile);
		}
		return parseJSBundleFile(jsBundleFile,jsBundleComponentList);
	}

	public JSBundleInfo parseJSBundleFile(String jsBundleFile,List<String> jsBundleComponentList){
		if(jsBundleComponentList == null || jsBundleComponentList.size() <= 0){
			return null;
		}
		List<String> mainComponentNames = new ArrayList<>();

		for(String mainComponentLine:jsBundleComponentList){
			JSONObject jsonObject = parseJsonObject(mainComponentLine);
			if(jsonObject != null){
				mainComponentNames.add(jsonObject.optString("name"));
			}
		}

		if(mainComponentNames.size() <= 0){
			return null;
		}

		JSBundleInfo jsBundleInfo= null;
		if(mainComponentNames.size() > 0){
			jsBundleInfo = new JSBundleInfo(mainComponentNames,jsBundleFile);
		}

		return jsBundleInfo;
	}

	private JSONObject parseJsonObject(String mainComponentLine){
		try {
			String mainComponentJson =  mainComponentLine.substring(mainComponentLine.lastIndexOf("{"),mainComponentLine.lastIndexOf("}"));
			JSONObject jsonObject = new JSONObject(mainComponentJson) ;
			return jsonObject;
		}catch (Exception e){
			return  null;
		}
	}



	public void setGetReactPackageCallback(GetReactPackageCallback getReactPackageCallback){
		this.getReactPackageCallback = getReactPackageCallback;
	}

	public GetReactPackageCallback getGetReactPackageCallback() {
		return getReactPackageCallback;
	}

	public abstract String[] getChildDirs(String parentDir,String bundlesDir) throws IOException;

	public abstract InputStream getInputStream(String jsBundleFile) throws IOException;
}
