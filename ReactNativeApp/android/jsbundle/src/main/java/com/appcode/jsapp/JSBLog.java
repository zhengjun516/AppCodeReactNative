/**
 * <p>Title: MyLog.java</p>
 * <p>Description: </p>
 * <p/>
 * <p>Copyright: Copyright (c) 2011 by
 * Beijing Freefly, Beijing, China
 * All rights reserved.</p>
 * <p>Company: Freefly</p>
 *
 * @author wangsq
 * @version 1.0
 * @see <PRE>
 * <U>Updated by:</U>   Lester wangsq, 2009-9-16
 * <U>Description:</U>  Update description
 * </PRE>
 */
package com.appcode.jsapp;

import android.util.Log;

public class JSBLog {

    public static String TAG  = "JSBundle_";

    public static boolean DEBUG = BuildConfig.DEBUG;

    public static void d(String msg){
         d(TAG,msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG){
            Log.d(TAG+tag, msg);
        }
    }

    public static void e(Throwable throwable){
        e(TAG,throwable);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void e(String tag, Exception e) {
        e(tag, e.getMessage(), e);
    }

    public static void e(String tag, Throwable throwable) {
        e(tag, throwable.getMessage(), throwable);
    }


    public static void e(String tag, String msg, Throwable e) {
        Log.e(tag, msg, e);
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, Exception e) {
        w(tag, e.getMessage(), e);
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable e) {
        if (DEBUG) {
            Log.w(tag, msg, e);
        }
    }


}
