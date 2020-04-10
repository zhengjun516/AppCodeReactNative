package com.appcode.reactnativeapp.react;

import android.app.Application;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;

import java.util.Arrays;
import java.util.List;

public abstract class AppCodeReactNativeHost extends ReactNativeHost {
	public AppCodeReactNativeHost(Application application) {
		super(application);
	}

	@Override
	public boolean getUseDeveloperSupport() {
		return false;
	}

	@Override
	protected List<ReactPackage> getPackages() {
		return Arrays.asList();
	}
}
