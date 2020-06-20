package com.appcode.react;

import androidx.annotation.Nullable;

import com.appcode.jsapp.JSApp;
import com.appcode.jsapp.JSAppManager;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactNativeHost;


public class AppCodeReactActivityDelegate extends ReactActivityDelegate {

	public AppCodeReactActivityDelegate(ReactActivity activity, @Nullable String mainComponentName) {
		super(activity, mainComponentName);
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		JSApp jsBundle = JSAppManager.getInstance().getJSBundleFromStandard(getMainComponentName());
		return jsBundle.getReactNativeHost();
	}


}
