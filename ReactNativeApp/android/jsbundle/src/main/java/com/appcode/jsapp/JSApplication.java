package com.appcode.jsapp;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;

public class JSApplication extends Application implements ReactApplication {

	private Application mApplication;

	public ReactNativeHost mReactNativeHost;

	public JSApplication(Application application) {
		mApplication = application;
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		return null;
	}
}
