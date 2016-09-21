package com.jkb.model.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间的工具类
 * Created by JustKiddingBaby on 2016/9/18.
 */

public class TimeUtils {

    private static final String TAG = "TimeUtils";

    /**
     * 判断 beforeTime是否在afterTime之前
     *
     * @param beforeTime
     * @param afterTime
     * @return
     */
    public static boolean dateDue(String beforeTime, String afterTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dateBefore = df.parse(beforeTime);
            Date dateAfter = df.parse(afterTime);
            if (dateBefore.getTime() > dateAfter.getTime()) {
                System.out.println("dt1 在dt2前");
                return false;
            } else if (dateBefore.getTime() < dateAfter.getTime()) {
                System.out.println("dt1在dt2后");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 得到系统当前时间
     */
    public static String getCurrentTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    /**
     * 转换时间为只有日期显示
     *
     * @param time yyyy-MM-dd HH:mm:ss格式的事件
     * @return
     */
    public static String changeTimeToDay(String time) {
        String result = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date;
            Calendar now = Calendar.getInstance();
            date = format.parse(time);
            now.setTime(date);
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH) + 1; // 0-based!
            int day = now.get(Calendar.DAY_OF_MONTH);
            result = year + "/" + month + "/" + day;
            Log.i(TAG, "year=" + year);
            Log.i(TAG, "month=" + month);
            Log.i(TAG, "day=" + day);
        } catch (ParseException e) {
            Log.d(TAG, "ParseException");
            e.printStackTrace();
        }
        return result;
    }
}
