package com.appcode.react;

import androidx.annotation.Nullable;

import com.appcode.jsbundle.JSBundle;
import com.appcode.jsbundle.JSBundleManager;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactNativeHost;


public class DevReactActivityDelegate extends ReactActivityDelegate {

	public DevReactActivityDelegate(ReactActivity activity, @Nullable String mainComponentName) {
		super(activity, mainComponentName);
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		return new DevReactNativeHost(getPlainActivity().getApplication());
	}


}
