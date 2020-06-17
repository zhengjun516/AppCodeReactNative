package com.facebook.react;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.appcode.jsbundle.JSBridge;
import com.appcode.jsbundle.JSBundle;
import com.appcode.jsbundle.JSBundleSdk;
import com.appcode.jsbundle.OnJSBundleLoadListener;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactContext;

public class MultipleJSBundlePreloadActivity extends Activity {

	private JSBridge mJsBridge;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		JSBundle jsBundle = JSBundleSdk.getJSBundler(getMainComponentName());
		ReactNativeHost appCodeReactNativeHost = JSBundleSdk.getAppCodeReactNativeHost(jsBundle.getMainComponentName());
		mJsBridge = new JSBridge(appCodeReactNativeHost);

		ReactInstanceManager manager = appCodeReactNativeHost.getReactInstanceManager();
		if(!manager.hasStartedCreatingInitialContext() || mJsBridge.getCatalystInstance() == null){
			manager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener(){
				@Override
				public void onReactContextInitialized(ReactContext context) {
					loadScript(new OnJSBundleLoadListener() {
						@Override
						public void onComplete(boolean success, JSBundle jsBundle) {
							mJsBridge.setJsBundleAssetPath(manager.getCurrentReactContext(),jsBundle.getBundleAssetName());
							startToMultipeJSBundleActivity();
						}
					});
					manager.removeReactInstanceEventListener(this);
				}
			});
			manager.createReactContextInBackground();
		}else{
			loadScript(new OnJSBundleLoadListener() {
				@Override
				public void onComplete(boolean success, JSBundle jsBundle) {
					mJsBridge.setJsBundleAssetPath(manager.getCurrentReactContext(),jsBundle.getBundleAssetName());
					startToMultipeJSBundleActivity();
				}
			});
		}
	}

	public void loadScript(OnJSBundleLoadListener onJSBundleLoadListener){
		JSBundle  jsBundle = JSBundleSdk.getJSBundler(getMainComponentName());
		mJsBridge.loadScriptFile(jsBundle,false);
		if(onJSBundleLoadListener != null){
			onJSBundleLoadListener.onComplete(true,jsBundle);
		}
	}

	private void startToMultipeJSBundleActivity() {
		Intent intent = new Intent(getBaseContext(), MultipleJSBundleActivity.class);
		intent.putExtras(getIntent());
		startActivity(intent);
		finish();
	}

	public CatalystInstance getCatalystInstance(MultipleReactNativeHost appCodeReactNativeHost) {
		ReactInstanceManager manager = appCodeReactNativeHost.getReactInstanceManager();

		if (manager.getCurrentReactContext() == null) {
			return null;
		}
		return manager.getCurrentReactContext().getCatalystInstance();
	}

	public String getMainComponentName() {
		return getIntent().getStringExtra(JSBundle.MAIN_COMPONENT_NAME);
	}
}
