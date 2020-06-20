package com.facebook.react;

import android.app.Application;
import androidx.annotation.Nullable;

import com.appcode.jsapp.JSApp;

import java.util.Collections;
import java.util.List;

public class JSAppReactNativeHost extends ReactNativeHost {

	JSApp mJsBundle;
	public JSAppReactNativeHost(JSApp jsBundle, Application application) {
		super(application);
		mJsBundle = jsBundle;
	}

	@Override
	public boolean getUseDeveloperSupport() {
		return false;
	}

	@Override
	protected List<ReactPackage> getPackages() {
		if(mJsBundle.getGetReactPackageCallback() != null){
			return mJsBundle.getGetReactPackageCallback().getReactPackages(this);
		}
		return Collections.emptyList();
	}

	@Nullable
	@Override
	protected String getJSBundleFile() {
		if(mJsBundle.isSupportCommonJSBundle()){
			return mJsBundle.getCommonJSBundleFile();
		}
		return mJsBundle.getJSBundleFile();
	}

	@Nullable
	@Override
	protected String getBundleAssetName() {
		if(mJsBundle.isSupportCoomonJSBundleAsset()){
			return mJsBundle.getCommonJSBundleAssetName();
		}
		return mJsBundle.getBundleAssetName();
	}
}
