package com.facebook.react;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.appcode.jsapp.JSApp;
import com.appcode.jsapp.JSAppManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;

public class JSBundleReactActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler, PermissionAwareActivity {
	private  ReactActivityDelegate mDelegate;
	public JSBundleReactActivity() {

	}
	/**
	 * Returns the name of the main component registered from JavaScript. This is used to schedule
	 * rendering of the component. e.g. "MoviesApp"
	 */
	public @Nullable String getMainComponentName() {
		JSApp jsBundle = JSAppManager.getInstance().getStackTopJSBundle();
		if(jsBundle == null){
			finish();
		}
		return jsBundle.getDefaultMainComponentName();
	}

	/** Called at construction time, override if you have a custom delegate implementation. */
	public ReactActivityDelegate createReactActivityDelegate() {
		return new JSBundleActivityDelegate(this, getMainComponentName());
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDelegate = createReactActivityDelegate();

		mDelegate.onCreate(savedInstanceState);
	}

	@Override
	public void onPause() {
		super.onPause();
		mDelegate.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mDelegate.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mDelegate.onDestroy();
		JSAppManager.getInstance().destroyStackTopJSbundle(getMainComponentName());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		mDelegate.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return mDelegate.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return mDelegate.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		return mDelegate.onKeyLongPress(keyCode, event) || super.onKeyLongPress(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		if (!mDelegate.onBackPressed()) {
			super.onBackPressed();
		}
	}

	@Override
	public void invokeDefaultOnBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void onNewIntent(Intent intent) {
		if (!mDelegate.onNewIntent(intent)) {
			super.onNewIntent(intent);
		}
	}

	@Override
	public void requestPermissions(
			String[] permissions, int requestCode, PermissionListener listener) {
		mDelegate.requestPermissions(permissions, requestCode, listener);
	}

	@Override
	public void onRequestPermissionsResult(
			int requestCode, String[] permissions, int[] grantResults) {
		mDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		mDelegate.onWindowFocusChanged(hasFocus);
	}

	public final ReactNativeHost getReactNativeHost() {
		return mDelegate.getReactNativeHost();
	}

	public final ReactInstanceManager getReactInstanceManager() {
		return mDelegate.getReactInstanceManager();
	}

	public final void loadApp(String appKey) {
		mDelegate.loadApp(appKey);
	}

}
