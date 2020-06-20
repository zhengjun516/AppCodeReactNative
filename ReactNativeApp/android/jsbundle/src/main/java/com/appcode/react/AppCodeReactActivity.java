package com.appcode.react;

import androidx.annotation.Nullable;
import com.appcode.jsbundle.JSBundle;
import com.appcode.jsbundle.JSBundleManager;
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
		JSBundle jsBundle = JSBundleManager.getInstance().getStackTopJSBundle();
		if(jsBundle == null){
			finish();
		}
		return jsBundle.getDefaultMainComponentName();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		JSBundleManager.getInstance().destroyStackTopJSbundle(getMainComponentName());
	}
}
