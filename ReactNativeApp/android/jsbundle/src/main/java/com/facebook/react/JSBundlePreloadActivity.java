package com.facebook.react;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.appcode.jsbundle.JSBridge;
import com.appcode.jsbundle.JSApp;
import com.appcode.jsbundle.JSAppManager;
import com.appcode.jsbundle.OnJSAppLoadListener;
import com.facebook.react.bridge.ReactContext;

public class JSBundlePreloadActivity extends Activity {

	private JSBridge mJsBridge;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		JSApp jsBundle = JSAppManager.getInstance().getJSBundleFromMultiple(getMainComponentName());
		ReactNativeHost appCodeReactNativeHost = jsBundle.getReactNativeHost();
		mJsBridge = new JSBridge(appCodeReactNativeHost);

		ReactInstanceManager manager = appCodeReactNativeHost.getReactInstanceManager();
		if(!manager.hasStartedCreatingInitialContext() || mJsBridge.getCatalystInstance() == null){
			manager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener(){
				@Override
				public void onReactContextInitialized(ReactContext context) {
					loadScript(new OnJSAppLoadListener() {
						@Override
						public void onComplete(boolean success, JSApp jsBundle) {
							mJsBridge.setJsBundleAssetPath(manager.getCurrentReactContext(),jsBundle.getBundleAssetName());
							startToMultipeJSBundleActivity();
						}
					});
					manager.removeReactInstanceEventListener(this);
				}
			});
			manager.createReactContextInBackground();
		}else{
			loadScript(new OnJSAppLoadListener() {
				@Override
				public void onComplete(boolean success, JSApp jsBundle) {
					mJsBridge.setJsBundleAssetPath(manager.getCurrentReactContext(),jsBundle.getBundleAssetName());
					startToMultipeJSBundleActivity();
				}
			});
		}
	}

	public void loadScript(OnJSAppLoadListener onJSBundleLoadListener){
		JSApp jsBundle = JSAppManager.getInstance().getJSBundleFromMultiple(getMainComponentName());
		mJsBridge.loadScriptFile(jsBundle,false);
		if(onJSBundleLoadListener != null){
			onJSBundleLoadListener.onComplete(true,jsBundle);
		}
	}

	private void startToMultipeJSBundleActivity() {
		Intent intent = new Intent(getBaseContext(), JSBundleReactActivity.class);
		intent.putExtras(getIntent());
		startActivity(intent);
		finish();
	}

	public String getMainComponentName() {
		return getIntent().getStringExtra(JSApp.MAIN_COMPONENT_NAME);
	}
}
