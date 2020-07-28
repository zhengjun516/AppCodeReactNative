package com.appcode.react;

import android.app.Application;

import com.appcode.jsbundle.JSBundleSdk;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;

import java.util.List;

public class DevReactNativeHost extends ReactNativeHost {

	public DevReactNativeHost(Application application) {
		super(application);
	}

	@Override
	protected List<ReactPackage> getPackages() {
		return JSBundleSdk.getGetReactPackageCallback().getReactPackages(this);
	}

	@Override
	public boolean getUseDeveloperSupport() {
		return JSBundleSdk.isDebug();
	}

	@Override
	protected String getJSMainModuleName() {
		return "index";
	}


}
