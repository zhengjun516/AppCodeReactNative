package com.appcode.reactnativeapp;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.appcode.jsbundle.GetReactPackageCallback;
import com.appcode.jsbundle.JSBundle;
import com.appcode.jsbundle.JSBundleSdk;
import com.appcode.reactnativeapp.communication.CommPackage;
import com.appcode.reactnativeapp.hotupdate.HotUpdate;
import com.facebook.react.PackageList;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CALL_PERMISSION = 10111;

    private CompleteReceiver localReceiver;
    private long mDownLoadId;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initReactInstance();

        setContentView(R.layout.activity_main);
        checkPermissions();
    }

    private void initReactInstance() {
        JSBundleSdk.initAllReactContext();
    }

    public void checkPermissions(){
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,REQUEST_CALL_PERMISSION);
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,REQUEST_CALL_PERMISSION);
        checkPermission(Manifest.permission.CALL_PHONE,REQUEST_CALL_PERMISSION);
    }

    /**
     * 下载更新包
     * @param v
     */
    public void load(View v) {
        checkVersion();
        HotUpdate.mergePatAndAsset(this);
    }
    private void checkVersion() {
        // 默认有最新版本
        Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
        //downLoadBundle();
    }

    /**
     * 注册广播
     */
    private void registeReceiver() {
        localReceiver = new CompleteReceiver();
        registerReceiver(localReceiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void sendMsgToRN(View view) {
        //startActivity(new Intent(this, App1ReactActivity.class));
     /*   String appName = "ReactNativeApp";
        JSBundle jsBundle = new JSBundle(appName,null, "bundles/bundle02/index.android.bundle" );
        jsBundle.setGetReactPackageCallback(new GetReactPackageCallback() {
            @Override
            public List<ReactPackage> getReactPackages(ReactNativeHost reactNativeHost) {
                PackageList packageList = new PackageList(reactNativeHost);
                packageList.getPackages().add(new CommPackage());
                return packageList.getPackages();
            }
        });*/
       // JSBundleSdk.startJSBundle(jsBundle);
    }

    public void startAppCodeReactActivity(View view){
   /*     String appName = "ReactNativeApp";
        List<String> mainCompnentNames = new ArrayList<>();
        mainCompnentNames.add("AppCodeReactNative");
        mainCompnentNames.add("ReactNativeApp");
        JSBundle jsBundle = new JSBundle(mainCompnentNames, appName,null, "bundles/bundle01/index.bundle");
        jsBundle.setGetReactPackageCallback(new GetReactPackageCallback() {
            @Override
            public List<ReactPackage> getReactPackages(ReactNativeHost reactNativeHost) {
                PackageList packageList = new PackageList(reactNativeHost);
                List<ReactPackage> reactPackages = packageList.getPackages();
                reactPackages.add(new CommPackage());
                return reactPackages;
            }
        });
        JSBundleSdk.startJSBundle(jsBundle);*/
    }

    public void jumpToMultipleActivity(View view){
        String appName = "ReactNativeApp";
        JSBundleSdk.startJSBundle(appName,true);
    }

    public void jumpToMultipleActivity2(View view){
        String appName = "AppCodeReactNative";
        JSBundleSdk.startJSBundle(appName,true);
    }

    public class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long completeId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            if(completeId == mDownLoadId) {
                HotUpdate.handleZIP(getApplicationContext());
            }
        }
    }

    public boolean checkPermission(String string_permission,int request_code) {
        boolean flag = false;
        //已有权限
        if (ContextCompat.checkSelfPermission(this, string_permission) == PackageManager.PERMISSION_GRANTED) {
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(this, new String[]{string_permission}, request_code);
        }
        return flag;
    }


    /**
     * 下载最新Bundle
     */
  /*  private void downLoadBundle() {
        // 1.下载前检查SD卡是否存在更新包文件夹
        HotUpdate.checkPackage(getApplicationContext(), FileConstant.LOCAL_FOLDER);
        // 2.下载
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager
                .Request(Uri.parse(FileConstant.JS_BUNDLE_REMOTE_URL));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE| DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationUri(Uri.parse("file://"+ FileConstant.JS_PATCH_LOCAL_PATH));
        mDownLoadId = downloadManager.enqueue(request);
    }*/
}
