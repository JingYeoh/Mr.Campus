package com.jkb.core.presenter.message;

import com.jkb.core.contract.message.MessageSubscribeContract;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 订阅消息的P层
 * Created by JustKiddingBaby on 2016/11/6.
 */

public class MessageSubscribePresenter implements MessageSubscribeContract.Presenter {

    private MessageSubscribeContract.View view;

    //data
    private List<Messages> subscribeMessages;

    public MessageSubscribePresenter(MessageSubscribeContract.View view) {
        this.view = view;

        this.view.setPresenter(this);
    }

    @Override
    public void onUserItemClick(int position) {
        Messages messages = subscribeMessages.get(position);
        if (messages != null) {
            int senderId = messages.getSenderId();
            view.startPersonCenter(senderId);
        }
    }

    @Override
    public void onCircleItemClick(int position) {
        Messages messages = subscribeMessages.get(position);
        if (messages != null) {
            int targetId = messages.getTargetId();
            view.startCircleIndex(targetId);
        }
    }

    @Override
    public void setSubscribeMessages(List<Messages> subscribeMessages) {
        if (subscribeMessages == null) {
            subscribeMessages = new ArrayList<>();
        }
        this.subscribeMessages = subscribeMessages;
        view.setSubscribeMessages(subscribeMessages);
    }

    @Override
    public void start() {
        view.readAllSubscribeMessage();
    }
}
