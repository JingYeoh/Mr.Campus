package com.jkb.mrcampus.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jkb.api.config.Config;
import com.jkb.core.control.messageState.MessageObservable;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.activity.MessageActivity;
import com.jkb.mrcampus.receiver.data.MessageModel;
import com.jkb.mrcampus.utils.SystemUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Observable;
import java.util.Observer;

import cn.jpush.android.api.JPushInterface;
import jkb.mrcampus.db.entity.Messages;
import jkb.mrcampus.db.entity.UserAuths;

/**
 * 激光推送的广播接收器
 * Created by JustKiddingBaby on 2016/10/23.
 */

public class JPushReceiver extends BroadcastReceiver implements Observer {

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        //添加为观察者
        MessageObservable.newInstance().addObserver(this);

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
            saveReceivedMessage(context, bundle, true);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtils.d(JPushReceiver.class, "接受到推送下来的通知");
            saveNotifyMessage(context, bundle, false);
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
        Messages messages = changeNotifyDataToMessages(bundle, false);
        if (messages == null) {
            return;
        }
        //跳转
        String action = messages.getAction();
        switch (action) {
            case Config.MESSAGE_ACTION_FAVORITE:
            case Config.MESSAGE_ACTION_LIKE:
            case Config.MESSAGE_ACTION_MAKECOMMENT:
            case Config.MESSAGE_ACTION_MAKEREPLY:
                //跳转到动态页面
                startDynamicMessage(context);
                break;
        }
    }

    /**
     * 打开动态的消息页面
     */
    private void startDynamicMessage(Context context) {
        if (SystemUtils.isActivityRunning(MainActivity.class.getName(), context)) {
            Intent mainIntent = new Intent(context, MessageActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mainIntent.putExtra(com.jkb.mrcampus.Config.INTENT_KEY_MESSAGE_TYPE,
                    MessageActivity.MESSAGE_TYPE_DYNAMIC);
            context.startActivity(mainIntent);
        } else {
            //正常启动
            Bundle args = new Bundle();
            args.putString(com.jkb.mrcampus.Config.BUNDLE_KEY_JUMP_ACTION,
                    com.jkb.mrcampus.Config.BUNDLE_JUMP_ACTION_MESSAGE_DYNAMIC);
            SystemUtils.launchApp(context,args);
        }
    }

    /**
     * 转换数据为Messages表的实体对象
     */
    private Messages changeReceiveDataToMessages(Bundle bundle, boolean isRead) {
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
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
            Messages messages = changeToMessages(title, title, messageModel, isRead);
            return messages;
        } catch (JSONException e) {
            LogUtils.w(JPushReceiver.class, "消息非json，解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换数据为Messages表的实体对象
     */
    private Messages changeNotifyDataToMessages(Bundle bundle, boolean isRead) {
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
            Messages messages = changeToMessages(alert, title, messageModel, isRead);
            return messages;
        } catch (JSONException e) {
            LogUtils.w(JPushReceiver.class, "消息非json，解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存自定义消息
     */
    private void saveReceivedMessage(Context context, Bundle bundle, boolean isRead) {
        Messages messages = changeReceiveDataToMessages(bundle, isRead);
        addOrUpdateToDb(messages);
    }

    /**
     * 处理消息
     */
    private void saveNotifyMessage(Context context, Bundle bundle, boolean isRead) {
        Messages messages = changeNotifyDataToMessages(bundle, isRead);
        addOrUpdateToDb(messages);
    }

    /**
     * 添加或者更新消息數據庫表
     */
    private void addOrUpdateToDb(Messages messages) {
        if (messages != null) {
            int user_id;
            if (messages.getAction().equals("system")) {
                user_id = -1;
            } else {
                if (LoginContext.getInstance().isLogined()) {
                    UserAuths userAuths = UserInfoSingleton.getInstance().getUserAuths();
                    user_id = userAuths.getUser_id();
                } else {
                    user_id = 0;
                }
            }
            messages.setUser_id(user_id);
            MessageObservable.newInstance().saveMessage(messages);
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
    private Messages changeToMessages(
            String alert, String action, MessageModel messageModel, boolean is_read) {
        if (messageModel == null) {
            return null;
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
        messages.setRelationContent(messageModel.getRelationContent());
        messages.setUpdated_at(StringUtils.getSystemCurrentTime());
        return messages;
    }

    @Override
    public void update(Observable o, Object arg) {
        //在此无用
    }
}
