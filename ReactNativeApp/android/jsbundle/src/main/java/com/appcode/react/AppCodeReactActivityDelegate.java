package com.appcode.react;

import androidx.annotation.Nullable;

import com.appcode.jsbundle.JSBundle;
import com.appcode.jsbundle.JSBundleManager;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactNativeHost;


public class AppCodeReactActivityDelegate extends ReactActivityDelegate {

	public AppCodeReactActivityDelegate(ReactActivity activity, @Nullable String mainComponentName) {
		super(activity, mainComponentName);
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		JSBundle jsBundle = JSBundleManager.getInstance().getJSBundle(getMainComponentName());
		if(jsBundle.getReactNativeHost() != null){
			return jsBundle.getReactNativeHost();
		}
		return new AppCodeReactNativeHost(JSBundleManager.getInstance().getJSBundle(getMainComponentName()),getPlainActivity().getApplication());
	}


}
