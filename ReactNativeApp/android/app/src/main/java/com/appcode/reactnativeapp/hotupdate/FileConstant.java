package com.appcode.reactnativeapp.hotupdate;

import com.appcode.reactnativeapp.MainApplication;

import java.io.File;

/**
 * Created by Song on 2017/2/15.
 */
public class FileConstant {

    /**
     * zip的文件名
     */
    public static  String ZIP_NAME = "wan";

    /**
     * bundle文件名
     */
    public static  String JS_BUNDLE_LOCAL_FILE = "index.android.bundle";

    public static  String PATCH_IMG_FILE = "patch_imgs.txt";

    public static  String PATCHES_FILE = "patches.pat";

    public static String JS_PATCH_LOCAL_ROOT_FOLDER = MainApplication.getInstance().getExternalFilesDir(null).getAbsolutePath();

    /**
     * 第一次解压zip后的文件目录
     */
    public static  String JS_PATCH_LOCAL_FOLDER = JS_PATCH_LOCAL_ROOT_FOLDER
            + File.separator + MainApplication.getInstance().getAppPackageName();


    public static  String LOCAL_FOLDER = JS_PATCH_LOCAL_FOLDER + "/" + ZIP_NAME;

    public static  String DRAWABLE_PATH = JS_PATCH_LOCAL_FOLDER + "/" + ZIP_NAME + "/drawable-mdpi/";

    /**
     * 除第一次外，未来解压zip后的文件目录
     */
    public static  String FUTURE_JS_PATCH_LOCAL_FOLDER = JS_PATCH_LOCAL_FOLDER+"/future";

    public static  String FUTURE_DRAWABLE_PATH = FUTURE_JS_PATCH_LOCAL_FOLDER + "/"+ ZIP_NAME + "/drawable-mdpi/";
    public static  String FUTURE_PAT_PATH = FUTURE_JS_PATCH_LOCAL_FOLDER+"/wan/"+"bundle.pat";

    /**
     * zip文件
     */
    public static  String JS_PATCH_LOCAL_PATH = JS_PATCH_LOCAL_FOLDER + File.separator + ZIP_NAME + ".zip";


    /**
     * 合并后的bundle文件保存路径
     */
    public static  String JS_BUNDLE_LOCAL_PATH = JS_PATCH_LOCAL_FOLDER +"/wan/" + JS_BUNDLE_LOCAL_FILE;

    /**
     * .pat文件
     */
    public static  String JS_PATCH_LOCAL_FILE = JS_PATCH_LOCAL_FOLDER +"/wan/bundle.pat";

    /**
     * 增量图片名称文件路径
     */
    public static  String PATCH_IMG_NAMES_PATH = JS_PATCH_LOCAL_FOLDER +"/wan/" + PATCH_IMG_FILE;

    /**
     * 下载URL
     */
    public static  String JS_BUNDLE_REMOTE_URL = "http://dn.dengpaoedu.com/patches.pat";
}
