package com.jkb.mrcampus.activity.callback;

import io.rong.imlib.RongIMClient;

/**
 * 融云聊天的连接状态的监听回调
 * Created by JustKiddingBaby on 2016/10/21.
 */

public interface RongIMConnectCallBack {

    /**
     * token过期
     */
    void onTokenIncorrect();

    /**
     * 成功
     *
     * @param user_id 用户id
     */
    void onSuccess(int user_id);

    /**
     * 连接错误
     *
     * @param errorCode 错误信息码
     */
    void onError(RongIMClient.ErrorCode errorCode);
}
