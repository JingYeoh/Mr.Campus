package com.jkb.mrcampus.utils;

import android.support.annotation.NonNull;

/**
 * 关于类之间的工具类
 * Created by JustKiddingBaby on 2016/8/1.
 */
public class ClassUtils {

    /**
     * 根据类得到类名
     *
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> String getClassName(@NonNull Class<T> clz) {
        return clz.getName();
    }

    /**
     * 判断两个类名是否相等
     *
     * @param name
     * @param clz
     * @return
     */
    public static boolean isNameEquals(String name, Class<?> clz) {
        if (clz == null || name == null) {
            return false;
        } else {
            if (getClassName(clz).equals(name)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
