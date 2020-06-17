package com.facebook.react;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.appcode.jsbundle.JSBundleSdk;


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
		ReactNativeHost appCodeReactNativeHost = JSBundleSdk.getAppCodeReactNativeHost(getMainComponentName());
		if(appCodeReactNativeHost != null){
			return appCodeReactNativeHost;
		}
		return new MultipleReactNativeHost(JSBundleSdk.getJSBundler(getMainComponentName()),getPlainActivity().getApplication());
	}


}
