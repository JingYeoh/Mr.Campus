package com.jkb.model.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

/**
 * 系统的工具类
 *
 * @author JustKiddingBaby
 */
public class SystemUtils {

    /**
     * 判断当前应用程序处于前台还是后台，方法一
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                // Log.i("后台", "---后台程序1");
                return true;
            }
        }
        // Log.i("前台", "---前台程序1");
        return false;
    }

    /**
     * 判断当前应用程序处于前台还是后台，方法二
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    // Log.i("后台", appProcess.processName);
                    return true;
                } else {
                    // Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断Service是否在运行
     *
     * @param mContext
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 判断Activity是否在首部运行
     *
     * @param mContext
     * @param activityClassName
     * @return
     */
    public static boolean isActivityRunningFirst(Context mContext,
                                                 String activityClassName) {
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> info = activityManager.getRunningTasks(1);
        if (info != null && info.size() > 0) {
            ComponentName component = info.get(0).topActivity;
            if (activityClassName.equals(component.getClassName())) {
                return true;
            }
        }
        return false;
    }

    // public static boolean isActivityRunning(Context context,String
    // activityName){
    // ActivityManager activityManager = (ActivityManager) context
    // .getSystemService(Context.ACTIVITY_SERVICE);
    // List<RunningTaskInfo> info = activityManager.getRunningTasks(1);
    // if (info != null && info.size() > 0){
    // for(RunningTaskInfo taskInfo:info){
    // if(taskInfo.get)
    // }
    // }
    // }

    /**
     * 得到当前活动的Activity名称
     *
     * @param context
     * @return
     */
    public static String getRunningActivityName(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity
                .getClassName();
        return runningActivity;
    }

    /**
     * 程序进入后台
     *
     * @param context
     */
    public static void onBackPress(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }

    /**
     * 完全退出程序
     *
     * @param context
     */
    public static void exitProgrames(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 得到当前系统的版本号
     *
     * @param context
     * @return
     */
    public static String getCurrentVersion(@NonNull Context context) throws PackageManager.NameNotFoundException {
        String currentVersion = null;
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        currentVersion = packInfo.versionName;
        return currentVersion;
    }

}
