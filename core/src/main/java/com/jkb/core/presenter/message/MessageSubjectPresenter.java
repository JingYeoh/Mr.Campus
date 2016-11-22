package com.jkb.core.presenter.message;

import com.jkb.api.config.Config;
import com.jkb.core.contract.message.MessageSubjectContract;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 专题消息的P层
 * Created by JustKiddingBaby on 2016/11/21.
 */

public class MessageSubjectPresenter implements MessageSubjectContract.Presenter {

    private MessageSubjectContract.View view;

    //data
    private List<Messages> subjectMessages;

    public MessageSubjectPresenter(MessageSubjectContract.View view) {
        this.view = view;

        subjectMessages = new ArrayList<>();

        this.view.setPresenter(this);
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void setSubjectMessages(List<Messages> subjectMessages) {
        if (subjectMessages == null) {
            subjectMessages = new ArrayList<>();
        }
        this.subjectMessages = subjectMessages;
        view.setSubjectMessages(subjectMessages);
    }

    @Override
    public void onSubjectItemClick(int position) {
        Messages messages = subjectMessages.get(position);
        if (messages == null) {
            return;
        }
        view.readSubjectMessage(messages);
        String targetType = messages.getTargetType();
        int targetId = messages.getTargetId();
        switch (targetType) {
            case Config.SUBJECT_TYPE_LOSTANDFOUND:
                view.startSubjectDetailLostAndFound(targetId);
                break;
            case Config.SUBJECT_TYPE_VINDICATOIN:
                view.startSubjectDetailConfession(targetId);
                break;
            case Config.SUBJECT_TYPE_COMPLAINT:
                view.startSubjectDetailTaunted(targetId);
                break;
            case Config.SUBJECT_TYPE_GRIND:
                view.startSubjectDetailWantedSavant(targetId);
                break;
            case Config.SUBJECT_TYPE_PARTNER:
                view.startSubjectDetailWantedPartner(targetId);
                break;
            case Config.SUBJECT_TYPE_FLEAMARKET:
                view.startSubjectDetailFleaMarket(targetId);
                break;
        }
    }

    @Override
    public void onUserItemClick(int position) {
        Messages messages = subjectMessages.get(position);
        if (messages == null) {
            return;
        }
        int senderId = messages.getSenderId();
        view.startPersonCenter(senderId);
    }

    @Override
    public void start() {

    }
}
