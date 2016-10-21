package com.jkb.mrcampus.receiver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.activity.FirstActivity;

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
        Intent[] intents = new Intent[2];
        //主要的Activity
        Intent mainIntent = new Intent(context, FirstActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intents[0] = mainIntent;
        //会话的Activity
        Intent conversationIntent = new Intent();
//        conversationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri.Builder builder = Uri.parse("rong://" + context.getPackageName()).buildUpon();
        RongPushClient.ConversationType type = pushNotificationMessage.getConversationType();
        String targetId = pushNotificationMessage.getTargetId();
        String targetName = pushNotificationMessage.getObjectName();
        builder.appendPath("conversation").appendPath(type.getName())
                .appendQueryParameter("targetId", targetId)
                .appendQueryParameter("title", targetName);
        Uri uri = builder.build();
        conversationIntent.setData(uri);
        intents[1] = conversationIntent;
        context.startActivities(intents);
    }
}
