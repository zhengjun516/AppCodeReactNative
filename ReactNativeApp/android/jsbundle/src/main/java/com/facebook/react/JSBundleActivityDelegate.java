package com.facebook.react;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.appcode.jsbundle.JSApp;
import com.appcode.jsbundle.JSAppManager;

public class JSBundleActivityDelegate extends ReactActivityDelegate {

	public JSBundleActivityDelegate(Activity activity, @Nullable String mainComponentName) {
		super(activity, mainComponentName);
	}

	public JSBundleActivityDelegate(ReactActivity activity, @Nullable String mainComponentName) {
		super(activity, mainComponentName);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		JSApp jsBundle = JSAppManager.getInstance().getJSBundleFromMultiple(getMainComponentName());
		if(jsBundle.getReactNativeHost() != null){
			return jsBundle.getReactNativeHost();
		}
		return new JSBundleReactNativeHost(JSAppManager.getInstance().getJSBundleFromMultiple(getMainComponentName()),getPlainActivity().getApplication());
	}


}
