package com.jkb.core.presenter.message;

import com.jkb.api.config.Config;
import com.jkb.core.contract.message.MessageDynamicContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.model.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 动态消息的P层
 * Created by JustKiddingBaby on 2016/10/26.
 */

public class MessageDynamicPresenter implements MessageDynamicContract.Presenter {

    private MessageDynamicContract.View view;

    //data
    private List<Messages> dynamicMessages;

    public MessageDynamicPresenter(MessageDynamicContract.View view) {
        this.view = view;

        dynamicMessages = new ArrayList<>();

        this.view.setPresenter(this);
    }

    @Override
    public void onDynamicMessageItemClick(int position) {
        Messages messages = dynamicMessages.get(position);
        if (messages != null) {
            //设置message为读取状态
            view.readDynamicMessage(messages);
            //打开相关的页面
            //判断页面类型
            String targetType = messages.getTargetType();
            int targetId = messages.getTargetId();
            LogUtils.d(MessageDynamicPresenter.class, "targetType=" + targetType);
            LogUtils.d(MessageDynamicPresenter.class, "targetId=" + targetId);
            switch (targetType) {
                case Config.D_TYPE_NORMAL:
                    view.startDynamicNormal(targetId);
                    break;
                case Config.D_TYPE_TOPIC:
                    view.startDynamicTopic(targetId);
                    break;
                case Config.D_TYPE_ARTICLE:
                    view.startDynamicArticle(targetId);
                    break;
            }
        }
    }

    @Override
    public void setDynamicMessages(List<Messages> dynamicMessages) {
        if (dynamicMessages == null) {
            dynamicMessages = new ArrayList<>();
        }
        this.dynamicMessages = dynamicMessages;
        view.setDynamicMessages(dynamicMessages);
    }

    @Override
    public void start() {
    }
}
