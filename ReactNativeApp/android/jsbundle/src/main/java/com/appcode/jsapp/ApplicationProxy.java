package com.appcode.jsapp;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;

public class ApplicationProxy{

	private Application mApplication;

	public ApplicationProxy(Application application) {
		mApplication = application;
	}

	public Application getApplication(){
		return mApplication;
	}



}
