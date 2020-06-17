package com.appcode.react;

import androidx.annotation.Nullable;

import com.appcode.jsbundle.JSBundleSdk;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactNativeHost;


public class AppCodeReactActivityDelegate extends ReactActivityDelegate {

	public AppCodeReactActivityDelegate(ReactActivity activity, @Nullable String mainComponentName) {
		super(activity, mainComponentName);
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		return new AppCodeReactNativeHost(JSBundleSdk.getJSBundler(getMainComponentName()),getPlainActivity().getApplication());
	}


}
