package com.jkb.mrcampus.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * 系统相关的工具类
 * Created by JustKiddingBaby on 2016/7/28.
 */
public class SystemUtils {

    /**
     * 退出系统
     */
    public static void exitSystem(Context applicationContext) {

        ActivityManager am = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (tasks != null && !tasks.isEmpty()) {
            for (ActivityManager.RunningTaskInfo info : tasks) {

            }
        }
    }
}
