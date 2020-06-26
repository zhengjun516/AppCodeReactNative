package com.appcode.reactnativeapp;

import android.app.Application;
import android.content.Context;

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
    SoLoader.init(this, /* native exopackage */ false);
    JSBundleSdk.init(this);
    JSBundleSdk.initAssetsJSBundle(new GetReactPackageCallback() {
      @Override
      public List<ReactPackage> getReactPackages(ReactNativeHost reactNativeHost) {
        PackageList packageList = new PackageList(reactNativeHost);
        List<ReactPackage> reactPackages = packageList.getPackages();
        reactPackages.add(new CommPackage());
        return reactPackages;
      }
    });
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
