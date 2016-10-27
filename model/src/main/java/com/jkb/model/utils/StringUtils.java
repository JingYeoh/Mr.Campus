package com.jkb.model.utils;

import android.text.format.DateFormat;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * String相关的工具类
 * Created by JustKiddingBaby on 2016/8/4.
 */

public class StringUtils {

    /**
     * 判断字符串是否为空
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

    /**
     * 转换时间对象为字符串
     */
    public static String changeDateToString(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = f.format(date);
        return format;
    }

    /**
     * 字符串转换为时间
     *
     * @param dateStr 时间字符串
     * @return 时间对象
     */
    public static Date getDateFromStr(String dateStr) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = f.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 得到系统当前时间
     *
     * @return
     */
    public static Date getSystemCurrentTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return getDateFromStr(date);
    }
}
