package com.jkb.model.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * SharePreference的工具类
 * Created by JustKiddingBaby on 2016/7/22.
 */
public class SharePreferenceUtils {

    /**
     * 缓存String数据到SharePreference中
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void cacheStringData(@NonNull Context context, String key, String value) {
        SharedPreferences.Editor e = context.getSharedPreferences(Config.APP_ID, context.MODE_PRIVATE)
                .edit();
        e.putString(key, value);
        e.commit();
    }

    /**
     * 从SharePreference中得到缓存的String数据
     *
     * @param context 上下文
     * @param key     键
     * @return
     */
    public static String getCachedStringData(@NonNull Context context, String key) {
        return context.getSharedPreferences(Config.APP_ID, context.MODE_PRIVATE)
                .getString(key, "");
    }
}
