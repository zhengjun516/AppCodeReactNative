package com.appcode.reactnativeapp;

import android.app.Application;
import android.content.Context;

import com.appcode.jsbundle.JSAppSdk;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.soloader.SoLoader;


public class MainApplication extends Application implements ReactApplication {

  public static Context appContext;
  private static MainApplication instance;
  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    appContext = getApplicationContext();
    SoLoader.init(this, /* native exopackage */ false);
    JSAppSdk.init(this);
  }
/*
  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    public boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }
//返回JS 的路径   super.getJSBundleFile();   asserts/index.android.bundle
//    替换这个返回值  bundle路径   patch文件

    @Nullable
    @Override
    protected String getJSBundleFile() {
      File file = new File (FileConstant.JS_BUNDLE_LOCAL_PATH);
      if(file != null && file.exists()) {
        return FileConstant.JS_BUNDLE_LOCAL_PATH;
      } else {
        return super.getJSBundleFile();
      }

    }

    @Override
    protected List<ReactPackage> getPackages() {
      List<ReactPackage> reactPackages = new PackageList(this).getPackages();
      reactPackages.add(new CommPackage());
      return reactPackages;
    }

    @Override
    protected String getJSMainModuleName() {
      return "index";
    }
  };
*/

  @Override
  public ReactNativeHost getReactNativeHost() {
    return null;
  }


  /**
   *包名
   */
  public String getAppPackageName() {
    return this.getPackageName();
  }

  /**
   * 获取Application实例
   */
  public static MainApplication getInstance() {
    return instance;
  }





}
