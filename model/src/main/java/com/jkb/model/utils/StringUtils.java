package com.jkb.model.utils;

/**
 * String相关的工具类
 * Created by JustKiddingBaby on 2016/8/4.
 */

public class StringUtils {

    /**
     * 判断字符串是否为空
     *
     * @param strs
     * @return
     */
    public static boolean isEmpty(String... strs) {
        if (strs == null || strs.length == 0) {
            return true;
        }
        for (int i = 0; i < strs.length; i++) {
            if (strs[i] == null || strs[i].trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
