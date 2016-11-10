package com.jkb.mrcampus.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.activity.PersonCenterActivity;

import java.util.List;

import io.rong.push.RongPushClient;

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

    /**
     * 判断Activity是否运行
     *
     * @param packageName 包名
     * @param context     上下文
     */
    public static boolean isActivityRunning(String packageName, Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        String MY_PKG_NAME = packageName;
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getClassName().equals(MY_PKG_NAME) ||
                    info.baseActivity.getClassName().equals(MY_PKG_NAME)) {
                isAppRunning = true;
                break;
            }
        }
        return isAppRunning;
    }

    /**
     * 判断app是否正在运行
     */
    public boolean appIsRunning(Context ctx, String packageName) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {

                if (runningAppProcessInfo.processName.startsWith(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * app 是否在后台运行
     */
    public boolean appIsBackgroundRunning(Context ctx, String packageName) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();

        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {

                if (runningAppProcessInfo.processName.startsWith(packageName)) {
                    return runningAppProcessInfo.importance != ActivityManager.RunningAppProcessInfo
                            .IMPORTANCE_FOREGROUND && runningAppProcessInfo.importance !=
                            ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE; //排除无界面的app
                }
            }
        }
        return false;
    }

    /**
     * 启动APP
     */
    public static void launchApp(Context context, Bundle bundle) {
        Intent launchIntent = context.getPackageManager().
                getLaunchIntentForPackage("com.jkb.mrcampus");
        launchIntent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        if (bundle != null) {
            launchIntent.putExtras(bundle);
        }
        context.startActivity(launchIntent);
    }

    /**
     * 打开私聊页面
     *
     * @param context    上下文
     * @param type       聊天类型
     * @param targetId   会话id
     * @param targetName 会话名称
     */
    public static void startConversationPrivateActivity(
            Context context, RongPushClient.ConversationType type,
            String targetId, String targetName) {
        Intent conversationIntent = new Intent();
        conversationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri.Builder builder = Uri.parse("rong://" + context.getPackageName()).buildUpon();
        builder.appendPath("conversation").appendPath(type.getName())
                .appendQueryParameter("targetId", targetId)
                .appendQueryParameter("title", targetName);
        Uri uri = builder.build();
        conversationIntent.setData(uri);
        context.startActivity(conversationIntent);
    }

    /**
     * 打开个人中心页面
     */
    public static void startPersonCenterActivity(
            Context context, int user_id) {
        Intent conversationIntent = new Intent();
        conversationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        conversationIntent.setClass(context, PersonCenterActivity.class);
        conversationIntent.putExtra(Config.INTENT_KEY_USER_ID, user_id);
        context.startActivity(conversationIntent);
    }
}
