package com.appcode.react;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;

public class AppCodeApplication extends Application implements ReactApplication {

	private Application mApplication;

	public ReactNativeHost mReactNativeHost;

	public AppCodeApplication(Application application) {
		mApplication = application;
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		return null;
	}
}
