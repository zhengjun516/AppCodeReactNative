package com.facebook.react;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.appcode.jsbundle.JSBundle;
import com.appcode.jsbundle.JSBundleManager;

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
		JSBundle jsBundle = JSBundleManager.getInstance().getJSBundleFromMultiple(getMainComponentName());
		if(jsBundle.getReactNativeHost() != null){
			return jsBundle.getReactNativeHost();
		}
		return new JSBundleReactNativeHost(JSBundleManager.getInstance().getJSBundleFromMultiple(getMainComponentName()),getPlainActivity().getApplication());
	}


}
