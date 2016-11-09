package com.jkb.core.control.messageState;

import android.os.Message;

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
     * 得到所有的未读的订阅消息数
     */
    int getAllUnReadSubscribeMessageCount();

    /**
     * 得到所有的订阅消息数
     */
    int getAllSubscribeMessageCount();

    /**
     * 得到所有的订阅的消息数
     */
    List<Messages> getAllSubscribeMessage();

    /**
     * 得到所有未读的消息数
     */
    List<Messages> getAllUnReadSubscribeMessage();

    /**
     * 得到所有的未读的粉丝消息数
     */
    int getAllUnReadFansMessageCount();

    /**
     * 得到所有圈子的消息
     */
    List<Messages> getAllCircleMessage();

    /**
     * 得到所有的圈子消息数目
     */
    int getAllCircleMessageCount();

    /**
     * 得到所有未读的圈子消息数目
     */
    int getAllUnReadCircleMessageCount();

    /**
     * 读取消息
     */
    void readMessage(Messages messages);

    /**
     * 读取消息
     */
    void readMessage(int messageId);

    /**
     * 读取所有的订阅消息
     */
    void readAllSubscribeMessage();

    /**
     * 读取所有的粉丝消息
     */
    void readAllFansMessage();

    /**
     * 存储消息
     */
    void saveMessage(Messages messages);
}
