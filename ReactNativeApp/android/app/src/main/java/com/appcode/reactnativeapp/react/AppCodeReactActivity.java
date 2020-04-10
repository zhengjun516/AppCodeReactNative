package com.appcode.reactnativeapp.react;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;

public class AppCodeReactActivity extends ReactActivity {

	@Override
	protected ReactActivityDelegate createReactActivityDelegate() {
		return new AppCodeReactActivityDelegate(this,getMainComponentName());
	}
}
