package com.appcode.reactnativeapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import com.appcode.downloadsdk.DownloadManager;
import com.appcode.downloadsdk.DownloadSdk;
import com.appcode.downloadsdk.FileManager;
import com.appcode.downloadsdk.UnZipManager;
import com.appcode.downloadsdk.model.bean.DownloadData;
import com.appcode.jsbundle.GetReactPackageCallback;
import com.appcode.jsbundle.JSBundleConstant;
import com.appcode.jsbundle.JSBundleSdk;
import com.appcode.jsbundle.JSIntent;
import com.appcode.reactnativeapp.communication.CommPackage;
import com.facebook.react.PackageList;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CALL_PERMISSION = 10111;

    private CompleteReceiver localReceiver;
    private long mDownLoadId;

    private ContentLoadingProgressBar mProgressBar;
    private ContentLoadingProgressBar mProgressBar1;
    private ContentLoadingProgressBar mProgressBar2;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.mProgressBar);
        mProgressBar1 = findViewById(R.id.mProgressBar1);
        mProgressBar2 = findViewById(R.id.mProgressBar2);

        checkPermissions();
        requestPermission();

        initJSBundle();

        initReactInstance();
    }

    private void initJSBundle() {
        JSBundleSdk.initSDCardJSBundle(new GetReactPackageCallback() {
            @Override
            public List<ReactPackage> getReactPackages(ReactNativeHost reactNativeHost) {
                PackageList packageList = new PackageList(reactNativeHost);
                List<ReactPackage> reactPackages = packageList.getPackages();
                reactPackages.add(new CommPackage());
                return reactPackages;
            }
        });
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
    public void loadBaseBundle(View v) {
        mProgressBar.setVisibility(View.VISIBLE);
        DownloadManager downloadManager = DownloadSdk.getDownloadManager();
        downloadManager.download(Api.baseBundleUrl,new DownloadManager.DefaultDownloadCallback(){
            @Override
            public void onBefore() {
                super.onBefore();
            }

            @Override
            public void onProgress(int process) {
                super.onProgress(process);
                mProgressBar.setProgress(process);
            }

            @Override
            public void onComplete(DownloadData data) {
                super.onComplete(data);
                UnZipManager.unzip(data.getLocalUrl(), JSBundleConstant.BUNDLES_PATH_DATA);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(String error) {
                super.onFailed(error);
                mProgressBar.setVisibility(View.GONE);
            }
        });

    }

    public void loadBusinessBundle(View v) {
        // checkVersion();
        //HotUpdate.mergePatAndAsset(this);
        mProgressBar1.setVisibility(View.VISIBLE);
        DownloadManager downloadManager = DownloadSdk.getDownloadManager();
        downloadManager.download(Api.businessBundleUrl,new DownloadManager.DefaultDownloadCallback(){
            @Override
            public void onBefore() {
                super.onBefore();
            }

            @Override
            public void onProgress(int process) {
                super.onProgress(process);
                mProgressBar1.setProgress(process);
            }

            @Override
            public void onComplete(DownloadData data) {
                super.onComplete(data);
                UnZipManager.unzip(data.getLocalUrl(), JSBundleConstant.BUNDLES_PATH_DATA);
                mProgressBar1.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(String error) {
                super.onFailed(error);
                mProgressBar1.setVisibility(View.GONE);
            }
        });
    }

    public void loadBusiness2Bundle(View v) {
        // checkVersion();
        //HotUpdate.mergePatAndAsset(this);
        mProgressBar2.setVisibility(View.VISIBLE);
        DownloadManager downloadManager = DownloadSdk.getDownloadManager();
        downloadManager.download(Api.business2BundleUrl,new DownloadManager.DefaultDownloadCallback(){
            @Override
            public void onBefore() {
                super.onBefore();
            }

            @Override
            public void onProgress(int process) {
                super.onProgress(process);
                mProgressBar2.setProgress(process);
            }

            @Override
            public void onComplete(DownloadData data) {
                super.onComplete(data);
                UnZipManager.unzip(data.getLocalUrl(), JSBundleConstant.BUNDLES_PATH_DATA);
                mProgressBar2.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(String error) {
                super.onFailed(error);
                mProgressBar2.setVisibility(View.GONE);
            }
        });
    }

    public void deleteBundle(View view){
        FileManager.deleteDir(JSBundleConstant.BUNDLES_PATH_DATA);
        FileManager.deleteDir(JSBundleConstant.DOWNLOAD_PATH_DATA);
    }

    public void reinitialize(View view){
        initJSBundle();
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
        //registerReceiver(localReceiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void sendMsgToRN(View view) {
        String appName = "ReactNativeApp";
        //String appName = "AppCodeReactNative";
        JSIntent jsIntent = new JSIntent("business",appName);
        JSBundleSdk.startJSBundle(jsIntent);
    }

    public void startAppCodeReactActivity(View view){

        String appName = "AppCodeReactNative";
        JSIntent jsIntent = new JSIntent("business2",appName);
        JSBundleSdk.startJSBundle(jsIntent);
    }

    public void jumpToMultipleActivity(View view){
        String appName = "ReactNativeApp";
        JSIntent jsIntent = new JSIntent("business",appName);
        JSBundleSdk.startJSBundle(jsIntent);
    }

    public void jumpToMultipleActivity2(View view){
        String appName = "AppCodeReactNative";
        JSIntent jsIntent = new JSIntent("business2",appName);
        JSBundleSdk.startJSBundle(jsIntent);
    }

    public class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            /*long completeId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            if(completeId == mDownLoadId) {
            HotUpdate.handleZIP(getApplicationContext());*/
        }
    }

    private void requestPermission() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, permissions, 1);
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
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
