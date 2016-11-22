package com.jkb.core.control.messageState.dataSource;

import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 消息数据库的数据来源类
 * Created by JustKiddingBaby on 2016/10/24.
 */

public interface MessagesDbDataSource {

    /**
     * 获取所有的未读消息
     *
     * @param callback 回调
     */
    void getAllUnReadMessage(MessagesDataCallback<List<Messages>> callback);

    /**
     * 得到所有的消息
     *
     * @param callback 回调
     */
    void getAllMessage(MessagesDataCallback<List<Messages>> callback);

    /**
     * 得到所有的动态消息
     *
     * @param callback 回调
     */
    void getAllDynamicMessage(int user_id, MessagesDataCallback<List<Messages>> callback);

    /**
     * 得到所有未读动态消息
     *
     * @param callback 回调
     */
    void getAllUnReadDynamicMessage(int user_id, MessagesDataCallback<List<Messages>> callback);

    /**
     * 得到所有的动态消息数目
     *
     * @param callback 回调
     */
    void getAllDynamicMessageCount(int user_id, MessagesDataCallback<Integer> callback);

    /**
     * 得到所有未读的动态消息数目
     *
     * @param callback 回调
     */
    void getAllUnReadDynamicMessageCount(int user_id, MessagesDataCallback<Integer> callback);

    /**
     * 得到所有未读的动态消息数目
     *
     * @param callback 回调
     */
    void getAllUnReadMessageCount(int user_id, MessagesDataCallback<Integer> callback);

    /**
     * 得到所有未读的订阅消息数
     *
     * @param callback 回调
     */
    void getAllUnReadSubscribeMessageCount(int user_id, MessagesDataCallback<Integer> callback);

    /**
     * 得到所有的订阅消息数
     *
     * @param callback 回调
     */
    void getAllSubscribeMessageCount(int user_id, MessagesDataCallback<Integer> callback);

    /**
     * 得到所有未读的订阅消息
     *
     * @param callback 回调
     */
    void getAllUnReadSubscribeMessage(int user_id, MessagesDataCallback<List<Messages>> callback);

    /**
     * 得到所有订阅消息
     *
     * @param callback 回调
     */
    void getAllSubscribeMessage(int user_id, MessagesDataCallback<List<Messages>> callback);

    /**
     * 得到所有的未读粉丝数目
     */
    void getAllUnReadFansMessageCount(int user_id, MessagesDataCallback<Integer> callback);

    /**
     * 得到所有的未读粉丝消息
     */
    void getAllUnReadFansMessage(int user_id, MessagesDataCallback<List<Messages>> callback);

    /**
     * 得到所有的圈子消息
     */
    void getAllCircleMessages(int user_id, MessagesDataCallback<List<Messages>> callback);

    /**
     * 得到所有未读的圈子消息
     */
    void getAllUnReadCircleMessages(int user_id, MessagesDataCallback<List<Messages>> callback);

    /**
     * 得到所有未读的圈子消息数目
     */
    void getAllUnReadCircleMessagesCount(int user_id, MessagesDataCallback<Integer> callback);

    /**
     * 得到所有的圈子消息数目
     */
    void getAllCircleMessagesCount(int user_id, MessagesDataCallback<Integer> callback);

    /**
     * 获取所有的专题消息
     */
    void getAllSubjectMessage(int user_id, MessagesDataCallback<List<Messages>> callback);

    /**
     * 获取所有的专题消息数目
     */
    void getAllSubjectMessageCount(int user_id, MessagesDataCallback<Integer> callback);

    /**
     * 获取所有的未读专题消息数目
     */
    void getAllSubjectUnReadMessageCount(int user_id, MessagesDataCallback<Integer> callback);

    /**
     * 得带所以体验的系统消息的数目
     *
     * @param callback 回调
     */
    void getAllSystemMessageCount(MessagesDataCallback<Integer> callback);

    /**
     * 得带所有未登录的系统消息的数目
     *
     * @param callback 回调
     */
    void getAllUnReadSystemMessageCount(MessagesDataCallback<Integer> callback);

    /**
     * 存储数据到数据库中
     *
     * @param messages 消息数据库表的实体类对象
     */
    void saveMessagesToDb(Messages messages);

    /**
     * 通过消息id查找消息
     *
     * @param message_id 消息id
     * @param callback   回调
     */
    void getMessageById(int message_id, MessagesDataCallback<Messages> callback);
}
