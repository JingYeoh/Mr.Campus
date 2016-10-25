package com.jkb.core.control.messageState.dataSource;

/**
 * 消息数据库的回调类
 * Created by JustKiddingBaby on 2016/10/24.
 */

public interface MessagesDataCallback<T> {

    /**
     * 返回成功
     */
    void onSuccess(T messageObj);

    /**
     * 返回数据无效
     */
    void onDataNotAvailable();
}
