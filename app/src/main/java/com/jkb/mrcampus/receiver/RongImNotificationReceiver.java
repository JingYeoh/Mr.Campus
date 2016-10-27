package com.jkb.mrcampus.receiver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.activity.FirstActivity;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.activity.MessageActivity;
import com.jkb.mrcampus.utils.SystemUtils;

import io.rong.push.RongPushClient;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * 融云的消息通知的接收器
 * Created by JustKiddingBaby on 2016/10/20.
 */

public class RongImNotificationReceiver extends PushMessageReceiver {

    private static final String TAG = "RongImNotificationReceiver";

    @Override
    public boolean onNotificationMessageArrived(
            Context context, PushNotificationMessage pushNotificationMessage) {
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(
            Context context, PushNotificationMessage pushNotificationMessage) {
        RongPushClient.ConversationType conversationType =
                pushNotificationMessage.getConversationType();
        LogUtils.d(TAG, "我接受到的消息类型是=" + conversationType);
        if (conversationType == RongPushClient.ConversationType.PRIVATE) {
            startPrivateConversation(context, pushNotificationMessage);
        } else if (conversationType == RongPushClient.ConversationType.GROUP) {

        }
        return true;
    }

    /**
     * 启动私聊的相关页面
     */
    private void startPrivateConversation(
            Context context, PushNotificationMessage pushNotificationMessage) {
        String targetId = pushNotificationMessage.getTargetId();
        String targetName = pushNotificationMessage.getObjectName();
        RongPushClient.ConversationType type = pushNotificationMessage.getConversationType();
        if (SystemUtils.isActivityRunning(MainActivity.class.getName(), context)) {
            SystemUtils.startConversationPrivateActivity(context,type,targetId,targetName);
        } else {
            //正常启动
            Bundle args = new Bundle();
            args.putString(com.jkb.mrcampus.Config.BUNDLE_KEY_JUMP_ACTION,
                    com.jkb.mrcampus.Config.BUNDLE_JUMP_ACTION_CONVERSATION_PRIVETE);
            args.putSerializable(Config.INTENT_KEY_CONVERSATION_TYPE,type);
            args.putString(Config.INTENT_KEY_TARGETID,targetId);
            args.putString(Config.INTENT_KEY_TARGETNAME,targetName);
            SystemUtils.launchApp(context,args);
        }
    }
}
