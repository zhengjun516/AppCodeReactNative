package com.appcode.react;

import androidx.annotation.Nullable;
import com.appcode.jsbundle.JSBundle;
import com.appcode.jsbundle.JSBundleSdk;
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
		JSBundle jsBundle = JSBundleSdk.getCurrentBundle();

		if(jsBundle == null){
			finish();
		}
		return jsBundle.getMainComponentName();
	}


}
