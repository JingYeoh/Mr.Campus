package com.jkb.model.utils;

import android.util.Log;

import com.orhanobut.logger.Logger;

import org.json.JSONObject;

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
//            Logger.i(TAG, value);
        }
    }

    public static void v(String TAG, String value) {
        if (StringUtils.isEmpty(value)) {
            value = "null";
        }
        if (isAllowToPrint) {
            Log.v(TAG, value);
//            Logger.v(TAG, value);
        }
    }

    public static void w(String TAG, String value) {
        if (StringUtils.isEmpty(value)) {
            value = "null";
        }
        if (isAllowToPrint) {
            Log.w(TAG, value);
//            Logger.w(TAG, value);
        }
    }

    public static void e(String TAG, String value) {
        if (StringUtils.isEmpty(value)) {
            value = "null";
        }
        if (isAllowToPrint) {
            Log.e(TAG, value);
//            Logger.e(TAG, value);
        }
    }

    public static void d(String TAG, String value) {
        if (StringUtils.isEmpty(value)) {
            value = "null";
        }
        if (isAllowToPrint) {
            Log.d(TAG, value);
//            Logger.d(TAG, value);
        }
    }

    public static void wtf(String TAG, String value) {
        if (StringUtils.isEmpty(value)) {
            value = "null";
        }
        if (isAllowToPrint) {
            Log.wtf(TAG, value);
//            Logger.wtf(TAG, value);
        }
    }

    public static void i(Class<?> clz, String value) {
        String TAG = clz.getSimpleName();
        i(TAG, value);
    }

    public static void d(Class<?> clz, String value) {
        String TAG = clz.getSimpleName();
        d(TAG, value);
    }

    public static void v(Class<?> clz, String value) {
        String TAG = clz.getSimpleName();
        v(TAG, value);
    }

    public static void w(Class<?> clz, String value) {
        String TAG = clz.getSimpleName();
        w(TAG, value);
    }

    public static void e(Class<?> clz, String value) {
        String TAG = clz.getSimpleName();
        e(TAG, value);
    }

    public static void wtf(Class<?> clz, String value) {
        String TAG = clz.getSimpleName();
        wtf(TAG, value);
    }

    /**
     * 打印Json数据
     */
    public static void json(JSONObject json) {
        if (isAllowToPrint) {
            Logger.json(json.toString());
        }
    }

    /**
     * 打印Json数据
     */
    public static void json(String jsonObj) {
        if (isAllowToPrint) {
            Logger.json(jsonObj);
        }
    }
}
