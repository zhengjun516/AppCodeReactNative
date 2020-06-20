package com.appcode.react;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.appcode.jsapp.JSApp;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;

import java.util.Collections;
import java.util.List;

public class AppCodeReactNativeHost extends ReactNativeHost {

	JSApp mJsBundle;

	public AppCodeReactNativeHost(JSApp jsBundle, Application application) {
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
		return TextUtils.isEmpty(mJsBundle.getJSBundleFile())?null:mJsBundle.getJSBundleFile();
	}

	@Nullable
	@Override
	protected String getBundleAssetName() {
		return TextUtils.isEmpty(mJsBundle.getBundleAssetName())?null:mJsBundle.getBundleAssetName();
	}
}
