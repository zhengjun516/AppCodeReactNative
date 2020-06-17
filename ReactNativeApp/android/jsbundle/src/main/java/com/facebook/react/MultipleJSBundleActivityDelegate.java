package com.facebook.react;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.appcode.jsbundle.BuildConfig;
import com.appcode.jsbundle.JSBridge;
import com.appcode.jsbundle.JSBundle;
import com.appcode.jsbundle.JSBundleSdk;
import com.appcode.jsbundle.OnJSBundleLoadListener;
import com.appcode.react.AppCodeReactNativeHost;
import com.facebook.react.bridge.ReactContext;


public class MultipleJSBundleActivityDelegate extends ReactActivityDelegate {



	public MultipleJSBundleActivityDelegate(Activity activity, @Nullable String mainComponentName) {
		super(activity, mainComponentName);
	}

	public MultipleJSBundleActivityDelegate(ReactActivity activity, @Nullable String mainComponentName) {
		super(activity, mainComponentName);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	}



	@Override
	public ReactNativeHost getReactNativeHost() {
		AppCodeReactNativeHost appCodeReactNativeHost = JSBundleSdk.getAppCodeReactNativeHost(getMainComponentName());
		if(appCodeReactNativeHost != null){
			return appCodeReactNativeHost;
		}
		return new AppCodeReactNativeHost(JSBundleSdk.getJSBundler(getMainComponentName()),getPlainActivity().getApplication());
	}


}
