package com.jkb.mrcampus.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.receiver.data.MessageModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import cn.jpush.android.api.JPushInterface;
import jkb.mrcampus.db.MrCampusDB;
import jkb.mrcampus.db.dao.DaoSession;
import jkb.mrcampus.db.entity.Messages;

/**
 * 激光推送的广播接收器
 * Created by JustKiddingBaby on 2016/10/23.
 */

public class JPushReceiver extends BroadcastReceiver {

    private NotificationManager nm;
    //数据库
    private MrCampusDB mrCampusDB;
    private DaoSession daoSession;

    @Override
    public void onReceive(Context context, Intent intent) {
        //初始化数据库
        mrCampusDB = MrCampusDB.getInstance();
        daoSession = mrCampusDB.getDaoSession();

        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Bundle bundle = intent.getExtras();
        LogUtils.d(JPushReceiver.class,
                "onReceive - " + intent.getAction());
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            LogUtils.d(JPushReceiver.class, "JPush用户注册成功");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.d(JPushReceiver.class, "接受到推送下来的自定义消息");
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtils.d(JPushReceiver.class, "接受到推送下来的通知");
            saveMessage(context, bundle, false);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtils.d(JPushReceiver.class, "用户点击打开了通知");
            onNotificationClick(context, bundle);
        } else {
            LogUtils.d(JPushReceiver.class, "Unhandled intent - " + intent.getAction());
        }
    }

    /**
     * 当通知被打开的时候
     */
    private void onNotificationClick(Context context, Bundle bundle) {
        //跳转到相应的页面

    }

    /**
     * 处理消息
     */
    private void saveMessage(Context context, Bundle bundle, boolean isRead) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

        LogUtils.d(JPushReceiver.class, "---------title=" + title);
        LogUtils.d(JPushReceiver.class, "---------alert=" + alert);
        LogUtils.d(JPushReceiver.class, "---------extras=" + extras);

        //处理extras数据
        try {
            new JSONObject(extras);
            Gson gson = new Gson();
            gson.toJson(extras);
            Type mType = new TypeToken<MessageModel>() {
            }.getType();
            MessageModel messageModel = new Gson().fromJson(extras, mType);
            //录入到数据库中
            addOrUpdateToDb(alert, title, messageModel, isRead);
        } catch (JSONException e) {
            LogUtils.w(JPushReceiver.class, "消息非json，解析错误");
            e.printStackTrace();
        }
    }

    /**
     * 添加或者更新数据库数据
     *
     * @param alert        标题内容
     * @param action       标题/相当于action
     * @param messageModel 解析的消息对象
     * @param is_read      是否读取
     */
    private void addOrUpdateToDb(
            String alert, String action, MessageModel messageModel, boolean is_read) {
        if (messageModel == null) {
            return;
        }
        Messages messages = new Messages();
        messages.setMsg_content(alert);
        messages.setAction(action);
        messages.setIs_read(is_read);
        messages.setSenderId(messageModel.getSenderId());
        messages.setSenderName(messageModel.getSenderName());
        messages.setSenderPicture(messageModel.getSenderPicture());
        messages.setSenderType(messageModel.getSenderType());
        messages.setTargetId(messageModel.getTargetId());
        messages.setTargetName(messageModel.getTargetName());
        messages.setTargetPicture(messageModel.getTargetPicture());
        messages.setTargetType(messageModel.getTargetType());
        messages.setUpdated_at(StringUtils.getSystemCurrentTime());

        daoSession.insertOrReplace(messages);
    }
}
