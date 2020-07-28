package com.appcode.jsbundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.appcode.react.DevReactActivity;
import com.facebook.react.JSBundleReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReactContext;

public class JSBundlePreloadActivity extends Activity {

	private JSBridge mJsBridge;
	private JSBundle mJsBundle;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		JSIntent jsIntent = getIntent().getParcelableExtra(JSIntent.KEY_JS_INTENT);
		if (jsIntent == null) {
			finish();
			return;
		}
		if (JSBundleSdk.isDebug()) {
			startToDevReactActivity();
		} else {
			mJsBundle = JSBundleManager.getInstance().getJSBundle(jsIntent.getPackageName());
			if (mJsBundle == null) {
				finish();
				return;
			}

			ReactNativeHost appCodeReactNativeHost = mJsBundle.getReactNativeHost();
			mJsBridge = new JSBridge(appCodeReactNativeHost);
			ReactInstanceManager manager = appCodeReactNativeHost.getReactInstanceManager();
			if (!manager.hasStartedCreatingInitialContext() || mJsBridge.getCatalystInstance() == null) {
				manager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
					@Override
					public void onReactContextInitialized(ReactContext context) {
						loadScript(new OnJSBundleLoadListener() {
							@Override
							public void onComplete(boolean success, JSBundle jsBundle) {
								mJsBridge.setJsBundleAssetPath(manager.getCurrentReactContext(), jsBundle.getBundleAssetName());
								startToMultipeJSBundleActivity();
							}
						});
						manager.removeReactInstanceEventListener(this);
					}
				});
				manager.createReactContextInBackground();
			} else {
				loadScript(new OnJSBundleLoadListener() {
					@Override
					public void onComplete(boolean success, JSBundle jsBundle) {
						mJsBridge.setJsBundleAssetPath(manager.getCurrentReactContext(), jsBundle.getBundleAssetName());
						startToMultipeJSBundleActivity();
					}
				});
			}
		}


	}

	public void loadScript(OnJSBundleLoadListener onJSBundleLoadListener) {
		//当设置成debug模式时，所有需要的业务代码已经都加载好了
		if (JSBundleSdk.isDebug()) {
			onJSBundleLoadListener.onComplete(true, null);
			return;
		}

		mJsBridge.loadScriptFile(mJsBundle, false);
		if (onJSBundleLoadListener != null) {
			onJSBundleLoadListener.onComplete(true, mJsBundle);
		}
	}

	private void startToMultipeJSBundleActivity() {
		Intent intent = new Intent(getBaseContext(), JSBundleReactActivity.class);
		intent.putExtras(getIntent());
		startActivity(intent);
		finish();
	}

	private void startToDevReactActivity() {
		Intent intent = new Intent(getBaseContext(), DevReactActivity.class);
		intent.putExtras(getIntent());
		startActivity(intent);
		finish();
	}

	public String getMainComponentName() {
		return getIntent().getStringExtra(JSBundle.MAIN_COMPONENT_NAME);
	}
}
