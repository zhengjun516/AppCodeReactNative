package com.facebook.react;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.appcode.jsapp.JSApp;
import com.appcode.jsapp.JSAppManager;

public class JSAppReactActivityDelegate extends ReactActivityDelegate {

	public JSAppReactActivityDelegate(Activity activity, @Nullable String mainComponentName) {
		super(activity, mainComponentName);
	}

	public JSAppReactActivityDelegate(ReactActivity activity, @Nullable String mainComponentName) {
		super(activity, mainComponentName);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		JSApp jsBundle = JSAppManager.getInstance().getJSBundleFromMultiple(getMainComponentName());

		return jsBundle.getReactNativeHost();
	}


}
