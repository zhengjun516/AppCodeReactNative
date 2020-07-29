package com.appcode.jsbundle;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
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
	protected GetReactPackageCallback getReactPackageCallback;

	public JSBundleFileBaseManager(String bundlesDir){
		mBundlesDir = bundlesDir;
	}

	public void init(String bundlesDir){
		try {
			if(!TextUtils.isEmpty(bundlesDir)){
				mBundlesDir = bundlesDir;
			}
			File bundlesFolder = new File(mBundlesDir);
			if(!bundlesFolder.exists()){
				bundlesFolder.mkdirs();
			}

			String[] bundleDirs = getChildDirs(null,mBundlesDir);

			JSBundleInfo baseJSBundleInfo = null;
            List<JSBundleInfo> jsBundleInfos = new ArrayList<>();
			for(String bundleDir : bundleDirs){
				JSBundleInfo jsBundleInfo = parseBundleDir(mBundlesDir,bundleDir);
				if(jsBundleInfo.isBaseBundle){
					baseJSBundleInfo = jsBundleInfo;
				}else{
					jsBundleInfos.add(jsBundleInfo);
				}
			}

			for(JSBundleInfo jsBundleInfo:jsBundleInfos){
				JSBundle jsBundle = new JSBundle(jsBundleInfo,baseJSBundleInfo);
				jsBundle.setGetReactPackageCallback(getReactPackageCallback);
				JSBundleManager.getInstance().addJSBundle(jsBundle);
			}
		}catch (IOException e){
			JSBLog.e(TAG,e);
		}catch (JSONException e){
			JSBLog.e(TAG,e);
		}
	}

	protected JSBundleInfo parseBundleDir(String parentDir, String bundleDir) throws IOException, JSONException {
			String bundleDirPath = parentDir+File.separator+bundleDir+File.separator;
			String manifestJson = readManifest(bundleDirPath+"manifest.json");
			JSONObject jsonObject = new JSONObject(manifestJson);
			JSBundleInfo jsBundleInfo = new JSBundleInfo(parentDir,
					jsonObject.optString(JSBundleInfo.BUNDLE_DIR),
														jsonObject.optString(JSBundleInfo.BUNDLE_FILE),
														jsonObject.optString(JSBundleInfo.MAIN_COMPONENT),
														jsonObject.optInt(JSBundleInfo.VERSION),
														jsonObject.optBoolean(JSBundleInfo.IS_BASE_BUNDLE),
														jsonObject.optBoolean(JSBundleInfo.IS_PRELOAD),
														jsonObject.optString(JSBundleInfo.MD5));
			return jsBundleInfo;
	}

	public String readManifest(String manifestFile) throws IOException{
		BufferedReader bufferedReader  = new BufferedReader(new InputStreamReader(getInputStream(manifestFile)));
		StringBuilder builder = new StringBuilder();
		String line;
		while((line=bufferedReader.readLine()) != null){
			builder.append(line);
		}
		bufferedReader.close();
		return builder.toString();
	}


	protected void parseBundleDir2(String parentDir,String bundleDir) throws IOException {

		String[] bundleFiles = getChildDirs(parentDir,bundleDir);
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
					JSBundleInfo jsBundleInfo = readAssetsJSBundle(parentDir+File.separator+bundleDir+File.separator+bundleFile);
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
					JSBundleInfo jsBundleInfo = readAssetsJSBundle(parentDir+File.separator+bundleDir+File.separator+bundleFile);
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
