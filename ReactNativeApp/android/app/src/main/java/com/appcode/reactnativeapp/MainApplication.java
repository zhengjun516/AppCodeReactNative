package com.appcode.reactnativeapp;

import android.app.Application;
import android.content.Context;

import com.appcode.downloadsdk.DownloadSdk;
import com.appcode.jsbundle.GetReactPackageCallback;
import com.appcode.jsbundle.JSBundleSdk;
import com.appcode.reactnativeapp.communication.CommPackage;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;
import com.appcode.reactnativeapp.hotupdate.FileConstant;

import java.io.File;
import java.util.List;

import javax.annotation.Nullable;


public class MainApplication extends Application implements ReactApplication {

  public static Context appContext;
  private static MainApplication instance;
  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    appContext = getApplicationContext();
    /* native exopackage */
    SoLoader.init(this,  false);
    JSBundleSdk.init(this);
    JSBundleSdk.setDebug(true);
    DownloadSdk.init(this);
  }

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
