package com.facebook.react;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.appcode.jsbundle.BuildConfig;
import com.appcode.jsbundle.JSBundle;

import java.util.Collections;
import java.util.List;

public class AppCodeMultipleReactNativeHost extends ReactNativeHost {

	JSBundle mJsBundle;

	public AppCodeMultipleReactNativeHost(JSBundle jsBundle, Application application) {
		super(application);
		mJsBundle = jsBundle;
	}

	@Override
	public boolean getUseDeveloperSupport() {
		return BuildConfig.DEBUG;
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
		return TextUtils.isEmpty(mJsBundle.getJSBundleFile())?null:mJsBundle.getJSBundleFile();
	}

	@Nullable
	@Override
	protected String getBundleAssetName() {
		return TextUtils.isEmpty(mJsBundle.getBundleAssetName())?null:mJsBundle.getBundleAssetName();
	}
}
