package com.jkb.model.utils;

import android.util.Log;

/**
 * Log的工具类
 * Created by JustKiddingBaby on 2016/10/18.
 */

public class LogUtils {

    private static boolean isAllowToPrint = true;

    public static void i(String TAG, String value) {
        if (StringUtils.isEmpty(value)) {
            value = "null";
        }
        if (isAllowToPrint) {
            Log.i(TAG, value);
        }
    }

    public static void v(String TAG, String value) {
        if (StringUtils.isEmpty(value)) {
            value = "null";
        }
        if (isAllowToPrint) {
            Log.v(TAG, value);
        }
    }

    public static void w(String TAG, String value) {
        if (StringUtils.isEmpty(value)) {
            value = "null";
        }
        if (isAllowToPrint) {
            Log.w(TAG, value);
        }
    }

    public static void e(String TAG, String value) {
        if (StringUtils.isEmpty(value)) {
            value = "null";
        }
        if (isAllowToPrint) {
            Log.e(TAG, value);
        }
    }

    public static void d(String TAG, String value) {
        if (StringUtils.isEmpty(value)) {
            value = "null";
        }
        if (isAllowToPrint) {
            Log.d(TAG, value);
        }
    }

    public static void wtf(String TAG, String value) {
        if (StringUtils.isEmpty(value)) {
            value = "null";
        }
        if (isAllowToPrint) {
            Log.wtf(TAG, value);
        }
    }
}
