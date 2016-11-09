package com.jkb.core.presenter.message;

import com.jkb.api.config.Config;
import com.jkb.core.contract.message.MessageCircleContract;
import com.jkb.model.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 圈子消息的P层
 * Created by JustKiddingBaby on 2016/11/9.
 */

public class MessageCirclePresenter implements MessageCircleContract.Presenter {

    private MessageCircleContract.View view;

    //data
    private List<Messages> circleMessages;

    public MessageCirclePresenter(MessageCircleContract.View view) {
        this.view = view;

        circleMessages = new ArrayList<>();
        this.view.setPresenter(this);
    }

    @Override
    public void onCircleItemClick(int position) {
        Messages messages = circleMessages.get(position);
        view.readCircleMessage(messages);
        int senderId = messages.getSenderId();
        view.startCircleIndex(senderId);
    }

    @Override
    public void onItemClick(int position) {
        Messages messages = circleMessages.get(position);
        view.readCircleMessage(messages);
        String action = messages.getAction();
        switch (action) {
            case Config.MESSAGE_ACTION_IN_BLACK_LIST_USER:
            case Config.MESSAGE_ACTION_OUT_BLACK_LIST_USER:
                int targetId = messages.getTargetId();
                view.startPersonCenter(targetId);
                break;
            case Config.MESSAGE_ACTION_IN_BLACK_LIST_DYNAMIC:
            case Config.MESSAGE_ACTION_OUT_BLACK_LIST_DYNAMIC:
                jumpToDynamic(messages);
                break;
        }
    }

    /**
     * 跳转到动态页面
     */
    private void jumpToDynamic(Messages messages) {
        if (messages != null) {
            //设置message为读取状态
            view.readCircleMessage(messages);
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
    public void setDynamicMessages(List<Messages> circleMessages) {
        if (circleMessages == null) {
            circleMessages = new ArrayList<>();
        }
        this.circleMessages = circleMessages;
        view.setCircleMessages(this.circleMessages);
    }

    @Override
    public void start() {

    }
}
