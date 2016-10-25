package com.jkb.core.control.messageState;

import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 消息的被观察者的动作
 * Created by JustKiddingBaby on 2016/10/24.
 */

public interface MessageObservableAction {

    /**
     * 得到所有的动态消息
     */
    List<Messages> getAllDynamicMessage();

    /**
     * 得到所有未读的动态消息
     */
    List<Messages> getAllUnReadDynamicMessage();

    /**
     * 得带所有的未读消息数
     */
    int getAllUnReadMessageCount();

    /**
     * 得到所有的动态消息数
     */
    int getAllDynamicMessageCount();

    /**
     * 得到所有的未读的动态消息数
     */
    int getAllUnReadDynamicMessageCount();

    /**
     * 读取消息
     */
    void readMessage(Messages messages);

    /**
     * 读取消息
     */
    void readMessage(int messageId);

    /**
     * 存储消息
     */
    void saveMessage(Messages messages);
}
