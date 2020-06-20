package com.appcode.react;

import androidx.annotation.Nullable;
import com.appcode.jsbundle.JSApp;
import com.appcode.jsbundle.JSAppManager;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;

public class AppCodeReactActivity extends ReactActivity {

	@Override
	protected ReactActivityDelegate createReactActivityDelegate() {
		return new AppCodeReactActivityDelegate(this,getMainComponentName());
	}

	@Nullable
	@Override
	protected String getMainComponentName() {
		JSApp jsBundle = JSAppManager.getInstance().getStackTopJSBundle();
		if(jsBundle == null){
			finish();
		}
		return jsBundle.getDefaultMainComponentName();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		JSAppManager.getInstance().destroyStackTopJSbundle(getMainComponentName());
	}
}
