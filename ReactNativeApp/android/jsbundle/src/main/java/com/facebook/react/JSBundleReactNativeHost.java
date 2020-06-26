package com.facebook.react;

import android.app.Application;
import androidx.annotation.Nullable;

import com.appcode.jsbundle.JSBundle;

import java.util.Collections;
import java.util.List;

public class JSBundleReactNativeHost extends ReactNativeHost {

	JSBundle mJsBundle;
	public JSBundleReactNativeHost(JSBundle jsBundle, Application application) {
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
		if(mJsBundle.isMultipleJSBundle()){
			return mJsBundle.getCommonJSBundleFile();
		}
		return mJsBundle.getJSBundleFile();
	}

	@Nullable
	@Override
	protected String getBundleAssetName() {
		if(mJsBundle.isMultipleJSBundle()){
			return mJsBundle.getCommonJSBundleAssetName();
		}
		return mJsBundle.getBundleAssetName();
	}
}
