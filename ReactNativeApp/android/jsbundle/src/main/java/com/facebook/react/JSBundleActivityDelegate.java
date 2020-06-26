package com.facebook.react;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.appcode.jsbundle.JSBundle;
import com.appcode.jsbundle.JSBundleManager;
import com.appcode.jsbundle.JSIntent;

public class JSBundleActivityDelegate extends ReactActivityDelegate {
	JSBundle mJSBundle;

	public JSBundleActivityDelegate(Activity activity, JSBundle jsBundle, JSIntent jsIntent) {
		super(activity, jsIntent.getMainComponentName());
		mJSBundle = jsBundle;
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
		if(mJSBundle.getReactNativeHost() != null){
			return mJSBundle.getReactNativeHost();
		}
		return  null;
	}


}
