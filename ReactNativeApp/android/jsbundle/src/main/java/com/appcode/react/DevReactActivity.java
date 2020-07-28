package com.appcode.react;

import androidx.annotation.Nullable;
import com.appcode.jsbundle.JSBundleSdk;
import com.appcode.jsbundle.JSIntent;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;

public class DevReactActivity extends ReactActivity {

	@Override
	protected ReactActivityDelegate createReactActivityDelegate() {
		return new DevReactActivityDelegate(this,getMainComponentName());
	}

	@Nullable
	@Override
	protected String getMainComponentName() {
		JSIntent jsIntent = JSBundleSdk.getJsIntent();
		if(jsIntent == null){
			finish();
		}
		return jsIntent.getMainComponentName();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
